package br.com.debico.social.services;

import java.util.List;

import javax.inject.Inject;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.social.CadastroLigaException;
import br.com.debico.social.model.Liga;
import br.com.debico.social.spring.SocialConfig;
import br.com.debico.test.TestConstants;
import br.com.debico.test.spring.AbstractUnitTest;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SocialConfig.class })
public class TestLigaService extends AbstractUnitTest {
	
	@Inject
	private LigaService ligaService;

	@Test
	public void testCadastrarNovaLiga() throws CadastroLigaException {
		Liga liga = ligaService.cadastrarNovaLiga("Liga da Justiça", TestConstants.EMAIL_CARGA);
		
		assertNotNull(liga);
		assertTrue(liga.getId() > 0);
	}
	
	@Test
	public void testConsultarLiga() throws CadastroLigaException {
		ligaService.cadastrarNovaLiga("Liga da Justiça", TestConstants.EMAIL_CARGA);
		final List<Liga> liga = ligaService.consultarLiga(TestConstants.EMAIL_CARGA);
		assertNotNull(liga);
		assertFalse(liga.isEmpty());
	}
	
	@Test
	public void testConsultarLiga_Vazia() throws CadastroLigaException {
		final List<Liga> liga = ligaService.consultarLiga(TestConstants.EMAIL_CARGA);
		assertNotNull(liga);
		assertTrue(liga.isEmpty());
	}
	
	@Test
	public void testAtualizarLiga() throws CadastroLigaException {
		final Liga liga = ligaService.cadastrarNovaLiga("Liga da Justiça", TestConstants.EMAIL_CARGA);
		
		final Liga vingadores = ligaService.atualizarLiga(liga.getId(), "Vingadores", TestConstants.EMAIL_CARGA);
		
		assertThat(vingadores.getNome(), IsEqual.equalTo("Vingadores"));
		assertThat(vingadores.getPermalink(), IsEqual.equalTo("vingadores"));
	}

}
