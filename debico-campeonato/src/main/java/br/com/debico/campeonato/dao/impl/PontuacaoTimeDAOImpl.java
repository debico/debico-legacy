package br.com.debico.campeonato.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.PontuacaoTimeDAO;
import br.com.debico.model.Time;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.PontuacaoTime;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class PontuacaoTimeDAOImpl extends AbstractJPADao<PontuacaoTime, Integer> implements PontuacaoTimeDAO {

    public PontuacaoTimeDAOImpl() {
        super(PontuacaoTime.class);
    }
    
    public List<PontuacaoTime> selecionarPontuacaoTimes(final Collection<Time> times) {
        return getEntityManager()
                .createQuery("SELECT pont FROM PontuacaoTime pont WHERE time in (:times)", PontuacaoTime.class)
                .setParameter("times", times)
                .getResultList();
    }

    private static final String SQL_SELECIONAR_PONTUACAO_CAMP = 
    		"SELECT A.* FROM tb_pontuacao as A INNER JOIN tb_ranking AS C ON (A.ID_RANKING = C.ID_RANKING) INNER JOIN tb_fase AS D ON (C.ID_FASE = D.ID_FASE) WHERE D.ID_CAMPEONATO = :campeonato";
    
    @SuppressWarnings("unchecked")
	public List<PontuacaoTime> selecionarPontuacaoCampeonato(final Campeonato campeonato) {
    	 return getEntityManager()
    			.createNativeQuery(SQL_SELECIONAR_PONTUACAO_CAMP, "pontuacaoTimeRankingMapping")
    			.setParameter("campeonato", campeonato.getId())
    			.getResultList();
    }
}
