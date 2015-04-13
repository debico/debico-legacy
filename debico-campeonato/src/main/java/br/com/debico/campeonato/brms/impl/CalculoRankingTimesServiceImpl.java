package br.com.debico.campeonato.brms.impl;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.brms.CalculoRankingTimesService;
import br.com.debico.campeonato.dao.CampeonatoParametrizacaoDAO;
import br.com.debico.campeonato.dao.PontuacaoTimeDAO;
import br.com.debico.core.brms.BRMSExecutor;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.model.campeonato.ParametrizacaoCampeonato;
import br.com.debico.model.campeonato.PontuacaoTime;

@Named
@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
class CalculoRankingTimesServiceImpl implements CalculoRankingTimesService  {
    
    protected static final Logger LOGGER = LoggerFactory.getLogger(CalculoRankingTimesServiceImpl.class);

    protected static final String AGENDA_GROUP_STATUS_PARTIDA = "calcula_posicao_times";

    @Inject
    @Named("campeonatoBrmsExecutor")
    private BRMSExecutor brmsExecutor;
    
    @Inject
    private PontuacaoTimeDAO pontuacaoTimeDAO;
    
    @Inject
    private CampeonatoParametrizacaoDAO campeonatoParametrizacaoDAO;
    
    public CalculoRankingTimesServiceImpl() {
       
    }
    
    @Override
    public void calcularPosicaoGeral(Campeonato campeonato) {
        
        LOGGER.debug("[calcularPosicaoGeral] Selecionando o ranking para os times das partidas selecionadas.");
        
        List<PontuacaoTime> ranking = pontuacaoTimeDAO.selecionarPontuacaoCampeonato(campeonato);
        
        if(campeonato.getParametrizacao() == null) {
        	((CampeonatoImpl)campeonato).setParametrizacao((campeonatoParametrizacaoDAO.findById(campeonato.getId())));
        } 
        
        // apesar de nao ser uma coleção de parâmetros, foi feito dessa forma por causa da assinatura do método do BRMS.
        // tá na hora de refatorar, né?
        List<ParametrizacaoCampeonato> param = Collections.singletonList(campeonato.getParametrizacao());
        
        LOGGER.debug("[calcularPosicaoGeral] '{}' itens de pontuacao selecionados.", ranking.size());
        LOGGER.debug("[calcularPosicaoGeral] Parametrizacao definida: {}", param);
        
        brmsExecutor.processar(AGENDA_GROUP_STATUS_PARTIDA, ranking, param);
        
    }

}
