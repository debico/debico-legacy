package br.com.debico.social.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.social.dao.LigaDAO;
import br.com.debico.social.model.Liga;
import br.com.debico.social.model.LigaApostador;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
public class LigaDAOImpl extends AbstractJPADao<Liga, Long> implements LigaDAO {

	public LigaDAOImpl() {
		super(Liga.class);
	}

	@PostConstruct
	public void init() {
		
	}

	@Override
	public List<Liga> selecionarPorApostador(int idUsuarioApostador) {
		final CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Liga> query = cb.createQuery(Liga.class);
		final Root<Liga> liga = query.from(Liga.class);
		final Join<Liga, LigaApostador> apostador = liga.join("apostador");

		query.select(liga).where(
				cb.equal(apostador.get("apostador").get("usuario").get("id"),
						idUsuarioApostador));

		return getEntityManager().createQuery(query).getResultList();
	}
}
