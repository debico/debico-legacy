package br.com.debico.social.services;

import java.util.List;

import br.com.debico.model.Apostador;
import br.com.debico.social.model.Liga;

/**
 * Serviços relacionados à relação liga e apostador.
 * 
 * @author ricardozanini
 *
 */
public interface LigaApostadorService {

	/**
	 * Relaciona todos apostadores de determinada liga.
	 * 
	 * @param idLiga
	 * @return lista com apostadores.
	 */
	List<Apostador> consultarApostadores(final long idLiga);

	/**
	 * Inscreve o apostador na liga.
	 * 
	 * @param idLiga
	 * @param idUsuarioApostador
	 * @return true se realizado com sucesso.
	 */
	boolean inscreverApostador(final long idLiga, final int idUsuarioApostador);

	/**
	 * @see #inscreverApostador(Liga, Apostador)
	 */
	boolean inscreverApostador(final Liga liga, final int idUsuarioApostador);

	/**
	 * Atalho caso o código cliente tenha esses objetos "em mãos".
	 * 
	 * @param liga
	 * @param apostador
	 * @return true se realizado com sucesso.
	 * @see #inscreverApostador(long, int)
	 */
	boolean inscreverApostador(final Liga liga, final Apostador apostador);

	/**
	 * @see #inscreverApostador(long, int)
	 */
	boolean inscreverApostador(final long idLiga,
			List<Integer> idsUsuarioApostador);

	/**
	 * @see #inscreverApostador(long, int)
	 */
	boolean inscreverApostador(final Liga liga,
			List<Integer> idsUsuarioApostador);

	/**
	 * Remove o apostador da Liga.
	 * 
	 * @param idLiga
	 * @param idUsuarioApostador
	 * @return true se realizado com sucesso.
	 */
	boolean removerApostador(final long idLiga, final int idUsuarioApostador);

	/**
	 * 
	 * @see #removerApostador(long, int)
	 */
	boolean removerApostador(final Liga liga, final Apostador apostador);

	/**
	 * 
	 * @see #removerApostador(long, int)
	 */
	boolean removerApostador(final Liga liga, final int idUsuarioApostador);

	/**
	 * 
	 * @see #removerApostador(long, int)
	 */
	boolean removerApostador(final Liga liga, List<Integer> idsUsuarioApostador);

	/**
	 * 
	 * @see #removerApostador(long, int)
	 */
	boolean removerApostador(final long idLiga,
			List<Integer> idsUsuarioApostador);

}
