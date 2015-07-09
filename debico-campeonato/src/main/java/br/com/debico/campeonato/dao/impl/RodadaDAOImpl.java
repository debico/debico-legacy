package br.com.debico.campeonato.dao.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.RodadaDAO;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.Rodada;
import br.com.debico.model.campeonato.RodadaMeta;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class RodadaDAOImpl extends AbstractJPADao<Rodada, Integer> implements
        RodadaDAO {

    private static final String SQL_RODADAS_PARTIDAS_PROCESSADAS = "SELECT ID_RODADA, NM_RODADA FROM vi_rodada_partida_processada WHERE ID_CAMPEONATO = :campeonato ORDER BY NU_ORDEM DESC";

    public RodadaDAOImpl() {
        super(Rodada.class);
    }

    public Rodada selecionarRodadaAtual(final Campeonato campeonato) {
        return this.selecionarRodadaPorData(campeonato, new Date());
    }

    public Rodada selecionarRodadaPorData(Campeonato campeonato, Date data) {
        return DataAccessUtils
                .singleResult(getEntityManager()
                        .createQuery(
                                "SELECT NEW br.com.debico.model.campeonato.Rodada(r.id, r.nome, r.ordem) FROM Rodada r WHERE r.ranking.fase.campeonato = :c AND (r.dataInicioRodada >= :data OR r.dataFimRodada >= :data) ORDER BY r.dataInicioRodada",
                                Rodada.class).setMaxResults(1)
                        .setParameter("c", campeonato)
                        .setParameter("data", data).getResultList());
    }

    public List<Rodada> selecionarRodadas(final Campeonato campeonato) {
        return getEntityManager()
                .createQuery(
                        "SELECT NEW br.com.debico.model.campeonato.Rodada(r.id, r.nome, r.ordem) FROM Rodada r WHERE r.ranking.fase.campeonato = :c ORDER BY r.ordem",
                        Rodada.class).setParameter("c", campeonato)
                .getResultList();
    }

    public Rodada selecionarUltimaRodada(final Campeonato campeonato) {
        return DataAccessUtils
                .singleResult(getEntityManager()
                        .createQuery(
                                "SELECT r FROM Rodada r WHERE r.ranking.fase.campeonato = :c ORDER BY r.dataFimRodada DESC",
                                Rodada.class).setMaxResults(1)
                        .setParameter("c", campeonato).getResultList());
    }

    public List<Rodada> selecionarRodadasNaoCalculadas(Campeonato campeonato) {
        return getEntityManager()
                .createQuery(
                        "SELECT DISTINCT NEW br.com.debico.model.campeonato.Rodada(r.id) FROM Rodada r, PartidaRodada p WHERE p.rodada = r AND r.ranking.fase.campeonato = :c AND p.placar IS NOT NULL AND p.status = br.com.debico.model.StatusPartida.ND",
                        Rodada.class).setParameter("c", campeonato)
                .getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RodadaMeta> selecionarRodadasComPartidasProcessadas(
            Campeonato campeonato) {
        return getEntityManager()
                .createNativeQuery(SQL_RODADAS_PARTIDAS_PROCESSADAS,
                        RodadaMeta.class)
                .setParameter("campeonato", campeonato.getId()).getResultList();
    }
}
