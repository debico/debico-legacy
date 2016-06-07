package br.com.debico.social.dao.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.model.Perfil;
import br.com.debico.model.Usuario;
import br.com.debico.social.dao.UsuarioDAO;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class UsuarioDAOImpl extends AbstractJPADao<Usuario, Integer> implements UsuarioDAO {

	public UsuarioDAOImpl() {
		super(Usuario.class);
	}

	public Usuario selecionarPorEmail(String email) {
		return DataAccessUtils.singleResult(
				getEntityManager().createQuery("SELECT a FROM Usuario a WHERE email = :email", Usuario.class)
						.setParameter("email", email).getResultList());

	}

	@Override
	public List<Usuario> selecionarPorPerfil(Perfil perfil) {
		return getEntityManager().createQuery("SELECT u FROM Usuario u WHERE perfil = :perfil", Usuario.class)
				.setParameter("perfil", perfil.toString()).getResultList();
	}

	@Override
	public void alterarSenha(String email, String novaSenha) {
		getEntityManager().createQuery("UPDATE Usuario SET senha = :senha WHERE email = :email")
				.setParameter("email", email).setParameter("senha", novaSenha).executeUpdate();
	}

	@Override
	public String recuperarSenhaAtual(String email) {
		// @formatter:off
		return getEntityManager().createQuery("SELECT u.senha FROM Usuario u WHERE u.email = :email", String.class)
				.setParameter("email", email).getSingleResult();
		// @formatter:on
	}
}
