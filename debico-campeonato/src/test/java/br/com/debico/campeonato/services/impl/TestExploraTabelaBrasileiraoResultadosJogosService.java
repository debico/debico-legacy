package br.com.debico.campeonato.services.impl;

import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.services.ExploraWebResultadosException;
import br.com.debico.campeonato.services.ExploraWebResultadosJogosService;
import br.com.debico.campeonato.test.support.AbstractCampeonatoUnitTest;
import br.com.debico.model.PartidaBase;
import br.com.debico.test.TestConstants;

@DirtiesContext
@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestExploraTabelaBrasileiraoResultadosJogosService extends AbstractCampeonatoUnitTest {

	@Inject
	private ExploraWebResultadosJogosService<PartidaBase> exploraService;

	@Override
	public void setUp() throws Exception {

		// nada
	}

	@Test
	public void testRecuperarPartidasFinalizadas() throws ExploraWebResultadosException {
		URL sitePesquisaURL = this.getClass().getResource("/web-crawl/tabelabrasileirao-2014.net.html");
		List<PartidaBase> partidas = exploraService.recuperarPartidasFinalizadas(TestConstants.CAMPEONATO_ID,
				sitePesquisaURL);

		assertNotNull(partidas);
		assertThat(partidas, not(emptyCollectionOf(PartidaBase.class)));
		// devemos ter todas as partidas da base, afinal o campeonato ja acabou!
		assertThat(partidas.size(), is(380));
	}

	@Test
	public void testRecuperarPartidas() throws ExploraWebResultadosException {
		URL sitePesquisaURL = this.getClass().getResource("/web-crawl/tabelabrasileirao-2014.net.html");
		List<PartidaBase> partidas = exploraService.recuperarPartidas(TestConstants.CAMPEONATO_ID, sitePesquisaURL);

		assertNotNull(partidas);
		assertThat(partidas, not(emptyCollectionOf(PartidaBase.class)));
		// devemos ter todas as partidas da base.
		assertThat(partidas.size(), is(380));
	}

}
