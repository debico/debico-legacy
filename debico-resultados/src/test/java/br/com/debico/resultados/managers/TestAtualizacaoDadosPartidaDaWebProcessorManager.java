package br.com.debico.resultados.managers;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.debico.campeonato.CampeonatoBeans;
import br.com.debico.campeonato.services.CampeonatoService;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.resultados.ManagerBeans;
import br.com.debico.resultados.ProcessorManager;
import br.com.debico.resultados.config.ResultadosConfig;
import br.com.debico.test.TestConstants;
import br.com.debico.test.spring.AbstractUnitTest;

@ActiveProfiles(profiles = { TestConstants.PROFILE_DEV,
	TestConstants.PROFILE_DB })
@ContextConfiguration(classes = { ResultadosConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestAtualizacaoDadosPartidaDaWebProcessorManager extends
	AbstractUnitTest {

    @Inject
    @Named(ManagerBeans.ATUALIZACAO_DADOS_PARTIDA_MANAGER)
    private ProcessorManager atualizacaoPartidasProcessorManager;

    @Inject
    @Named(CampeonatoBeans.CAMPEONATO_SERVICE)
    private CampeonatoService campeonatoService;

    @Test
    public void test() {
	Campeonato campeonato = campeonatoService
		.selecionarCampeonato(TestConstants.CAMPEONATO_ID);
	campeonato.getParametrizacao().setSiteURLFetchJogos(
		this.getClass()
			.getResource(
				"/web-crawl/tabelabrasileirao-2014.net.html")
			.toExternalForm());
	atualizacaoPartidasProcessorManager.start();
    }

}
