package br.com.debico.campeonato.dao.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.RankingDAO;
import br.com.debico.core.dao.jpa.AbstractJPADAO;
import br.com.debico.model.campeonato.Ranking;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class RankingDAOImpl extends AbstractJPADAO<Ranking, Integer> implements RankingDAO {

    public RankingDAOImpl() {
        super(Ranking.class);
    }

    public void inserir(Ranking ranking) {
        super.inserir(ranking);
    }

}
