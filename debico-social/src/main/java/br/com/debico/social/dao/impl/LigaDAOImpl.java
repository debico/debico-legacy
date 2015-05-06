package br.com.debico.social.dao.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.model.Apostador;
import br.com.debico.model.Apostador_;
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
	public List<Liga> selecionarPorUsuario(int idUsuarioApostador) {
		return getEntityManager()
				.createQuery("SELECT L FROM Liga AS L WHERE EXISTS (SELECT A.liga.id FROM LigaApostador AS A WHERE A.liga.id = L.id AND A.apostador.usuario.id = :id)", Liga.class)
				.setParameter("id", idUsuarioApostador)
				.getResultList();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Apostador> selecionarApostadores(long idLiga) {
		final CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Apostador> query = cb.createQuery(Apostador.class);
		final Root<Apostador> apostador = query.from(Apostador.class);

		final Subquery<LigaApostador> subquery = query.subquery(LigaApostador.class);
		final Root<LigaApostador> liga = subquery.from(LigaApostador.class);
		subquery.select((Expression)liga.get("liga").get("id")).where(
				cb.and(	cb.equal(liga.get("liga").get("id"), idLiga),
						cb.equal(liga.get("apostador").get("id"), apostador.get("id"))));
		
		query.select(apostador).where(cb.exists(subquery)).orderBy(cb.asc(apostador.get(Apostador_.nome)));

		return getEntityManager().createQuery(query).getResultList();
	}
}
