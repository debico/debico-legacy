package br.com.debico.campeonato.brms.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.debico.model.Partida;
import br.com.debico.model.Time;

/**
 * Coleção de utilidades utilizadas no pacote.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */ 
final class ResultadosUtils {

    private ResultadosUtils() {
        
    }
    
    /**
     * Recupera uma lista de times a partir das partidas informadas.
     * 
     * @param partidas
     * @return
     */
    public static Set<Time> recuperarTimes(final List<? extends Partida> partidas) {
        Set<Time> times = new HashSet<Time>();

        for (Partida p : partidas) {
            times.add(p.getMandante());
            times.add(p.getVisitante());
        }

        return times;
    }

}
