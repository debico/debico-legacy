package br.com.debico.bolao.dao.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.dao.PalpiteDAO;
import br.com.debico.model.Apostador;
import br.com.debico.model.Palpite;
import br.com.debico.model.Partida;
import br.com.debico.model.PartidaBase;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class PalpiteDAOImpl extends AbstractJPADao<Palpite, Integer> implements
	PalpiteDAO {

    public PalpiteDAOImpl() {
	super(Palpite.class);
    }

    @Override
    public List<Palpite> selecionarTodos(CampeonatoImpl campeonato) {
	return getEntityManager()
		.createQuery(
			"SELECT p FROM Palpite p JOIN FETCH p.apostador, ApostadorPontuacao a WHERE p.apostador = a.apostador AND a.campeonato = :campeonato ",
			Palpite.class).setParameter("campeonato", campeonato)
		.getResultList();
    }

    public List<Palpite> selecionarTodos(List<? extends PartidaBase> partidas) {
	return getEntityManager()
		.createQuery(
			"SELECT p FROM Palpite p JOIN FETCH p.apostador WHERE p.computado = false AND p.partida IN (:partidas)",
			Palpite.class).setParameter("partidas", partidas)
		.getResultList();
    }

    public boolean pesquisarExistenciaPalpite(Partida partida,
	    Apostador apostador) {
	return getEntityManager()
		.createQuery(
			"SELECT CASE WHEN (COUNT(p) > 0) THEN TRUE ELSE FALSE END FROM Palpite p WHERE p.partida = :partida AND p.apostador = :apostador",
			Boolean.class).setParameter("partida", partida)
		.setParameter("apostador", apostador).getSingleResult();
    }

}
