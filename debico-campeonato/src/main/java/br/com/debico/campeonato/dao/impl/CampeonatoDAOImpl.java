package br.com.debico.campeonato.dao.impl;

import java.util.List;

import javax.inject.Named;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.CampeonatoDAO;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.model.campeonato.Rodada;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class CampeonatoDAOImpl extends AbstractJPADao<CampeonatoImpl, Integer>
		implements CampeonatoDAO {

	public CampeonatoDAOImpl() {
		super(CampeonatoImpl.class);
	}
	
	public List<CampeonatoImpl> selecionarCampeonatosAtivosNaoFinalizados() {
	    return getEntityManager()
	            .createQuery("SELECT c FROM CampeonatoImpl c WHERE c.ativo = true AND c.finalizado = false", CampeonatoImpl.class)
	            .getResultList();
	}

	
	@SuppressWarnings("unchecked")
	public <T extends Campeonato> T selecionarPorPermalink(final String permalink) {
		return (T) getEntityManager()
				.createQuery("SELECT c FROM CampeonatoImpl c WHERE c.permalink = :permalink", CampeonatoImpl.class)
				.setParameter("permalink", permalink)
				.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Campeonato> T selecionarPorRodada(Rodada rodada) {
		return (T) DataAccessUtils.singleResult(getEntityManager()
				.createQuery("SELECT c FROM CampeonatoImpl c, FaseImpl f, Ranking r, Rodada d WHERE d.ranking = r AND r.fase = f AND f.campeonato = c AND d = :rodada", CampeonatoImpl.class)
				.setParameter("rodada", rodada)
				.getResultList());
	}

}
