package br.com.debico.social.dao.impl;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.social.dao.LigaApostadorDAO;
import br.com.debico.social.model.LigaApostador;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class LigaApostadorDAOImpl extends AbstractJPADao<LigaApostador, LigaApostador> implements
		LigaApostadorDAO {

	public LigaApostadorDAOImpl() {
		super(LigaApostador.class);
	}

	@PostConstruct
	public void init() {

	}
	
}
