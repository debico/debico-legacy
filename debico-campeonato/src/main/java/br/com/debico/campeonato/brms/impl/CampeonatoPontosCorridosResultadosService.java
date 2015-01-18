package br.com.debico.campeonato.brms.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.CampeonatoDAO;
import br.com.debico.model.PartidaChave;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.model.campeonato.Chave;
import br.com.debico.model.campeonato.Rodada;

/**
 * Processa campeonatos de pontos corridos: Brasileirão, Séria A Italiana, Inglês, etc.
 * <p/>
 * Caso necessário (regras de cálculo de pontos diferentes), especializar o campeonato e determinar outra agenda 
 * de processamento.
 * 
 * @author r_fernandes
 *
 */
@Named("campeonatoPontosCorridosResultadosService")
@Transactional(readOnly = false)
class CampeonatoPontosCorridosResultadosService extends AbstractResultadosService<CampeonatoPontosCorridos> {
    
    protected static final String AGENDA_GROUP_PONTOS_CORRIDOS = "calcula_pontos_campeonato_pc";
    
    @Inject
    private CampeonatoDAO campeonatoDAO;
    
    @Override
    protected String getAgendaGroupCalculoPartida() {
        return AGENDA_GROUP_PONTOS_CORRIDOS;
    }
    
    @Override
    protected void doEfetuarCalculoPontosTimeRodada(final Rodada rodada, final List<PartidaRodada> partidas) {
    	getCalculoPartidasService().calcularPontuacaoTimes(partidas, AGENDA_GROUP_PONTOS_CORRIDOS);
    	//a partir da rodada devemos pegar o campeonato
    	getCalculoRankingTimesService().calcularPosicaoGeral(this.recuperarCampeonato(rodada));
    }
    
    @Override
    protected void doEfetuarCalculoResultadoTimeChave(final Chave chave, final List<PartidaChave> partidas) {
        //caso o desempate seja necessário, um jogo eliminatório deve ser realizado.
        throw new UnsupportedOperationException("Calculo nao suportado para campeonatos de pontos corridos.");
    }
    
    private Campeonato recuperarCampeonato(final Rodada rodada) {
    	if(rodada.getRanking() != null && rodada.getRanking().getFase() != null && rodada.getRanking().getFase().getCampeonato() != null) {
    		return rodada.getRanking().getFase().getCampeonato();
    	} else {
    		return campeonatoDAO.selecionarPorRodada(rodada);
    	}
    }
    
}
