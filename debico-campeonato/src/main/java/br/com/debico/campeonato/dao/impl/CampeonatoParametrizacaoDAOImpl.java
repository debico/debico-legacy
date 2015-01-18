package br.com.debico.campeonato.dao.impl;

import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.CampeonatoParametrizacaoDAO;
import br.com.debico.core.dao.jpa.AbstractJPADAO;
import br.com.debico.model.campeonato.CampeonatoParametrizacao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class CampeonatoParametrizacaoDAOImpl extends
		AbstractJPADAO<CampeonatoParametrizacao, Integer> implements
		CampeonatoParametrizacaoDAO {

	public CampeonatoParametrizacaoDAOImpl() {
		super(CampeonatoParametrizacao.class);
	}

}
