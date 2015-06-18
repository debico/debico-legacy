package br.com.debico.bolao.services;

import java.util.List;

import br.com.debico.bolao.model.view.PartidaRodadaPalpiteView;

/**
 * 
 * Interface para tratar dos casos de uso de consulta de palpite com partida.
 * Informações normalmente disponibilizadas em views/widgets.
 * 
 * @since 2.0.0
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 */
public interface PartidaPalpiteService {
    
    /**
     * Recupera uma lista de partidas com ou sem palpites. 
     * <p/>
     * Utilizado pela view de palpites de campeonatos com pontos corridos.
     * 
     * @return
     */
    List<PartidaRodadaPalpiteView> recuperarPalpitesRodada(int idRodada, String username);
    
    /**
     * Recupera uma lista de partidas com ou sem palpites.
     * <p/>
     * Utilizado pela view de palpites de campeonatos com pontos corridos.
     * 
     * @param permalink do campeonato em questão.
     * @param rodadaOrdinal ordem da rodada: 1, 2, 3, 4, etc.
     * @param username do apostador
     * @return lista com as rodadas
     */
    List<PartidaRodadaPalpiteView> recuperarPalpitesRodadaPorOrdinal(String permalink, int rodadaOrdinal, String username);

}
