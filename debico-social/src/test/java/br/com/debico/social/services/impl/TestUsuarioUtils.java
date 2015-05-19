package br.com.debico.social.services.impl;

import javax.inject.Inject;

import org.jasypt.util.password.PasswordEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.model.Usuario;
import br.com.debico.social.spring.SocialConfig;
import br.com.debico.test.spring.AbstractUnitTest;
import static org.junit.Assert.assertNotEquals;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SocialConfig.class })
public class TestUsuarioUtils extends AbstractUnitTest {

    @Inject
    private PasswordEncryptor encryptor;

    @Test
    public void testCriptografarSenha() throws Exception {
        final String senha = "Ik1lloctopu5";
        Usuario peterparker = new Usuario("peter.parker@oscorp.com");
        peterparker.setSenha(senha);

        LOGGER.debug("senha: {}", peterparker.getSenha());
        UsuarioUtils.criptografarSenha(encryptor, peterparker);
        assertNotEquals(senha, peterparker.getSenha());
        LOGGER.debug("senha criptografada: {}", peterparker.getSenha());
    }

}
