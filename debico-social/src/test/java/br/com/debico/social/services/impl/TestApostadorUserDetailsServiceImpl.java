package br.com.debico.social.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.model.Apostador;
import br.com.debico.model.Usuario;
import br.com.debico.social.CadastroApostadorException;
import br.com.debico.social.services.UsuarioService;
import br.com.debico.social.spring.SocialConfig;
import br.com.debico.test.spring.AbstractUnitTest;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SocialConfig.class })
public class TestApostadorUserDetailsServiceImpl extends AbstractUnitTest {

    @Inject
    private UserDetailsService userDetailsService;
    
    @Inject
    private UsuarioService usuarioService;

    @Test
    public void testLoadUserByUsername() throws CadastroApostadorException {
	final String email = "peter.parker@oscorp.com";
	Usuario usuario = new Usuario("peter.parker@oscorp.com");
	usuario.setSenha("IhateOctopus666");

	Apostador apostador = new Apostador("Peter Parker", usuario);

	usuarioService.cadastrarApostadorUsuario(apostador, "IhateOctopus666");

	UserDetails userDetails = userDetailsService.loadUserByUsername(email);

	assertNotNull(userDetails);
	assertEquals(email, userDetails.getUsername());
	// esta criptografado.
	assertTrue(userDetails.getPassword().length() == 64);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsername_NotExists() {
	final String email = "peter.parker@oscorp.com";

	userDetailsService.loadUserByUsername(email);
    }
}
