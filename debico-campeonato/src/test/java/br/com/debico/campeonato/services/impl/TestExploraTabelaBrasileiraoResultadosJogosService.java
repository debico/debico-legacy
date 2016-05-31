package br.com.debico.campeonato.services.impl;

import static org.hamcrest.Matchers.emptyCollectionOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.services.ExploraWebResultadosJogosService;
import br.com.debico.campeonato.test.support.AbstractCampeonatoUnitTest;
import br.com.debico.model.PartidaBase;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestExploraTabelaBrasileiraoResultadosJogosService extends
	AbstractCampeonatoUnitTest {

    @Inject
    private ExploraWebResultadosJogosService exploraService;

    @Test
    public void testRecuperarPartidasFinalizadas() {
	exploraService.setPesquisaURL(this.getClass().getResource(
		"/web-crawl/tabelabrasileirao.net.html"));
	List<PartidaBase> partidas = exploraService
		.recuperarPartidasFinalizadas(this.CAMPEONATO);

	assertNotNull(partidas);
	assertThat(partidas, not(emptyCollectionOf(PartidaBase.class)));
    }

}
