package br.com.debico.bolao.services;

import java.util.List;

import br.com.debico.bolao.ApostadorJaInscritoException;
import br.com.debico.model.AbstractApostadorPontuacao;
import br.com.debico.model.Apostador;
import br.com.debico.model.ApostadorPontuacao;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.CampeonatoImpl;

/**
 * Responsável por interagir com as entidades de Ranking de Apostadores.
 * 
 * @author ricardozanini
 *
 */
public interface ApostadorPontuacaoService {

    /**
     * Todos os apostadores no campeonato.
     * 
     * @param campeonato
     * @return
     */
	List<ApostadorPontuacao> listarRanking(final Campeonato campeonato);
	
	/**
	 * Lista o Ranking de acordo com os integrantes da liga.
	 * 
	 * @param idCampeonato
	 * @param idLiga
	 * @return
	 */
	// id do campeonato por causa do cache
	List<ApostadorPontuacao> listarRankingPorLiga(final int idCampeonato, final long idLiga);
	
	/**
	 * Lista o Ranking de acordo com a rodada no campeonato em questão.
	 * 
	 * @param idCampeonato
	 * @param idRodada
	 * @return
	 */
	List<AbstractApostadorPontuacao> listarRankingPorRodada(final int idRodada);

	/**
	 * Lista o Ranking de acordo com a rodada e a liga em questão.
	 * 
	 * @param idRodada
	 * @param idLiga
	 * @return
	 */
	List<AbstractApostadorPontuacao> listarRankingPorRodadaELiga(final int idRodada, final long idLiga);
	
	void inscreverApostadorCampeonato(final Apostador apostador,
			final CampeonatoImpl campeonato)
			throws ApostadorJaInscritoException;

	/**
	 * O mesmo que
	 * {@link #inscreverApostadorCampeonato(Apostador, CampeonatoImpl)}, sem
	 * lançar a <code>exception</code>.
	 * 
	 * @param apostador
	 * @param campeonato
	 */
	void inscreverApostadorCampeonatoSileciosamente(final Apostador apostador,
			final CampeonatoImpl campeonato);

}
