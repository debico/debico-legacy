package br.com.debico.campeonato.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.CampeonatoDAO;
import br.com.debico.campeonato.services.CampeonatoPontosCorridosService;
import br.com.debico.campeonato.test.support.AbstractCampeonatoUnitTest;
import br.com.debico.core.DebicoException;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.Time;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.model.campeonato.PontuacaoTime;
import br.com.debico.model.campeonato.Rodada;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class TestCampeonatoPontosCorridosServiceImpl extends AbstractCampeonatoUnitTest {

	@Inject
	private CampeonatoPontosCorridosService campeonatoPontosCorridosService;
	
	@Inject
	private CampeonatoDAO campeonatoDAO;

	@Test
	public void testDefinirFaseUnica() {
		CampeonatoPontosCorridos brasileirao = new CampeonatoPontosCorridos("Marvel Super Teams 2014");

		for (int i = 1; i <= CampeonatoPontosCorridosService.QUANTIDADE_TIMES_PADRAO; i++) {
			brasileirao.addTime(new Time("Time" + i));
		}

		campeonatoDAO.create(brasileirao);

		campeonatoPontosCorridosService.definirFaseUnica(brasileirao);

		List<PontuacaoTime> ranking = campeonatoPontosCorridosService.selecionarTabela(brasileirao);

		assertNotNull(ranking);
		assertTrue(ranking.size() == CampeonatoPontosCorridosService.QUANTIDADE_TIMES_PADRAO);

		LOGGER.info("Ranking do {}: {} ", brasileirao, ranking);
	}

	@Test
	public void testSelecionarRodada() throws DebicoException {
	    final Rodada rodada = campeonatoPontosCorridosService.selecionarRodadaAtual(CAMPEONATO);
	    
		assertNotNull(rodada);
		
		final List<PartidaRodada> partidas = campeonatoPontosCorridosService.selecionarPartidasRodada(rodada.getId());
		
		assertNotNull(partidas);
		assertFalse(partidas.isEmpty());
	}

}
