package br.com.debico.bolao.dao.impl;

import java.util.List;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.dao.ApostadorPontuacaoDAO;
import br.com.debico.core.dao.jpa.AbstractJPADAO;
import br.com.debico.model.Apostador;
import br.com.debico.model.ApostadorPontuacao;
import br.com.debico.model.campeonato.Campeonato;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class ApostadorPontuacaoDAOImpl extends
		AbstractJPADAO<ApostadorPontuacao, Apostador> implements
		ApostadorPontuacaoDAO {

	public ApostadorPontuacaoDAOImpl() {
		super(ApostadorPontuacao.class);
	}

	// TODO: transformar em uma "native query" ou qualquer outra coisa para melhorar o desempenho e nao ter que dar select nos outros campos.
	public List<ApostadorPontuacao> selecionarApostadores(Campeonato campeonato) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<ApostadorPontuacao> query = cb.createQuery(ApostadorPontuacao.class);
		final Root<ApostadorPontuacao> pontuacao = query.from(ApostadorPontuacao.class);
		
		query.select(pontuacao).where(cb.equal(pontuacao.get("campeonato"), campeonato));
		
		return getEntityManager().createQuery(query).getResultList();
	}

	@Override
	public void inserir(ApostadorPontuacao e) {
		super.inserir(e);
	}

	public ApostadorPontuacao selecionarApostador(Apostador apostador,
			Campeonato campeonato) {
		try {
			return getEntityManager()
					.createQuery(
							"SELECT ap FROM ApostadorPontuacao ap WHERE campeonato = :campeonato AND apostador = :apostador",
							ApostadorPontuacao.class)
					.setParameter("apostador", apostador)
					.setParameter("campeonato", campeonato).getSingleResult();

		} catch (NoResultException nre) {
			LOGGER.debug(
					"[selecionarApostador] Nenhum resultado encontrado na busca pelo apostador '{}' inscrito no campeonato '{}'.",
					apostador, campeonato);
			return null;
		}
	}

}
