package br.com.debico.social.services;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.inject.Inject;
import javax.mail.MessagingException;

import org.jasypt.util.password.PasswordEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.mock_javamail.Mailbox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.model.Apostador;
import br.com.debico.social.CadastroApostadorException;
import br.com.debico.social.model.PasswordContext;
import br.com.debico.social.spring.SocialConfig;
import br.com.debico.test.TestConstants;
import br.com.debico.test.spring.AbstractUnitTest;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SocialConfig.class })
public class TestUsuarioService extends AbstractUnitTest {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(TestUsuarioService.class);

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private ApostadorService apostadorService;

    @Inject
    private PasswordEncryptor encryptor;

    @Test
    public void testAlterarSenhaAutenticado() throws CadastroApostadorException {
	final PasswordContext passwordContext = new PasswordContext();
	passwordContext.setEmailUsuario(TestConstants.EMAIL_CARGA);
	passwordContext.setSenhaAtual("vedder2468");
	passwordContext.setNovaSenha("P@ssw0rd");
	passwordContext.setConfirmacaoSenha("P@ssw0rd");

	assertTrue(usuarioService.alterarSenhaApostadorUsuario(passwordContext));
	final Apostador apostador = apostadorService
		.selecionarPerfilApostadorPorEmail(TestConstants.EMAIL_CARGA);

	assertTrue(encryptor.checkPassword("P@ssw0rd", apostador.getUsuario()
		.getSenha()));
    }

    @Test(expected = CadastroApostadorException.class)
    public void testAlterarSenhaAutenticado_ConfirmacaoErrada()
	    throws CadastroApostadorException {
	final PasswordContext passwordContext = new PasswordContext();
	passwordContext.setEmailUsuario(TestConstants.EMAIL_CARGA);
	passwordContext.setSenhaAtual("vedder2468");
	passwordContext.setNovaSenha("P@ssw0rd");
	passwordContext.setConfirmacaoSenha("P@ssw0rd1");

	usuarioService.alterarSenhaApostadorUsuario(passwordContext);
    }

    @Test(expected = CadastroApostadorException.class)
    public void testAlterarSenhaAutenticado_SenhaAtualErrada()
	    throws CadastroApostadorException {
	final PasswordContext passwordContext = new PasswordContext();
	passwordContext.setEmailUsuario(TestConstants.EMAIL_CARGA);
	passwordContext.setSenhaAtual("vedder242");
	passwordContext.setNovaSenha("P@ssw0rd");
	passwordContext.setConfirmacaoSenha("P@ssw0rd");

	usuarioService.alterarSenhaApostadorUsuario(passwordContext);
    }

    @Test
    public void testEnviarTokenSenha() throws MessagingException, IOException, CadastroApostadorException {
	usuarioService.enviarTokenEsqueciMinhaSenha(TestConstants.EMAIL_CARGA);
	final Mailbox mailbox = Mailbox.get(TestConstants.EMAIL_CARGA);

	assertThat(mailbox.getNewMessageCount(), greaterThan(0));
	assertThat(mailbox.get(0).getSubject(), containsString("senha"));
	assertThat(mailbox.get(0).getContent().toString(),
		containsString("token"));

	final String msgBody = mailbox.get(0).getContent().toString();
	final int indexOfToken = msgBody.indexOf("token=");
	final String token = msgBody.substring(indexOfToken + 6,
		indexOfToken + 42);

	// no contexto do token nós, clientes, não temos o email do usuário
	final PasswordContext context = new PasswordContext();
	context.setConfirmacaoSenha("teste123");
	context.setNovaSenha("teste123");
	context.setTokenSenha(token);

	LOGGER.info("Mensagem {}", msgBody);
	LOGGER.info("Token {}", token);
	
	assertTrue(usuarioService.alterarSenhaApostadorUsuario(context));
    }

}
