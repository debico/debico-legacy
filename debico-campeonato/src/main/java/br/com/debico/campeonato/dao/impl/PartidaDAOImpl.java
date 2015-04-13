package br.com.debico.campeonato.dao.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.PartidaDAO;
import br.com.debico.model.Partida;
import br.com.debico.model.PartidaChave;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.StatusPartida;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.Chave;
import br.com.debico.model.campeonato.Rodada;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class PartidaDAOImpl extends AbstractJPADao<Partida, Integer> implements PartidaDAO {
	
	protected final static String ORDER_BY_PARTIDA = "ORDER BY p.dataHoraPartida, p.mandante.alias";

    public PartidaDAOImpl() {
        super(Partida.class);
    }

    public List<PartidaRodada> selecionarPartidasNaoDefinidasComPlacar(final Rodada rodada) {
        return getEntityManager()
                .createQuery("SELECT p FROM PartidaRodada p WHERE rodada = :rodada AND status = :status AND placar IS NOT NULL " + ORDER_BY_PARTIDA, PartidaRodada.class)
                .setParameter("status", StatusPartida.ND)
                .setParameter("rodada", rodada)
                .getResultList();
    }
    
    public List<PartidaChave> selecionarPartidasNaoDefinidasComPlacar(final Chave chave) {
        return getEntityManager()
                .createQuery("SELECT p FROM PartidaChave p WHERE chave = :chave AND status = :status AND placar IS NOT NULL " + ORDER_BY_PARTIDA, PartidaChave.class)
                .setParameter("status", StatusPartida.ND)
                .setParameter("chave", chave)
                .getResultList();
    }
    
    
    public List<PartidaRodada> selecionarPartidasPorRodada(int idRodada) {
        return getEntityManager()
                .createQuery("SELECT p FROM PartidaRodada p JOIN FETCH p.mandante JOIN FETCH p.visitante LEFT JOIN FETCH p.placar WHERE p.rodada.id = :rodada " + ORDER_BY_PARTIDA, PartidaRodada.class)
                .setParameter("rodada", idRodada)
                .getResultList();

    }
    
    public List<PartidaRodada> selecionarPartidasPorRodada(final int ordemRodada, final String campeonatoPermalink) {
        return getEntityManager()
                .createQuery("SELECT p FROM PartidaRodada p JOIN FETCH p.mandante JOIN FETCH p.visitante LEFT JOIN FETCH p.placar WHERE p.rodada.ordem = :ordem AND p.fase.campeonato.permalink = :c " + ORDER_BY_PARTIDA, PartidaRodada.class)
                .setParameter("ordem", ordemRodada)
                .setParameter("c", campeonatoPermalink)
                .getResultList();
    }
    
    public List<Partida> selecionarProximasPartidas(Campeonato campeonato) {
        return getEntityManager()
                .createQuery("SELECT p FROM Partida p WHERE p.fase.campeonato = :campeonato AND p.dataHoraPartida IS NOT NULL AND p.local IS NOT NULL " + ORDER_BY_PARTIDA, Partida.class)
                .setMaxResults(5)
                .setParameter("campeonato", campeonato)
                .getResultList();
    }
    
    public List<PartidaRodada> selecionarPartidasRodadaAtual(Campeonato campeonato) {
    	return getEntityManager()
    			.createQuery("SELECT p FROM PartidaRodada p WHERE p.fase.campeonato = :campeonato AND :hoje BETWEEN p.rodada.dataInicioRodada AND p.rodada.dataFimRodada " + ORDER_BY_PARTIDA, PartidaRodada.class)
    			.setParameter("campeonato", campeonato)
    			.setParameter("hoje", new Date())
    			.getResultList();
    }
}
