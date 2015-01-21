package br.com.debico.campeonato;

import java.util.List;

import br.com.debico.model.campeonato.Rodada;


/**
 * Utilizada para interagir com a Rodada, independente do tipo de Campeonato ou Partida. 
 * 
 * @author ricardozanini
 * @since 1.0.0
 */
public interface RodadaService {
	
	/**
	 * Recupera todas as rodadas de determinado campeonato. 
	 * 
	 * @param campeonato
	 * @return
	 */
	List<Rodada> selecionarRodadas(String campeonatoPermalink);

}
