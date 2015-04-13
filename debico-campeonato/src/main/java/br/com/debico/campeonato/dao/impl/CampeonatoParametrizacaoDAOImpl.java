package br.com.debico.campeonato.dao.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.CampeonatoParametrizacaoDAO;
import br.com.debico.model.campeonato.ParametrizacaoCampeonato;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class CampeonatoParametrizacaoDAOImpl extends
		AbstractJPADao<ParametrizacaoCampeonato, Integer> implements
		CampeonatoParametrizacaoDAO {

	public CampeonatoParametrizacaoDAOImpl() {
		super(ParametrizacaoCampeonato.class);
	}

}
