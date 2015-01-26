package br.com.debico.campeonato.dao;

import br.com.debico.model.campeonato.ParametrizacaoCampeonato;

public interface CampeonatoParametrizacaoDAO {

	ParametrizacaoCampeonato selecionarPorId(Integer idCampeonato);
	
}
