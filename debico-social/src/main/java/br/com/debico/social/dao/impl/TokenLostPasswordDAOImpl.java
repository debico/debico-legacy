package br.com.debico.social.dao.impl;

import javax.inject.Named;
import javax.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.social.dao.TokenLostPasswordDAO;
import br.com.debico.social.model.TokenLostPassword;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class TokenLostPasswordDAOImpl extends
	AbstractJPADao<TokenLostPassword, String> implements
	TokenLostPasswordDAO {

    public TokenLostPasswordDAOImpl() {
	super(TokenLostPassword.class);
    }

    @Override
    public TokenLostPassword findById(String id) {
	try {
	    return super.findById(id);
	} catch (EntityNotFoundException e) {
	    return null;
	}
    }

}
