package br.com.debico.campeonato.services;

import java.util.List;

import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.Rodada;
import br.com.debico.model.campeonato.RodadaMeta;

import com.google.common.base.Optional;

/**
 * Utilizada para interagir com a Rodada, independente do tipo de Campeonato ou
 * Partida.
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

	/**
	 * Seleciona todas as rodadas de determinado {@link Campeonato} que ainda
	 * não foram definidos os resultados. Isso inclui as rodadas com partidas
	 * sem placar.
	 * 
	 * @param campeonato
	 *            desejado
	 * @return rodadas não processadas
	 * @since 2.0.0
	 */
	List<Rodada> selecionarRodadasNaoCalculadas(Campeonato campeonato);
	
	/**
	 * Recupera a rodada em questão (sem as partidas).
	 * 
	 * @param idRodada
	 * @return
	 * @since 2.0.3
	 */
	Optional<Rodada> selecionarRodada(int idRodada);
	
	/**
	 * Recupera as rodadas que possuem partidas calculadas.
	 * 
	 * @param campeonato
	 * @return
	 * @since 2.0.4
	 */
	List<RodadaMeta> selecionarRodadasCalculadas(Campeonato campeonato);

}
