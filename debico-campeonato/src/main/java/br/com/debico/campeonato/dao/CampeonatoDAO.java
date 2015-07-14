package br.com.debico.campeonato.dao;

import java.util.List;

import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.model.campeonato.Rodada;
import br.com.tecnobiz.spring.config.dao.Dao;

public interface CampeonatoDAO extends Dao<CampeonatoImpl, Integer> {

	List<CampeonatoImpl> selecionarCampeonatosAtivosNaoFinalizados();

	<T extends Campeonato> T selecionarPorPermalink(String permalink);

	<T extends Campeonato> T selecionarPorRodada(Rodada rodada);
	
	/**
	 * Seleciona todos os campeonatos pontos corridos ativos e não finalizados.
	 * 
	 * @since 2.0.4
	 * @return
	 */
	List<CampeonatoPontosCorridos> selecionarCampeonatosPCAtivosNaoFinalizados();

}
