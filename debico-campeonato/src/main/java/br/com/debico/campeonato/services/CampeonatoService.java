package br.com.debico.campeonato.services;

import java.util.List;

import br.com.debico.model.campeonato.CampeonatoImpl;

public interface CampeonatoService {
	
	List<CampeonatoImpl> selecionarCampeonatosAtivos();
	
    /**
     * Recupera os dados do campeonato de acordo com o {@link CampeonatoImpl#getPermalink()}.
     * 
     * @param permalink
     * @return
     */
	CampeonatoImpl selecionarCampeonato(String permalink);
	
	/**
	 * Seleciona o campeonato pelo identificador especificado.
	 * @param id
	 * @return
	 */
	CampeonatoImpl selecionarCampeonato(int id);

}
