package br.com.debico.social.services;

import javax.inject.Inject;

import org.jasypt.util.password.PasswordEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.junit.Assert.assertTrue;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SocialConfig.class })
public class TestUsuarioService extends AbstractUnitTest {

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

        assertTrue(encryptor.checkPassword("P@ssw0rd", apostador.getUsuario().getSenha()));
    }

    @Test(expected=CadastroApostadorException.class)
    public void testAlterarSenhaAutenticado_ConfirmacaoErrada() throws CadastroApostadorException {
        final PasswordContext passwordContext = new PasswordContext();
        passwordContext.setEmailUsuario(TestConstants.EMAIL_CARGA);
        passwordContext.setSenhaAtual("vedder2468");
        passwordContext.setNovaSenha("P@ssw0rd");
        passwordContext.setConfirmacaoSenha("P@ssw0rd1");

        usuarioService.alterarSenhaApostadorUsuario(passwordContext);
    }

    @Test(expected=CadastroApostadorException.class)
    public void testAlterarSenhaAutenticado_SenhaAtualErrada() throws CadastroApostadorException {
        final PasswordContext passwordContext = new PasswordContext();
        passwordContext.setEmailUsuario(TestConstants.EMAIL_CARGA);
        passwordContext.setSenhaAtual("vedder242");
        passwordContext.setNovaSenha("P@ssw0rd");
        passwordContext.setConfirmacaoSenha("P@ssw0rd");

        usuarioService.alterarSenhaApostadorUsuario(passwordContext);
    }
    
}
