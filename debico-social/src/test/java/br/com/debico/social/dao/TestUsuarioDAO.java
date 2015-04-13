package br.com.debico.social.dao;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.model.Usuario;
import br.com.debico.social.spring.SocialConfig;
import br.com.debico.test.spring.AbstractUnitTest;
import br.com.debico.test.spring.ServicesUnitTestConfig;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SocialConfig.class,
        ServicesUnitTestConfig.class })
public class TestUsuarioDAO extends AbstractUnitTest {

    @Inject
    private UsuarioDAO usuarioDAO;

    @Test
    public void testInserir() {
        Usuario apostador = new Usuario("bruce@avengers.com");
        usuarioDAO.create(apostador);

        Usuario bruce = usuarioDAO.selecionarPorEmail("bruce@avengers.com");
        assertNotNull(bruce);
        assertTrue(bruce.getId() > 0);
    }

}
