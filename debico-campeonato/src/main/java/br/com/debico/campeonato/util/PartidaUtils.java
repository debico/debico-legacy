package br.com.debico.campeonato.util;

import java.util.Collection;

import br.com.debico.model.PartidaBase;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;

/**
 * 
 * @since 2.0.5
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 */
public final class PartidaUtils {

    private PartidaUtils() {

    }

    /**
     * Procura pela partida informada dentro da coleção. Uma partida é
     * considerada "encontrada" caso possua mesmo time mandante e visitante.
     */
    public static final PartidaBase procuraPartida(
	    Collection<? extends PartidaBase> partidas, PartidaBase partida) {
	return Iterators.find(partidas.iterator(),
		new BuscaPartidaMesmoTimePredicate(partida), null);
    }

    private static final class BuscaPartidaMesmoTimePredicate implements
	    Predicate<PartidaBase> {
	private final PartidaBase partida;

	public BuscaPartidaMesmoTimePredicate(PartidaBase partida) {
	    this.partida = partida;
	}

	@Override
	public boolean apply(PartidaBase input) {
	    return input.getMandante().getNome()
		    .equalsIgnoreCase(this.partida.getMandante().getNome())
		    && input.getVisitante()
			    .getNome()
			    .equalsIgnoreCase(
				    this.partida.getVisitante().getNome());
	}
    }

}
