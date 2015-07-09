package br.com.debico.campeonato.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.services.RodadaService;
import br.com.debico.campeonato.test.support.AbstractCampeonatoUnitTest;
import br.com.debico.model.campeonato.RodadaMeta;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRodadaServiceImpl extends AbstractCampeonatoUnitTest {

    @Inject
    private RodadaService rodadaService;

    @Test
    public void testSelecionarRodadasMetaCalculadas() {
        List<RodadaMeta> rodadas = rodadaService
                .selecionarRodadasCalculadas(CAMPEONATO);
        assertNotNull(rodadas);
        // não temos nada processado
        assertTrue(rodadas.isEmpty());
    }

}
