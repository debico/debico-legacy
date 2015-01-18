package br.com.debico.campeonato.dao.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.FaseDAO;
import br.com.debico.core.dao.jpa.AbstractJPADAO;
import br.com.debico.model.campeonato.FaseImpl;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class FaseDAOImpl extends AbstractJPADAO<FaseImpl, Integer> implements FaseDAO {

    public FaseDAOImpl() {
        super(FaseImpl.class);
    }
    
    public void inserir(FaseImpl fase) {
        super.inserir(fase);
    }

}
