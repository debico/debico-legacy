package br.com.debico.social.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
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
import br.com.debico.model.Time;
import br.com.debico.model.Usuario;
import br.com.debico.social.CadastroApostadorException;
import br.com.debico.social.services.ApostadorService;
import br.com.debico.social.services.UsuarioService;
import br.com.debico.social.spring.SocialConfig;
import br.com.debico.test.spring.AbstractUnitTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SocialConfig.class })
public class TestApostadorServiceImpl extends AbstractUnitTest {

	@Inject
	private ApostadorService apostadorService;

	@Inject
	private UsuarioService usuarioService;

	protected static final String EMAIL_PRIMEIRO_RANKING = "abacafrehley@gmail.com";
	protected static final String EMAIL_ULTIMO_RANKING = "fhbernardo@yahoo.com.br";

	@Before
	public void setUp() throws Exception {
		apostadorService = getTargetObject(apostadorService,
				ApostadorServiceImpl.class);
	}

	@Test
	public void testPesquisarApostadorPorNome() {
		List<Apostador> apostadores = apostadorService
				.pesquisarApostadoresPorParteNome("Mar");

		assertNotNull(apostadores);
		assertFalse(apostadores.isEmpty());
	}

	@Test
	public void testAtualizarApostador() throws CadastroApostadorException {
		Apostador apostador = apostadorService
				.selecionarPerfilApostadorPorEmail(EMAIL_PRIMEIRO_RANKING);

		apostador.setNome("Peter Parker");
		apostador.getOpcoes().setLembretePalpites(false);
		apostador.getOpcoes().setTimeCoracao(null);

		apostadorService.atualizarApostador(apostador);

		Apostador newApostador = apostadorService
				.selecionarPerfilApostadorPorEmail(EMAIL_PRIMEIRO_RANKING);

		assertEquals("Peter Parker", newApostador.getNome());
		assertFalse(newApostador.getOpcoes().isLembretePalpites());
		assertFalse(newApostador.possuiTimeCoracao());
	}

	@Test
	public void testAtualizarApostadorTimeCoracao()
			throws CadastroApostadorException {
		Apostador apostador = apostadorService
				.selecionarPerfilApostadorPorEmail(EMAIL_PRIMEIRO_RANKING);

		apostador.setNome("Peter Parker");
		apostador.getOpcoes().setLembretePalpites(false);
		apostador.getOpcoes().setTimeCoracao(new Time(1, "Time"));

		apostadorService.atualizarApostador(apostador);

		Apostador newApostador = apostadorService
				.selecionarPerfilApostadorPorEmail(EMAIL_PRIMEIRO_RANKING);

		assertEquals("Peter Parker", newApostador.getNome());
		assertFalse(newApostador.getOpcoes().isLembretePalpites());
		assertTrue(newApostador.possuiTimeCoracao());
		assertTrue(newApostador.getOpcoes().getTimeCoracao().getId() == 1);

	}

	@Test
	public void testLoadUserByUsername() throws CadastroApostadorException {
		final String email = "peter.parker@oscorp.com";
		Usuario usuario = new Usuario("peter.parker@oscorp.com");
		usuario.setSenha("IhateOctopus666");

		Apostador apostador = new Apostador("Peter Parker", usuario);

		usuarioService.cadastrarApostadorUsuario(apostador, "IhateOctopus666");

		UserDetails userDetails = ((UserDetailsService) usuarioService)
				.loadUserByUsername(email);

		assertNotNull(userDetails);
		assertEquals(email, userDetails.getUsername());
		// esta criptografado.
		assertTrue(userDetails.getPassword().length() == 64);
	}

	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUsername_NotExists() {
		final String email = "peter.parker@oscorp.com";

		((UserDetailsService) usuarioService).loadUserByUsername(email);
	}

	@Test(expected = CadastroApostadorException.class)
	public void testCadastrarApostador_existente()
			throws CadastroApostadorException {
		Usuario usuario = new Usuario("peter.parker@oscorp.com");
		usuario.setSenha("IhateOctopus666");

		Apostador apostador = new Apostador("Peter Parker", usuario);

		usuarioService.cadastrarApostadorUsuario(apostador, "IhateOctopus666");

		// denovo!
		Apostador apostador2 = new Apostador("Peter Parker2", usuario);

		usuarioService.cadastrarApostadorUsuario(apostador2, "IhateOctopus666");
	}

	@Test
	public void testConfirirPoliticaSenhaOK() throws Exception {
		final String senhaForte = "pEterp4rker";

		// OK
		super.getTargetObject(usuarioService, UsuarioServiceImpl.class)
				.confirirPoliticaSenha(senhaForte);
	}

	@Test(expected = CadastroApostadorException.class)
	public void testConfirirPoliticaSenhaNOK() throws Exception {
		final String senha = "peterparker";

		// Ops!
		super.getTargetObject(usuarioService, UsuarioServiceImpl.class)
				.confirirPoliticaSenha(senha);
	}

	@Test
	public void testCriptografarSenha() throws Exception {
		final String senha = "Ik1lloctopu5";
		Usuario peterparker = new Usuario("peter.parker@oscorp.com");
		peterparker.setSenha(senha);

		LOGGER.debug("senha: {}", peterparker.getSenha());
		super.getTargetObject(usuarioService, UsuarioServiceImpl.class)
				.criptografarSenha(peterparker);
		assertNotEquals(senha, peterparker.getSenha());
		LOGGER.debug("senha criptografada: {}", peterparker.getSenha());
	}

}
