package br.com.debico.bolao.services.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Set;

import javax.inject.Inject;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.model.PontuacaoRodadaApostadorSerie;
import br.com.debico.bolao.services.DesempenhoApostadorService;
import br.com.debico.bolao.test.support.AbstractBolaoUnitTest;
import br.com.debico.model.campeonato.Campeonato;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDesempenhoApostadorServiceImpl extends AbstractBolaoUnitTest {

    @Inject
    private DesempenhoApostadorService desempenhoApostadorService;
    
   
    @Test
    public void testRecuperarDesempenhoPontuacaoPorRodada() {
        final Campeonato campeonato = this.campeonatoService.selecionarCampeonato(CAMPEONATO_ID);
        
        Set<PontuacaoRodadaApostadorSerie> serie = 
                desempenhoApostadorService.recuperarDesempenhoTop10PontuacaoPorRodada(campeonato, EMAIL_ULTIMO_RANKING);
        
        LOGGER.debug("Recuperada a performance da galera: {}", serie);
        
        assertNotNull(serie);
        assertFalse(serie.isEmpty());
        assertThat(serie, IsCollectionWithSize.hasSize((11)));
    }

}
