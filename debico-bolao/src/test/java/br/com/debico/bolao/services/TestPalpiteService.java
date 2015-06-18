package br.com.debico.bolao.services;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.PalpiteForaPrazoException;
import br.com.debico.bolao.test.support.AbstractBolaoUnitTest;
import br.com.debico.campeonato.services.CampeonatoPontosCorridosService;
import br.com.debico.campeonato.services.PartidaService;
import br.com.debico.campeonato.services.RodadaService;
import br.com.debico.core.DebicoException;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.Placar;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.model.campeonato.Rodada;
import br.com.debico.model.to.PalpiteTO;
import static org.junit.Assert.assertNotNull;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestPalpiteService extends AbstractBolaoUnitTest {

    @Inject
    private PalpiteService palpiteService;

    @Inject
    private PartidaService partidaService;

    @Inject
    private RodadaService rodadaService;

    @Inject
    @Named("campeonatoPontosCorridosServiceImpl")
    private CampeonatoPontosCorridosService campeonatoPontosCorridosServiceImpl;

    @Test(expected = PalpiteForaPrazoException.class)
    public void testPalpitarPalpiteLiteCampeonatoImpl_ForaPrazo()
            throws DebicoException {
        final CampeonatoImpl campeonatoImpl = this.campeonatoService
                .selecionarCampeonato(CAMPEONATO_ID);

        // passou um minuto
        final PalpiteTO palpiteIO = this.atualizarPartidaERetornarPalpite(
                new DateTime(System.currentTimeMillis()).plusMinutes(
                        (int) PalpiteService.PRAZO_PALPITE_MINUTOS - 1)
                        .toDate(), campeonatoImpl);
        
        palpiteService.palpitar(palpiteIO, campeonatoImpl);

    }

    @Test
    public void testPalpitarPalpiteLiteCampeonatoImpl() throws DebicoException {
        final CampeonatoImpl campeonatoImpl = this.campeonatoService
                .selecionarCampeonato(CAMPEONATO_ID);
        // no limite
        final PalpiteTO palpiteIO = this.atualizarPartidaERetornarPalpite(
                new DateTime(System.currentTimeMillis()).plusMinutes(
                        (int) PalpiteService.PRAZO_PALPITE_MINUTOS + 1)
                        .toDate(), campeonatoImpl);

        assertNotNull(palpiteService.palpitar(palpiteIO, campeonatoImpl));
    }

    private PalpiteTO atualizarPartidaERetornarPalpite(Date dataHoraPartida,
            Campeonato campeonato) {
        final List<Rodada> rodadas = rodadaService.selecionarRodadas(campeonato
                .getPermalink());
        final List<PartidaRodada> partidaRodadas = campeonatoPontosCorridosServiceImpl
                .selecionarPartidasRodada(rodadas.get(0).getId());

        partidaService.atualizarDataHorario(partidaRodadas.get(0).getId(),
                dataHoraPartida);

        return new PalpiteTO(EMAIL_PRIMEIRO_RANKING, new Placar(2, 0),
                partidaRodadas.get(0).getId());
    }
}
