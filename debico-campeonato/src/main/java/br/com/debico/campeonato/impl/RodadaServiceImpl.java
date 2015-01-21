package br.com.debico.campeonato.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.RodadaService;
import br.com.debico.campeonato.dao.CampeonatoDAO;
import br.com.debico.campeonato.dao.RodadaDAO;
import br.com.debico.model.campeonato.Rodada;

@Named
@Transactional(readOnly = true)
class RodadaServiceImpl implements RodadaService {

	@Inject
	private RodadaDAO rodadaDAO;
	
	@Inject
	private CampeonatoDAO campeonatoDAO;
	
	public RodadaServiceImpl() {
		
	}

	public List<Rodada> selecionarRodadas(final String campeonatoPermalink) {
		return rodadaDAO.selecionarRodadas(campeonatoDAO.selecionarPorPermalink(campeonatoPermalink));
	}

}
