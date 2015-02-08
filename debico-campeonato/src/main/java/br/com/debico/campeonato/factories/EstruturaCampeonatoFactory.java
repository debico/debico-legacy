package br.com.debico.campeonato.factories;

import java.util.List;

import br.com.debico.campeonato.model.EstruturaCampeonato;
import br.com.debico.model.Time;
import br.com.debico.model.campeonato.Campeonato;

/**
 * Responsável por criar uma estrutura coesa de um objeto do tipo
 * {@link Campeonato} a depender do cenário e/ou contexto desejado.
 * 
 * @author ricardozanini
 *
 */
public interface EstruturaCampeonatoFactory {

	EstruturaCampeonato criarCampeonato(String nomeCampeonato, List<Time> times);

}
