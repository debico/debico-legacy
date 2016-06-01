package br.com.debico.resultados.processors;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;

import br.com.debico.campeonato.CampeonatoBeans;
import br.com.debico.campeonato.services.CampeonatoService;
import br.com.debico.core.DebicoException;
import br.com.debico.model.campeonato.Rodada;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.ContextImpl;
import br.com.debico.resultados.Processor;
import br.com.debico.resultados.ProcessorBeans;
import br.com.debico.resultados.config.ResultadosConfig;
import br.com.debico.test.TestConstants;
import br.com.debico.test.spring.AbstractUnitTest;

@ContextConfiguration(classes = { ResultadosConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSumarizaEstatisticaRodadaApostadorProcessor extends AbstractUnitTest {

	@Inject
	@Named(ProcessorBeans.SUMARIZA_PONTOS_APOSTADOR_RODADA)
	private Processor processor;

	@Inject
	@Named(CampeonatoBeans.CAMPEONATO_SERVICE)
	private CampeonatoService campeonatoService;

	@Test
	public void testExecute() throws DebicoException {
		final Context context = new ContextImpl(campeonatoService.selecionarCampeonato(TestConstants.CAMPEONATO_ID));
		context.setRodadas(Lists.newArrayList(new Rodada(77)));

		processor.execute(context);
		processor.execute(context);
	}

}
