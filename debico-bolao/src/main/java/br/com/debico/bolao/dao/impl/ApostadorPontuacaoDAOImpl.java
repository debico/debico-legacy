package br.com.debico.bolao.dao.impl;

import java.util.List;

import javax.inject.Named;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.dao.ApostadorPontuacaoDAO;
import br.com.debico.model.AbstractApostadorPontuacao;
import br.com.debico.model.Apostador;
import br.com.debico.model.ApostadorPontuacao;
import br.com.debico.model.campeonato.Campeonato;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class ApostadorPontuacaoDAOImpl extends
        AbstractJPADao<ApostadorPontuacao, Apostador> implements
        ApostadorPontuacaoDAO {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ApostadorPontuacaoDAOImpl.class);

    public ApostadorPontuacaoDAOImpl() {
        super(ApostadorPontuacao.class);
    }

    @Override
    public List<ApostadorPontuacao> selecionarApostadoresPorLiga(
            int idCampeonato, long idLiga) {
        // @formatter:off
		return getEntityManager()
				.createQuery("SELECT A FROM ApostadorPontuacao AS A JOIN FETCH A.apostador, LigaApostador AS L WHERE A.apostador.id = L.apostador.id AND A.campeonato.id = :idCampeonato AND L.liga.id = :idLiga", 
						ApostadorPontuacao.class)
				.setParameter("idCampeonato", idCampeonato)
				.setParameter("idLiga", idLiga)
				.getResultList();
		// @formatter:on
    }

    @Override
    public List<AbstractApostadorPontuacao> selecionarApostadoresPorRodada(int idRodada) {
        // @formatter:off
	    return getEntityManager()
	            .createQuery("SELECT A FROM ApostadorPontuacaoRodada A JOIN FETCH A.apostador WHERE A.rodada.id = :idRodada", AbstractApostadorPontuacao.class)
	            .setParameter("idRodada", idRodada)
	            .getResultList();
	    // @formatter:on
    }

    @Override
    public List<AbstractApostadorPontuacao> selecionarApostadoresPorRodadaELiga(
            int idRodada, long idLiga) {
        // @formatter:off
        return getEntityManager()
                .createQuery("SELECT A FROM ApostadorPontuacaoRodada AS A JOIN FETCH A.apostador, LigaApostador AS L WHERE A.apostador.id = L.apostador.id AND P.rodada.id = :idRodada AND L.liga.id = :idLiga", 
                        AbstractApostadorPontuacao.class)
                .setParameter("idRodada", idRodada)
                .setParameter("idLiga", idLiga)
                .getResultList();
        // @formatter:on
    }

    public List<ApostadorPontuacao> selecionarApostadores(Campeonato campeonato) {
        return getEntityManager()
                .createQuery(
                        "SELECT A FROM ApostadorPontuacao AS A JOIN FETCH A.apostador WHERE A.campeonato = :campeonato",
                        ApostadorPontuacao.class)
                .setParameter("campeonato", campeonato).getResultList();
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
