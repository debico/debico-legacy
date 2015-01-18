package br.com.debico.social.dao;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.model.Usuario;
import br.com.debico.test.spring.AbstractUnitTest;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUsuarioDAO extends AbstractUnitTest {

	@Inject
	private UsuarioDAO usuarioDAO;

	@Test
	public void testInserir() {
		Usuario apostador = new Usuario("bruce@avengers.com");
		usuarioDAO.inserir(apostador);

		Usuario bruce = usuarioDAO.selecionarPorEmail("bruce@avengers.com");
		assertNotNull(bruce);
		assertTrue(bruce.getId() > 0);
	}

}
