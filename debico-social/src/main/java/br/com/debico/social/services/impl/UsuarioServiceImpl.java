package br.com.debico.social.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import org.jasypt.util.password.PasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.core.MessagesCodes;
import br.com.debico.core.spring.security.UserDetailsImpl;
import br.com.debico.model.Apostador;
import br.com.debico.model.ApostadorOpcoes;
import br.com.debico.model.Usuario;
import br.com.debico.social.CadastroApostadorException;
import br.com.debico.social.dao.ApostadorDAO;
import br.com.debico.social.dao.UsuarioDAO;
import br.com.debico.social.services.UsuarioService;

import static com.google.common.base.Preconditions.checkNotNull;

import static com.google.common.base.Strings.emptyToNull;

/**
 * Além das funções do bolão, implementa as interfaces de acesso do
 * <code>Spring Security</code> para realizar os casos de uso de login.
 * 
 * @see <a
 *      href="http://www.mkyong.com/spring-security/spring-security-hibernate-annotation-example/">Spring
 *      Security + Hibernate Annotation Example</a>
 * @see <a
 *      href="http://www.mkyong.com/tutorials/spring-security-tutorials/">Spring
 *      Security Tutorials</a>
 * 
 * @author ricardozanini
 * @since 0.1
 */
@Named
@Transactional
class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(UsuarioServiceImpl.class);

	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	private ApostadorDAO apostadorDAO;
	
	@Inject
	private PasswordEncryptor passwordEncryptor;

	@Inject
	@Named("resourceBundleMessageSource")
	private MessageSource messageSource;

	public UsuarioServiceImpl() {

	}

	@Transactional(rollbackFor = CadastroApostadorException.class)
	public void cadastrarApostadorUsuario(Apostador apostador, String confirmacaoSenha) throws CadastroApostadorException {
		LOGGER.debug("[cadastrarApostadorUsuario] Tentando realizar o cadastro do apostador '{}'.", apostador);
	    checkNotNull(apostador, "Apostador nao pode ser nulo");
	    checkNotNull(emptyToNull(confirmacaoSenha), "Confirmacao de senha em branco!");
	    
	    Usuario usuario = apostador.getUsuario();
	    
	    if(usuarioDAO.selecionarPorEmail(usuario.getEmail()) == null) {
	    	// todas opções padrão.
	        apostador.setOpcoes(new ApostadorOpcoes());
	        
	    	this.checarConfirmacaoSenha(usuario, confirmacaoSenha);
	        this.confirirPoliticaSenha(usuario.getSenha());
	        this.criptografarSenha(usuario);
	        
	        usuarioDAO.create(usuario);
	        apostadorDAO.create(apostador);
	        
	        //TODO: enviar email de confirmação.
	        LOGGER.debug("[cadastrarApostadorUsuario] Apostador '{}' cadastrado com sucesso!", usuario);
	    } else {
	    	LOGGER.warn("[cadastrarApostadorUsuario] Tentativa de cadastro de usuario com o email '{}'. Ja existe.", usuario.getEmail());
	        throw new CadastroApostadorException(messageSource, MessagesCodes.USUARIO_JA_CADASTRADO, usuario.getEmail());
	    }
	}
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("[loadUserByUsername] Tentando carregar o usuario '{}'.", username);

		Apostador apostador = apostadorDAO.selecionarPorEmail(username);

		if (apostador == null || apostador.getUsuario() == null) {
			throw new UsernameNotFoundException(
					messageSource.getMessage(
							MessagesCodes.USUARIO_NAO_ENCONTRADO, 
							new Object[] { username }, 
							Locale.getDefault()));
		}

	    Usuario usuario = apostador.getUsuario();
		usuario.setUltimoLogin(new Date());

		usuarioDAO.update(usuario);

		LOGGER.debug("[loadUserByUsername] Apostador com o usuario '{}' carregado.", usuario);
		return this.construirUsuario(apostador);
	}
	
	/**
	 * Confere a política de senha.
	 * <p/>
	 * Atualmente utilizamos o seguinte <code>regexp: (?=.{6,})(?=.*[a-zA-Z])(?=.*[0-9]).*</code>.
	 * <p/>
	 * Isso significa ao menos 6 caraterers com dígitos e letras maiúsculas ou minúsculas.
	 * 
	 * @param senha a ser conferida
	 * @throws CadastroApostadorException caso a <code>regexp</code> não seja atendida.
	 * @see <a href="http://stackoverflow.com/a/9922150">Regex for password policy</a>
	 */
	protected void confirirPoliticaSenha(final String senha) throws CadastroApostadorException {
	    Pattern p = Pattern.compile(
	                          "(?=.{6,})"   +     // "" followed by 6+ symbols
	                          "(?=.*[a-zA-Z])" +     // --- ' ' --- at least 1 lower or upper
	                          //"(?=.*[A-Z])" +     // --- ' ' --- at least 1 upper
	                          "(?=.*[0-9])" +     // --- ' ' --- at least 1 digit
	                          //"(?=.*\\p{Punct})"+ // --- ' ' --- at least 1 symbol
                              ".*");              // the actual characters
	    
	    if(!p.matcher(senha).matches()) {
	        throw new CadastroApostadorException(messageSource, MessagesCodes.SENHA_FAIL_POLITICA);
	    }
	}
	
	
	protected void checarConfirmacaoSenha(final Usuario usuario, final String confirmacaoSenha) throws CadastroApostadorException {      
        if(!confirmacaoSenha.equals(usuario.getSenha())) {
            throw new CadastroApostadorException(messageSource, MessagesCodes.SENHA_NAO_CONFERE);
        }
	}
	
	/**
	 * Realiza a criptografia de acordo com o algoritimo fornecido pelo framework <code>jasypt</code>.
	 * 
	 * @param usuario que possui a senha definida.
	 * @see <a href="http://www.jasypt.org/howtoencryptuserpasswords.html">How to encrypt user passwords</a>.
	 */
	protected void criptografarSenha(final Usuario usuario) {
        usuario.setSenha(passwordEncryptor.encryptPassword(usuario.getSenha()));
    }
   
	/**
	 * Constrói a estrutura base de um {@link User} de acordo com a especificação do Spring Security.
	 * 
	 * @param usuario
	 * @return
	 */
	protected User construirUsuario(final Apostador apostador) {
        final UserDetailsImpl user = new UserDetailsImpl(
                apostador.getUsuario().getEmail(), 
                apostador.getUsuario().getSenha(), 
                this.construirPerfil(apostador.getUsuario()));
        
        user.setId(apostador.getUsuario().getId());
        user.setName(apostador.getNome());
        user.setIdApostador(apostador.getId());
        
        return user;
    }
	
	/**
	 * Constrói a estrutura de perfil de acordo com a especificação do Spring Security.
	 * 
	 * @param usuario
	 * @return
	 */
	protected List<GrantedAuthority> construirPerfil(final Usuario usuario) {
	    List<GrantedAuthority> perfis = new ArrayList<GrantedAuthority>();
	    perfis.add(new SimpleGrantedAuthority(usuario.getPerfil()));
	    
	    return perfis;
    }

}
