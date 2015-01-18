package br.com.debico.campeonato.brms.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.brms.CalculoPartidasService;
import br.com.debico.campeonato.dao.PartidaDAO;
import br.com.debico.campeonato.dao.PontuacaoTimeDAO;
import br.com.debico.core.brms.BRMSExecutor;
import br.com.debico.model.Partida;
import br.com.debico.model.PartidaChave;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.campeonato.Chave;
import br.com.debico.model.campeonato.PontuacaoTime;
import br.com.debico.model.campeonato.Rodada;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
class CalculoPartidasServiceImpl implements CalculoPartidasService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(CalculoPartidasServiceImpl.class);

    protected static final String AGENDA_GROUP_STATUS_PARTIDA = "calcula_resultado";

    @Inject
    private BRMSExecutor brmsExecutor;

    @Inject
    private PartidaDAO partidaDAO;
    
    @Inject
    private PontuacaoTimeDAO pontuacaoTimeDAO;

    public List<PartidaRodada> definirStatusPartidas(final Rodada rodada) {
        checkNotNull(rodada);
        
        LOGGER.debug("[definirStatusPartidas] Selecionando todas as partidas ainda nao definidas com placar na rodada '{}'.", rodada);
        List<PartidaRodada> partidas = partidaDAO.selecionarPartidasNaoDefinidasComPlacar(rodada);
        
        LOGGER.debug("[definirStatusPartidas] '{}' partidas selecionadas.", partidas.size());
        
        if(!partidas.isEmpty()) {
            brmsExecutor.processar(AGENDA_GROUP_STATUS_PARTIDA, partidas);
            LOGGER.debug("[definirStatusPartidas] Execucao finalizada com sucesso. Retornando partidas.");
        }
        
        return partidas;
    }
    
    public List<PartidaChave> definirStatusPartidas(Chave chave) {
        throw new UnsupportedOperationException("Disponivel apenas quando implementado algum campeonato que necessite de calculo de chave");
    }
    
    public void calcularPontuacaoTimes(List<? extends Partida> partidas, String agendaGroup) {
        checkNotNull(partidas);
        checkArgument(partidas.size() > 0, "Nao ha partidas para serem processadas");
        
        LOGGER.debug("[calcularPontuacaoTimes] Selecionando o ranking para os times das partidas selecionadas.");
        
        List<PontuacaoTime> ranking = pontuacaoTimeDAO.selecionarPontuacaoTimes(ResultadosUtils.recuperarTimes(partidas));
        
        LOGGER.debug("[calcularPontuacaoTimes] '{}' itens de pontuacao selecionados.", ranking.size());
        
        brmsExecutor.processar(agendaGroup, partidas, ranking);
    }

}
