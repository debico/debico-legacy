package br.com.debico.social.services.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.util.Map;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.RandomStringUtils;
import org.jasypt.util.password.PasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.core.MessagesCodes;
import br.com.debico.model.Apostador;
import br.com.debico.model.ApostadorOpcoes;
import br.com.debico.model.Usuario;
import br.com.debico.notify.model.ContatoImpl;
import br.com.debico.notify.model.TipoNotificacao;
import br.com.debico.notify.services.EmailNotificacaoService;
import br.com.debico.notify.services.TemplateContextoBuilder;
import br.com.debico.social.CadastroApostadorException;
import br.com.debico.social.TokenSenhaInvalidoException;
import br.com.debico.social.UsuarioInexistenteException;
import br.com.debico.social.UsuarioJaCadastradoException;
import br.com.debico.social.dao.ApostadorDAO;
import br.com.debico.social.dao.TokenLostPasswordDAO;
import br.com.debico.social.dao.UsuarioDAO;
import br.com.debico.social.model.PasswordContext;
import br.com.debico.social.model.TokenLostPassword;
import br.com.debico.social.services.UsuarioService;

import com.google.common.base.Strings;

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
class UsuarioServiceImpl implements UsuarioService {

    protected static final Logger LOGGER = LoggerFactory
	    .getLogger(UsuarioServiceImpl.class);

    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    private ApostadorDAO apostadorDAO;

    @Inject
    private PasswordEncryptor passwordEncryptor;

    @Inject
    private TokenLostPasswordDAO tokenDAO;

    @Inject
    private EmailNotificacaoService notificacaoService;

    @Inject
    @Named("resourceBundleMessageSource")
    private MessageSource messageSource;

    public UsuarioServiceImpl() {

    }

    @Transactional(rollbackFor = CadastroApostadorException.class)
    public Usuario cadastrarOuRelacionarApostadorUserProfile(
	    Connection<?> connection) throws CadastroApostadorException {
	checkNotNull(connection, "A conexao com o Servico nao pode ser nulo");

	Apostador apostador = this.construirApostadorComUserProfile(connection);

	try {
	    this.cadastrarApostadorUsuario(apostador, apostador.getUsuario()
		    .getSenha());
	} catch (UsuarioJaCadastradoException e) {
	    LOGGER.info(
		    "[cadastrarOuRelacionarApostadorUserProfile] Como o usuario {} ja existe, relacionamos o seu cadastro com o perfil.",
		    apostador.getEmail());
	    // vamos relacionar o perfil desse usuario com o nosso cadastro.
	    Usuario usuario = usuarioDAO.selecionarPorEmail(apostador
		    .getEmail());
	    usuario.setSocialUserId(connection.getKey().getProviderUserId());
	    usuario.setSocialNetwork(connection.getKey().getProviderId());
	    return usuario;
	}

	return apostador.getUsuario();
    }

    @Transactional(rollbackFor = CadastroApostadorException.class)
    public void cadastrarApostadorUsuario(Apostador apostador,
	    String confirmacaoSenha) throws CadastroApostadorException {
	LOGGER.debug(
		"[cadastrarApostadorUsuario] Tentando realizar o cadastro do apostador '{}'.",
		apostador);
	checkNotNull(apostador, "Apostador nao pode ser nulo");
	checkNotNull(emptyToNull(confirmacaoSenha),
		"Confirmacao de senha em branco!");

	Usuario usuario = apostador.getUsuario();

	if (usuarioDAO.selecionarPorEmail(usuario.getEmail()) == null) {
	    this.checarConfirmacaoSenha(usuario, confirmacaoSenha);
	    this.confirirPoliticaSenha(usuario.getSenha());
	    UsuarioUtils.criptografarSenha(passwordEncryptor, usuario);

	    // todas opções padrão.
	    apostador.setOpcoes(new ApostadorOpcoes());
	    usuarioDAO.create(usuario);
	    apostadorDAO.create(apostador);

	    // TODO: enviar email de confirmação.
	    LOGGER.debug(
		    "[cadastrarApostadorUsuario] Apostador '{}' cadastrado com sucesso!",
		    usuario);
	} else {
	    LOGGER.warn(
		    "[cadastrarApostadorUsuario] Tentativa de cadastro de usuario com o email '{}'. Ja existe.",
		    usuario.getEmail());
	    throw new UsuarioJaCadastradoException(usuario.getEmail());
	}
    }

    @Override
    public boolean alterarSenhaApostadorUsuario(PasswordContext passwordContext)
	    throws CadastroApostadorException {
	LOGGER.debug(
		"[alterarSenhaApostadorUsuario] Tentando alterar a senha no contexto {}",
		passwordContext);
	if (passwordContext.hasToken()) {
	    LOGGER.debug("[alterarSenhaApostadorUsuario] Alteracao sendo realizada por token.");
	    final TokenLostPassword token = this
		    .validarTokenEsqueciMinhaSenha(passwordContext);
	    token.setUtilizado(true);
	    passwordContext.setEmailUsuario(token.getUsuario().getEmail());
	    tokenDAO.update(token);
	} else {
	    LOGGER.debug("[alterarSenhaApostadorUsuario] Alteracao sendo feita por meio da senha anterior.");
	    final String senhaAtual = usuarioDAO
		    .recuperarSenhaAtual(passwordContext.getEmailUsuario());

	    if (!this.passwordEncryptor.checkPassword(
		    passwordContext.getSenhaAtual(), senhaAtual)) {
		throw new CadastroApostadorException(
			MessagesCodes.SENHA_ATUAL_NAO_CONFERE);
	    }
	}

	checkArgument(
		!Strings.isNullOrEmpty(passwordContext.getEmailUsuario()),
		"Email do usuario em branco! Nao da pra alterar a senha.");

	this.checarConfirmacaoSenha(passwordContext.getNovaSenha(),
		passwordContext.getConfirmacaoSenha());
	this.confirirPoliticaSenha(passwordContext.getNovaSenha());

	usuarioDAO.alterarSenha(passwordContext.getEmailUsuario(), UsuarioUtils
		.criptografarSenha(passwordEncryptor,
			passwordContext.getNovaSenha()));
	LOGGER.debug("[alterarSenhaApostadorUsuario] Senha alterada com sucesso!");
	return true;
    }

    @Override
    public void enviarTokenEsqueciMinhaSenha(String emailUsuario)
	    throws UsuarioInexistenteException {
	checkNotNull(emptyToNull(emailUsuario), "Email do usuario obrigatorio");

	LOGGER.debug(
		"[enviarTokenEsqueciMinhaSenha] Tentando enviar o token de senha para {}",
		emailUsuario);

	final Usuario usuario = this.recuperarUsuario(emailUsuario);
	final TokenLostPassword token = TokenLostPassword.newInstance(usuario);

	tokenDAO.create(token);

	final Map<String, Object> contextoEmail = TemplateContextoBuilder
		.contextLinkBuilder(token.getToken());
	contextoEmail.put("usuario", usuario);

	notificacaoService.enviarNotificacao(new ContatoImpl(emailUsuario),
		TipoNotificacao.ESQUECI_SENHA, contextoEmail);

	LOGGER.debug(
		"[enviarTokenEsqueciMinhaSenha] Email para recuperacao de senha enviado para {}",
		emailUsuario);
    }

    private TokenLostPassword validarTokenEsqueciMinhaSenha(
	    PasswordContext context) throws TokenSenhaInvalidoException {
	final TokenLostPassword tokenData = tokenDAO.findById(context
		.getTokenSenha());

	if (tokenData == null || !tokenData.isValido()) {
	    throw new TokenSenhaInvalidoException(context.getTokenSenha());
	}

	return tokenData;
    }

    @Override
    public void validarTokenEsqueciMinhaSenha(String token)
	    throws TokenSenhaInvalidoException {
	this.validarTokenEsqueciMinhaSenha(new PasswordContext(token));
    }

    /**
     * Recupera o usuário com o email em questão
     * 
     * @param email
     * @return instância do usuário
     * @throws UsuarioInexistenteException
     *             caso não encontre.
     */
    private Usuario recuperarUsuario(String email)
	    throws UsuarioInexistenteException {
	final Usuario usuario = usuarioDAO.selecionarPorEmail(email);

	if (usuario == null) {
	    throw new UsuarioInexistenteException(email);
	}

	return usuario;
    }

    /**
     * A partir de {@link UserProfile} constrói um novo {@link Apostador}
     * 
     * @param userProfile
     * @return
     * @throws CadastroApostadorException
     *             caso nao seja encontrado um email no profile.
     */
    private Apostador construirApostadorComUserProfile(
	    final Connection<?> connection) throws CadastroApostadorException {
	final Apostador apostador = new Apostador();
	final UserProfile userProfile = connection.fetchUserProfile();
	checkNotNull(userProfile,
		"Nao foi possivel recuperar o UserProfile, impossivel continuar.");

	if (Strings.isNullOrEmpty(userProfile.getName())) {
	    apostador.setNome(String.format("%s %s",
		    userProfile.getFirstName(), userProfile.getLastName()));
	} else {
	    apostador.setNome(userProfile.getName());
	}

	final Usuario usuario = new Usuario(userProfile.getEmail());
	usuario.setSocialUserId(connection.getKey().getProviderUserId());
	usuario.setSocialNetwork(connection.getKey().getProviderId());
	// senha para passar pela política de senha. :)
	usuario.setSenha(RandomStringUtils.randomAlphanumeric(10));

	apostador.setUsuario(usuario);

	if (Strings.isNullOrEmpty(usuario.getEmail())) {
	    throw new CadastroApostadorException(
		    MessagesCodes.EMAIL_REDE_SOCIAL_INEXISTENTE);
	}

	return apostador;
    }

    /*
     * TODO: passar os metodos abaixo para UsuarioUtils assim que o
     * messageSource não for mais obrigado na exception.
     */

    /**
     * Confere a política de senha.
     * <p/>
     * Atualmente utilizamos o seguinte
     * <code>regexp: (?=.{6,})(?=.*[a-zA-Z])(?=.*[0-9]).*</code>.
     * <p/>
     * Isso significa ao menos 6 caraterers com dígitos e letras maiúsculas ou
     * minúsculas.
     * 
     * @param senha
     *            a ser conferida
     * @throws CadastroApostadorException
     *             caso a <code>regexp</code> não seja atendida.
     * @see <a href="http://stackoverflow.com/a/9922150">Regex for password
     *      policy</a>
     */
    protected void confirirPoliticaSenha(final String senha)
	    throws CadastroApostadorException {
	Pattern p = Pattern.compile("(?=.{6,})" + // "" followed by 6+ symbols
		"(?=.*[a-zA-Z])" + // --- ' ' --- at least 1 lower or upper
		// "(?=.*[A-Z])" + // --- ' ' --- at least 1 upper
		"(?=.*[0-9])" + // --- ' ' --- at least 1 digit
		// "(?=.*\\p{Punct})"+ // --- ' ' --- at least 1 symbol
		".*"); // the actual characters

	if (!p.matcher(senha).matches()) {
	    throw new CadastroApostadorException(messageSource,
		    MessagesCodes.SENHA_FAIL_POLITICA);
	}
    }

    protected void checarConfirmacaoSenha(final Usuario usuario,
	    final String confirmacaoSenha) throws CadastroApostadorException {
	this.checarConfirmacaoSenha(usuario.getSenha(), confirmacaoSenha);
    }

    protected void checarConfirmacaoSenha(final String senha,
	    final String confirmacaoSenha) throws CadastroApostadorException {
	if (!confirmacaoSenha.equals(senha)) {
	    throw new CadastroApostadorException(messageSource,
		    MessagesCodes.SENHA_NAO_CONFERE);
	}
    }

}
