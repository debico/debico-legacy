package br.com.debico.campeonato.services;

import br.com.debico.campeonato.model.EstruturaCampeonato;
import br.com.debico.model.campeonato.Campeonato;

/**
 * Serviço de persistência de estruturas completas de campeonatos.
 * 
 * @author ricardozanini
 * @since 2.0.0
 */
public interface EstruturaCampeonatoService {

	/**
	 * Insere uma nova estrutura de campeonato.
	 * 
	 * @param estruturaCampeonato
	 * @see CampeonatoFactory
	 * @return
	 */
	Campeonato inserirNovaEstrutura(EstruturaCampeonato estruturaCampeonato);

}
