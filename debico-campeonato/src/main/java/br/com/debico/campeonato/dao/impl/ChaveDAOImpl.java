package br.com.debico.campeonato.dao.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.ChaveDAO;
import br.com.debico.model.campeonato.Chave;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class ChaveDAOImpl extends AbstractJPADao<Chave, Integer> implements ChaveDAO {

    public ChaveDAOImpl() {
        super(Chave.class);
    }

}
