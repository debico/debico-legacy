package br.com.debico.campeonato.dao.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.ChaveDAO;
import br.com.debico.core.dao.jpa.AbstractJPADAO;
import br.com.debico.model.campeonato.Chave;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class ChaveDAOImpl extends AbstractJPADAO<Chave, Integer> implements ChaveDAO {

    public ChaveDAOImpl() {
        super(Chave.class);
    }

    public void inserir(Chave chave) {
        super.inserir(chave);
    }

}
