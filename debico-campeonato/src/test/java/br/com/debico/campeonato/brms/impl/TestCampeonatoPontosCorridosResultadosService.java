package br.com.debico.campeonato.brms.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.CampeonatoPontosCorridosService;
import br.com.debico.campeonato.RodadaService;
import br.com.debico.campeonato.brms.ResultadosService;
import br.com.debico.campeonato.test.support.AbstractCampeonatoUnitTest;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.model.campeonato.PontuacaoTime;
import br.com.debico.model.campeonato.Rodada;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(defaultRollback = true)
public class TestCampeonatoPontosCorridosResultadosService extends AbstractCampeonatoUnitTest {

	@Inject
	@Named("campeonatoPontosCorridosResultadosService")
	private ResultadosService<CampeonatoPontosCorridos> resultadosService;
	
    @Inject
    private CampeonatoPontosCorridosService campeonatoPontosCorridosService;
	
	@Inject
	private RodadaService rodadaService; 
	
	@Test
	public void testProcessarRodada() {
		// processamento das rodadas. uma a uma é melhro para testar do que #processar(campeonato);
		List<Rodada> rodadas = rodadaService.selecionarRodadasNaoCalculadas(CAMPEONATO);
		
		for (Rodada rodada : rodadas) {
			List<PartidaRodada> partidas = resultadosService.processar(CAMPEONATO, rodada);
			
			assertNotNull(partidas);
			assertFalse(partidas.isEmpty());	
		}
		
		// checagem do ranking dos times
		List<PontuacaoTime> tabela = campeonatoPontosCorridosService.selecionarTabela(CAMPEONATO);

		assertNotNull(tabela);
        assertFalse(tabela.isEmpty());
        
        LOGGER.debug("Tabela processada: {}", tabela);
        
        assertTrue(tabela.get(0).getStatusClassificacao().isElite()); // top
        assertTrue(tabela.get(tabela.size()-1).getStatusClassificacao().isInferior()); // rebaixado
	}

}
