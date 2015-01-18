package br.com.debico.campeonato.dao;

import java.util.List;

import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.model.campeonato.Rodada;

public interface CampeonatoDAO {

	void inserir(final CampeonatoImpl campeonato);
	
	List<CampeonatoImpl> selecionarCampeonatosAtivosNaoFinalizados();
	
	<T extends Campeonato> T selecionar(int id);
	
	<T extends Campeonato> T selecionarPorPermalink(String permalink);
	
	void excluir(CampeonatoImpl campeonato);
	
	<T extends Campeonato> T selecionarPorRodada(Rodada rodada);
	
}
