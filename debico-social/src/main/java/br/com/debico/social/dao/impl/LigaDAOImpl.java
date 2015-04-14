package br.com.debico.social.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.model.Apostador;
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

		final Subquery<LigaApostador> subquery = query.subquery(LigaApostador.class);
		final Root<LigaApostador> apostador = subquery.from(LigaApostador.class);
		subquery.select(apostador).where(
				cb.and(	cb.equal(apostador.get("apostador").get("id"), idUsuarioApostador),
						cb.equal(apostador.get("liga").get("id"), liga.get("id"))));
		
		query.select(liga).where(cb.exists(subquery));

		return getEntityManager().createQuery(query).getResultList();
	}

	@Override
	public List<Apostador> selecionarApostadores(long idLiga) {
		final CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Apostador> query = cb.createQuery(Apostador.class);
		final Root<Apostador> apostador = query.from(Apostador.class);

		final Subquery<LigaApostador> subquery = query.subquery(LigaApostador.class);
		final Root<LigaApostador> liga = subquery.from(LigaApostador.class);
		subquery.select(liga).where(
				cb.and(	cb.equal(liga.get("liga").get("id"), idLiga),
						cb.equal(liga.get("apostador").get("id"), apostador.get("id"))));
		
		query.select(apostador).where(cb.exists(subquery));

		return getEntityManager().createQuery(query).getResultList();
	}
}
