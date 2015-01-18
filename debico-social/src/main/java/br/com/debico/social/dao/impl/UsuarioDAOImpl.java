package br.com.debico.social.dao.impl;

import javax.inject.Named;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.core.dao.jpa.AbstractJPADAO;
import br.com.debico.model.Usuario;
import br.com.debico.social.dao.UsuarioDAO;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class UsuarioDAOImpl extends AbstractJPADAO<Usuario, Integer> implements
		UsuarioDAO {

	public UsuarioDAOImpl() {
		super(Usuario.class);
	}

	public Usuario selecionarPorEmail(String email) {
		return DataAccessUtils.singleResult(
				getEntityManager()
				.createQuery("SELECT a FROM Usuario a WHERE email = :email", Usuario.class)
				.setParameter("email", email)
				.getResultList());

	}

}
