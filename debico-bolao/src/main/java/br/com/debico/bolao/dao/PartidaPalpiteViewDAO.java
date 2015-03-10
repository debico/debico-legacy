package br.com.debico.bolao.dao;

import java.util.List;

import br.com.debico.bolao.model.PartidaRodadaPalpiteView;

/**
 * Casos de uso para recuperação de Visões relacionadas com partidas e palpites.
 * 
 * @author ricardozanini
 *
 */
public interface PartidaPalpiteViewDAO {
	
	/**
     * Visão de todas as partidas para o apostador realizar os palpites.
     * 
     * @param idRodada o identificador da Rodada
     * @param idApostador o identificador do Apostador
     * @return
     */
    List<PartidaRodadaPalpiteView> selecionarPartidasComSemPalpite(int idRodada, int idApostador);
    
    /**
     * Visão de todas as partidas para o apostador realizar os palpites, por ordinal da Rodada.
     * 
     * @param ordinalRodada
     * @param idApostador
     * @return
     */
	List<PartidaRodadaPalpiteView> selecionarPartidasComSemPalpiteOrdinal(String campeonatoPermalink, int ordinalRodada, int idApostador);
	
}
