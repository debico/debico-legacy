package br.com.debico.bolao.services;

import java.util.List;

import br.com.debico.bolao.ApostadorJaInscritoException;
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

	List<ApostadorPontuacao> listarRanking(final Campeonato campeonato);
	
	/**
	 * Lista o Ranking de acordo com os integrantes da liga.
	 * 
	 * @param idCampeonato
	 * @param idLiga
	 * @return
	 */
	List<ApostadorPontuacao> listarRankingPorLiga(final int idCampeonato, final long idLiga);

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
