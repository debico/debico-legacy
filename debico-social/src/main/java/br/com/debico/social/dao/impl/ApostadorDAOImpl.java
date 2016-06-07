package br.com.debico.social.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.model.Apostador;
import br.com.debico.model.ApostadorOpcoes_;
import br.com.debico.model.ApostadorPontuacao;
import br.com.debico.model.Apostador_;
import br.com.debico.model.Palpite;
import br.com.debico.model.Partida;
import br.com.debico.model.Usuario;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.FaseImpl;
import br.com.debico.social.dao.ApostadorDAO;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class ApostadorDAOImpl extends AbstractJPADao<Apostador, Integer> implements ApostadorDAO {

	public ApostadorDAOImpl() {
		super(Apostador.class);
	}

	@Override
	public List<Apostador> procurarPorParteNome(String parteNome) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Apostador> query = cb.createQuery(Apostador.class);
		final Root<Apostador> apostador = query.from(Apostador.class);

		query.select(cb.construct(Apostador.class, apostador.get(Apostador_.id), apostador.get(Apostador_.nome)))
				.where(cb.like(apostador.get(Apostador_.nome), String.format("%%%s%%", parteNome)));

		return getEntityManager().createQuery(query).setMaxResults(20).getResultList();
	}

	@Override
	public Apostador selecionarPorIdUsuario(int idUsuario) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Apostador> query = cb.createQuery(Apostador.class);
		final Root<Apostador> apostador = query.from(Apostador.class);
		@SuppressWarnings("unchecked")
		final Join<Apostador, Usuario> usuario = (Join<Apostador, Usuario>) apostador.fetch(Apostador_.usuario,
				JoinType.INNER);

		// fetch
		apostador.join(Apostador_.opcoes).fetch(ApostadorOpcoes_.timeCoracao, JoinType.LEFT);

		query.select(apostador).where(cb.equal(usuario.get("id"), idUsuario));

		return DataAccessUtils.singleResult(getEntityManager().createQuery(query).getResultList());
	}

	@Override
	public Apostador selecionarPorSocialId(String socialUserId) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Apostador> query = cb.createQuery(Apostador.class);
		final Root<Apostador> apostador = query.from(Apostador.class);
		@SuppressWarnings("unchecked")
		final Join<Apostador, Usuario> usuario = (Join<Apostador, Usuario>) apostador.fetch(Apostador_.usuario,
				JoinType.INNER);

		query.select(apostador).where(cb.equal(usuario.get("socialUserId"), socialUserId));

		return DataAccessUtils.singleResult(getEntityManager().createQuery(query).getResultList());
	}

	public Apostador selecionarPorEmail(String email) {
		return DataAccessUtils.singleResult(getEntityManager()
				.createQuery("SELECT a FROM Apostador a WHERE usuario.email = :email", Apostador.class)
				.setParameter("email", email).getResultList());
	}

	@SuppressWarnings("unchecked")
	public Apostador selecionarPerfilPorEmail(String email) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Apostador> query = cb.createQuery(Apostador.class);
		final Root<Apostador> apostador = query.from(Apostador.class);
		final Join<Apostador, Usuario> usuario = (Join<Apostador, Usuario>) apostador.fetch(Apostador_.usuario,
				JoinType.INNER);

		// fetch
		apostador.join(Apostador_.opcoes).fetch(ApostadorOpcoes_.timeCoracao, JoinType.LEFT);

		query.select(apostador).where(cb.equal(usuario.get("email"), email));

		return DataAccessUtils.singleResult(getEntityManager().createQuery(query).getResultList());
	}

	public List<Apostador> selecionarApostadoresSemPalpite(final Campeonato campeonato, Date dataInicioPartida,
			Date dataFimPartida) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		// principal
		final CriteriaQuery<Apostador> query = cb.createQuery(Apostador.class);
		final Root<Partida> partida = query.from(Partida.class);
		final Join<Partida, FaseImpl> fase = partida.join("fase");
		final Root<ApostadorPontuacao> pontuacao = query.from(ApostadorPontuacao.class);
		final Join<ApostadorPontuacao, Apostador> apostador = pontuacao.join("apostador");

		// subquery: apostadores
		final Subquery<Palpite> subquery2 = query.subquery(Palpite.class);
		final Root<Palpite> palpite = subquery2.from(Palpite.class);
		final Join<Palpite, Partida> joinSub = palpite.join("partida");

		subquery2.select(palpite);
		subquery2.where(cb.and(cb.equal(pontuacao.get("apostador"), palpite.get("apostador")),
				cb.between(joinSub.<Date>get("dataHoraPartida"), dataInicioPartida, dataFimPartida)));

		final List<Predicate> predicates = new ArrayList<Predicate>();

		predicates.add(cb.equal(apostador.get("opcoes").get("lembretePalpites"), true));
		predicates.add(cb.isNotNull(apostador.get("usuario").get("email")));
		predicates.add(cb.equal(fase.get("campeonato"), pontuacao.get("campeonato")));
		predicates.add(cb.between(partida.<Date>get("dataHoraPartida"), dataInicioPartida, dataFimPartida));
		predicates.add(cb.equal(pontuacao.get("campeonato"), campeonato));
		predicates.add(cb.not(cb.exists(subquery2)));

		query.distinct(true).select(cb.construct(Apostador.class, apostador.get("id"), apostador.get("nome"),
				apostador.get("usuario").get("email")));
		query.where(predicates.toArray(new Predicate[] {}));

		return getEntityManager().createQuery(query).getResultList();
	}
}
