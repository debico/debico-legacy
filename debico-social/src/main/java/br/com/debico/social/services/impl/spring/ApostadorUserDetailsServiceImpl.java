package br.com.debico.social.services.impl.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.core.MessagesCodes;
import br.com.debico.model.Apostador;
import br.com.debico.model.Usuario;
import br.com.debico.social.dao.ApostadorDAO;
import br.com.debico.social.dao.UsuarioDAO;
import br.com.debico.social.model.ApostadorUserDetails;

/**
 * Implementação de {@link UserDetailsService} do Spring Security para tratar do
 * processo de autenticação e autorização da App. Criação da classe em virtude
 * da refatoração dos casos de uso de usuário.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.2
 */
@Named
@Transactional(readOnly = false)
class ApostadorUserDetailsServiceImpl implements UserDetailsService, SocialUserDetailsService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ApostadorUserDetailsServiceImpl.class);

	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	private ApostadorDAO apostadorDAO;

	@Inject
	@Named("resourceBundleMessageSource")
	private MessageSource messageSource;

	public ApostadorUserDetailsServiceImpl() {

	}

	/**
	 * Método utilizado pelos serviço do Spring Social Jdbc que relaciona os
	 * dados da rede social com o usuário de sistema. Por essa razão ele envia
	 * um String que para nós é um Int.
	 */
	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
		LOGGER.debug("[loadUserByUserId] Tentando carregar o usuario pelo Id da rede Social '{}'.", userId);

		Apostador apostador = apostadorDAO
				.selecionarPorIdUsuario(Integer.parseInt(StringUtils.defaultString(userId, "0")));

		return this.validarConstruirUsuario(apostador, userId);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("[loadUserByUsername] Tentando carregar o usuario '{}'.", username);

		Apostador apostador = apostadorDAO.selecionarPorEmail(username);

		return this.validarConstruirUsuario(apostador, username);
	}

	private ApostadorUserDetails validarConstruirUsuario(final Apostador apostador, String username) {
		if (apostador == null || apostador.getUsuario() == null) {
			throw new UsernameNotFoundException(messageSource.getMessage(MessagesCodes.USUARIO_NAO_ENCONTRADO,
					new Object[] { username }, Locale.getDefault()));
		}

		Usuario usuario = apostador.getUsuario();
		usuario.setUltimoLogin(new Date());

		usuarioDAO.update(usuario);

		LOGGER.debug("[loadUserByUsername] Apostador com o usuario '{}' carregado.", usuario);
		return construirUsuario(apostador);
	}

	/**
	 * Constrói a estrutura base de um {@link User} de acordo com a
	 * especificação do Spring Security.
	 * 
	 * @param usuario
	 * @return
	 */
	private ApostadorUserDetails construirUsuario(final Apostador apostador) {
		// @formatter:off
		final ApostadorUserDetails user = new ApostadorUserDetails(apostador.getUsuario().getUserName(),
				apostador.getUsuario().getSenha(), construirPerfil(apostador.getUsuario()));
		// @formatter:on
		user.setId(apostador.getUsuario().getId());
		user.setName(apostador.getNome());
		user.setIdApostador(apostador.getId());
		user.setUserId(apostador.getUsuario().getSocialUserId());

		return user;
	}

	/**
	 * Constrói a estrutura de perfil de acordo com a especificação do Spring
	 * Security.
	 * 
	 * @param usuario
	 * @return
	 */
	private List<GrantedAuthority> construirPerfil(final Usuario usuario) {
		List<GrantedAuthority> perfis = new ArrayList<GrantedAuthority>();
		perfis.add(new SimpleGrantedAuthority(usuario.getPerfil()));

		return perfis;
	}

}
