package br.com.debico.campeonato.dao.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.RodadaDAO;
import br.com.debico.core.dao.jpa.AbstractJPADAO;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.Rodada;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class RodadaDAOImpl extends AbstractJPADAO<Rodada, Integer> implements RodadaDAO {

    public RodadaDAOImpl() {
        super(Rodada.class);
    }
    
    public void inserir(Rodada rodada) {
        super.inserir(rodada);
    }
    
    public Rodada selecionarPorId(int id) {
    	return super.selecionarPorId(id);
    }

    public Rodada selecionarRodadaAtual(final Campeonato campeonato) {
    		return DataAccessUtils.singleResult(
    		        getEntityManager()
        			.createQuery("SELECT NEW br.com.debico.model.campeonato.Rodada(r.id, r.nome, r.ordem) FROM Rodada r WHERE r.ranking.fase.campeonato = :c AND r.dataInicioRodada >= :hoje ORDER BY r.dataInicioRodada", Rodada.class)
        			.setMaxResults(1)
        			.setParameter("c", campeonato)
        			.setParameter("hoje", new Date())
        			.getResultList()
        			);
    }
    
    public List<Rodada> selecionarRodadas(final Campeonato campeonato) {
    	return getEntityManager()
    			.createQuery("SELECT NEW br.com.debico.model.campeonato.Rodada(r.id, r.nome, r.ordem) FROM Rodada r WHERE r.ranking.fase.campeonato = :c ORDER BY r.ordem", Rodada.class)
    			.setParameter("c", campeonato)
    			.getResultList();	
    }
    
    public Rodada selecionarUltimaRodada(final Campeonato campeonato) {
    	return DataAccessUtils.singleResult(
    	        getEntityManager()
    			.createQuery("SELECT r FROM Rodada r WHERE r.ranking.fase.campeonato = :c ORDER BY r.dataFimRodada DESC", Rodada.class)
    			.setMaxResults(1)
    			.setParameter("c", campeonato)
    			.getResultList());
    }
    
    public List<Rodada> selecionarRodadasNaoCalculadas(Campeonato campeonato) {
    	return getEntityManager()
    			.createQuery("SELECT DISTINCT NEW br.com.debico.model.campeonato.Rodada(r.id) FROM Rodada r, PartidaRodada p WHERE p.rodada = r AND r.ranking.fase.campeonato = :c AND p.placar IS NOT NULL AND p.status = br.com.debico.model.StatusPartida.ND", Rodada.class)
    			.setParameter("c", campeonato)
    			.getResultList();
    }
}
