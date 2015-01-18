package br.com.debico.campeonato;

import br.com.debico.model.Partida;
import br.com.debico.model.Placar;


/**
 * Realiza os casos de uso relacionado com as partidas dos campeonatos.
 * 
 * @author r_fernandes
 * @since 0.1
 */
public interface PartidaService {

    Partida salvarPlacar(int idPartida, Placar placar);
    
}
