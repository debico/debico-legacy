package br.com.debico.campeonato.dao.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.RodadaViewsDAO;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.Rodada;
import br.com.tecnobiz.spring.config.dao.AbstractJDBCDao;

@Named
@Transactional(propagation=Propagation.MANDATORY)
class RodadaViewsDAOImpl extends AbstractJDBCDao implements RodadaViewsDAO {

    public RodadaViewsDAOImpl() {
        
    }

    @Override
    public List<Rodada> selecionarRodadasProcessadas(Campeonato campeonato) {
        return null;
    }

}
