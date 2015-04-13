package br.com.debico.campeonato.dao.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.FaseDAO;
import br.com.debico.model.campeonato.FaseImpl;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class FaseDAOImpl extends AbstractJPADao<FaseImpl, Integer> implements FaseDAO {

    public FaseDAOImpl() {
        super(FaseImpl.class);
    }
   

}
