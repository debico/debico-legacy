package br.com.debico.campeonato.dao.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.RankingDAO;
import br.com.debico.model.campeonato.Ranking;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class RankingDAOImpl extends AbstractJPADao<Ranking, Integer> implements RankingDAO {

    public RankingDAOImpl() {
        super(Ranking.class);
    }

}
