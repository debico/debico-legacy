package br.com.debico.social.services.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.model.Usuario;
import br.com.debico.social.CadastroApostadorException;
import br.com.debico.social.services.UsuarioService;

/**
 * Serviço que efetua o cadastro de um usuário por meio de integração com as
 * Redes Sociais.
 * <p/>
 * É utilizado pelo `JdbcUsersConnectionRepository` para relacionar o profile
 * com o nosso cadastro de domínio.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.5
 */
@Named
@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
class ApostadorConnectionSignUp implements ConnectionSignUp {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(ApostadorConnectionSignUp.class);

    @Inject
    private UsuarioService usuarioService;

    public ApostadorConnectionSignUp() {

    }

    @Override
    public String execute(Connection<?> connection) {
	checkNotNull(connection, "Referencia da conexao com a rede social nulo");

	try {
	    Usuario usuario = usuarioService
		    .cadastrarOuRelacionarApostadorUserProfile(connection);
	    return String.valueOf(usuario.getId());
	} catch (CadastroApostadorException e) {
	    LOGGER.warn(
		    "[execute] Problema ao cadastrar o usuario utilizando o profile {}",
		    connection.fetchUserProfile());
	    return null;
	}
    }
}
