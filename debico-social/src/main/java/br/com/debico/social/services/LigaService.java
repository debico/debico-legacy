package br.com.debico.social.services;

import java.util.List;

import br.com.debico.model.Apostador;
import br.com.debico.social.CadastroLigaException;
import br.com.debico.social.model.Liga;

/**
 * Serviços de interesse da Liga.
 * 
 * @author ricardozanini
 *
 */
public interface LigaService {
	
	/**
	 * Seleciona todas as ligas de determinado usuário.
	 * 
	 * @param emailUsuario
	 * @return lista com as ligas
	 */
	List<Liga> consultarLiga(final String emailUsuario);

	/**
	 * Relaciona todos apostadores de determinada liga.
	 * 
	 * @param idLiga
	 * @return lista com apostadores.
	 */
	List<Apostador> consultarApostadores(final long idLiga);
	
	/**
	 * Cadastra uma nova liga.
	 * 
	 * @param nome
	 *            da liga
	 * @param emailAdmin
	 *            email do usuário administrador
	 * @return A liga cadastrada com o id definido.
	 */
	Liga cadastrarNovaLiga(final String nome, final String emailAdmin)
			throws CadastroLigaException;

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
	boolean removerApostador(final Liga liga,
			List<Integer> idsUsuarioApostador);
	
	
	/**
	 * 
	 * @see #removerApostador(long, int)
	 */
	boolean removerApostador(final long idLiga,
			List<Integer> idsUsuarioApostador);
}
