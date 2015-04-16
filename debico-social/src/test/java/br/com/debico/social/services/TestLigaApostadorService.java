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

import br.com.debico.model.Apostador;
import br.com.debico.social.CadastroLigaException;
import br.com.debico.social.InscricaoLigaException;
import br.com.debico.social.model.Liga;
import br.com.debico.social.model.LigaApostadorLite;
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
public class TestLigaApostadorService extends AbstractUnitTest {

	@Inject
	private LigaApostadorService ligaApostadorService;

	@Inject
	private LigaService ligaService;

	@Test(expected=InscricaoLigaException.class)
	public void testInscreverApostador_Existente() throws CadastroLigaException {
		final Liga liga = ligaService.cadastrarNovaLiga("Liga da Justiça",
				TestConstants.EMAIL_CARGA);
		assertTrue(ligaApostadorService
				.inscreverApostador(new LigaApostadorLite(liga.getId(),
						TestConstants.IDS_APOSTADORES[0])));
		// denovo?
		ligaApostadorService.inscreverApostador(new LigaApostadorLite(liga
				.getId(), TestConstants.IDS_APOSTADORES[0]));
	}

	@Test
	public void testInscreverApostador() throws CadastroLigaException {
		// @formatter:off
		final Liga liga = ligaService.cadastrarNovaLiga("Liga da Justiça", TestConstants.EMAIL_CARGA);
		assertTrue(ligaApostadorService.inscreverApostador(new LigaApostadorLite(liga.getId(), TestConstants.IDS_APOSTADORES[0])));
		
		List<Apostador> apostadores = ligaApostadorService.consultarApostadores(liga.getId());
		// @formatter:on

		assertNotNull(apostadores);
		assertFalse(apostadores.isEmpty());
		assertThat(apostadores.size(), IsEqual.equalTo(2));

	}

	@Test
	public void testRemoverApostador() throws CadastroLigaException {
		// @formatter:off
		Liga liga = ligaService.cadastrarNovaLiga("Liga da Justiça", TestConstants.EMAIL_CARGA);
		ligaApostadorService.inscreverApostador(new LigaApostadorLite(liga.getId(), TestConstants.IDS_APOSTADORES[0]));
		ligaApostadorService.removerApostador(new LigaApostadorLite(liga.getId(), TestConstants.IDS_APOSTADORES[0]));
		
		List<Apostador> apostadores = ligaApostadorService.consultarApostadores(liga.getId());
		// @formatter:on

		assertNotNull(apostadores);
		assertFalse(apostadores.isEmpty());
		assertThat(apostadores.size(), IsEqual.equalTo(1));
	}

}
