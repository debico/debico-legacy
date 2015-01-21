package br.com.debico.campeonato.brms.impl;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.PontuacaoTimeDAO;
import br.com.debico.campeonato.test.support.AbstractCampeonatoUnitTest;
import br.com.debico.campeonato.test.support.TimesID;
import br.com.debico.core.brms.BRMSExecutor;
import br.com.debico.model.campeonato.PontuacaoTime;
import br.com.debico.model.campeonato.StatusPosicao;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class TestCalculoPartidasService extends AbstractCampeonatoUnitTest {
	
	@Inject
	public BRMSExecutor brmsExecutor;
	
	@Inject
	public PontuacaoTimeDAO pontuacaoTimeDAO;

	@Test
	public void testProcessar_posicaoTimes() {	
		List<PontuacaoTime> ranking = pontuacaoTimeDAO.selecionarPontuacaoCampeonato(CAMPEONATO);

		ranking.get(0).setPontos(10);
		ranking.get(0).setVitorias(4);
		ranking.get(0).setSaldoGols(3);
		ranking.get(0).setGolsPro(2);
		
		ranking.get(1).setPontos(10);
		ranking.get(1).setVitorias(5);
		ranking.get(1).setSaldoGols(2);
		ranking.get(1).setGolsPro(2);
		
		brmsExecutor.processar("calcula_posicao_times", ranking, Collections.singletonList(CAMPEONATO.getParametrizacao()));
		
		Collections.sort(ranking);
		
		LOGGER.info("Ranking apos processar: {}", ranking);
		
		PontuacaoTime primeiroColocado = ranking.get(0);
		PontuacaoTime segundoColocado = ranking.get(1);
		
		// primeira vez ninguem realmente altera a posicao
		assertSame(StatusPosicao.MANTEVE, primeiroColocado.getStatusPosicao());
		assertSame(StatusPosicao.MANTEVE, segundoColocado.getStatusPosicao());

		ranking.get(2).setPontos(11);
		
		brmsExecutor.processar("calcula_posicao_times", ranking, Collections.singletonList(CAMPEONATO.getParametrizacao()));
		
		Collections.sort(ranking);
		
		LOGGER.info("Ranking apos processar: {}", ranking);
		assertSame(StatusPosicao.DESCEU, primeiroColocado.getStatusPosicao());
		assertSame(StatusPosicao.DESCEU, segundoColocado.getStatusPosicao());
		assertSame(StatusPosicao.SUBIU, ranking.get(0).getStatusPosicao());
	}
	
	@Test
	public void testProcessar_posicaoTimesDesempate() {
		List<PontuacaoTime> ranking = pontuacaoTimeDAO.selecionarPontuacaoCampeonato(CAMPEONATO);
		
		Collections.sort(ranking);
		
		LOGGER.info("Ranking antes de processar: {}", ranking);
		
		ranking.get(0).setPontos(10);
		ranking.get(0).setVitorias(4);
		ranking.get(0).setSaldoGols(3);
		ranking.get(0).setGolsPro(2);
		
		ranking.get(1).setPontos(10);
		ranking.get(1).setVitorias(5);
		ranking.get(1).setSaldoGols(2);
		ranking.get(1).setGolsPro(2);
		
		ranking.get(2).setPontos(10);
		ranking.get(2).setVitorias(5);
		ranking.get(2).setSaldoGols(3);
		ranking.get(2).setGolsPro(1);
		
		ranking.get(3).setPontos(10);
		ranking.get(3).setVitorias(5);
		ranking.get(3).setSaldoGols(3);
		ranking.get(3).setGolsPro(2);
		
		ranking.get(4).setPontos(10);
		ranking.get(4).setVitorias(5);
		ranking.get(4).setSaldoGols(3);
		ranking.get(4).setGolsPro(2);
		
		brmsExecutor.processar("calcula_posicao_times", ranking, Collections.singletonList(CAMPEONATO.getParametrizacao()));
		
		Collections.sort(ranking);
		
		LOGGER.info("Ranking apos processar: {}", ranking);
		
		assertSame(TimesID.BAH, ranking.get(0).getTime().getId());
		assertSame(TimesID.VIT, ranking.get(1).getTime().getId());
		assertSame(TimesID.FIG, ranking.get(2).getTime().getId());
		assertSame(TimesID.CRI, ranking.get(3).getTime().getId());
		assertSame(TimesID.CRU, ranking.get(4).getTime().getId());
		
		assertTrue(ranking.get(0).getStatusClassificacao().isElite());
		assertTrue(ranking.get(1).getStatusClassificacao().isElite());
		assertTrue(ranking.get(2).getStatusClassificacao().isElite());
		assertTrue(ranking.get(3).getStatusClassificacao().isElite());
		assertFalse(ranking.get(4).getStatusClassificacao().isElite());
	}

}
