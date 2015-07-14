package br.com.debico.resultados.managers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.services.CampeonatoPontosCorridosService;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.model.campeonato.PontuacaoTime;
import br.com.debico.resultados.ResultadosConfig;
import br.com.debico.test.TestConstants;
import br.com.debico.test.spring.AbstractUnitTest;

@ActiveProfiles({ "dev", "embedded-jpa" })
@ContextConfiguration(classes = { ResultadosConfig.class })
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class TestPontosCorridosProcessorManager extends AbstractUnitTest {

    @Inject
    private CampeonatoProcessorManager<CampeonatoPontosCorridos> processorManager;

    @Inject
    private CampeonatoPontosCorridosService campeonatoPontosCorridosService;

    @Test
    public void testStart() {
	assertNotNull(processorManager);

	processorManager.start();

	// checagem do ranking dos times
	List<PontuacaoTime> tabela = campeonatoPontosCorridosService
		.selecionarTabela((CampeonatoPontosCorridos) campeonatoPontosCorridosService
			.selecionarCampeonato(TestConstants.CAMPEONATO_ID));

	assertNotNull(tabela);
	assertFalse(tabela.isEmpty());

	LOGGER.debug("Tabela processada: {}", tabela);

	assertTrue(tabela.get(0).getStatusClassificacao().isElite()); // top
	assertTrue(tabela.get(tabela.size() - 1).getStatusClassificacao()
		.isInferior()); // rebaixado
    }

}
