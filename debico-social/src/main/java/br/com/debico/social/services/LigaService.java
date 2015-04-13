package br.com.debico.social.services;

import java.util.List;

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
	 * Cadastra uma nova liga.
	 * 
	 * @param nome da liga
	 * @param emailAdmin email do usuário administrador
	 * @return A liga cadastrada com o id definido.
	 */
	Liga cadastrarNovaLiga(final String nome, final String emailAdmin) throws CadastroLigaException;

	/**
	 * Remove o apostador da Liga.
	 * 
	 * @param idLiga
	 * @param idUsuarioApostador
	 * @return true se realizado com sucesso.
	 */
	boolean removerApostador(final long idLiga, final int idUsuarioApostador);
	
	/**
	 * Inscreve o apostador na liga.
	 * 
	 * @param idLiga
	 * @param idUsuarioApostador
	 * @return true se realizado com sucesso.
	 */
	boolean inscreverApostador(final long idLiga, final int idUsuarioApostador);
	
	/**
	 * @see #inscreverApostador(long, int)
	 */
	boolean inscreverApostador(final long idLiga, List<Integer> idsUsuarioApostador);
	
	/**
	 * 
	 * @see #removerApostador(long, int)
	 */
	boolean removerApostador(final long idLiga, List<Integer> idsUsuarioApostador);
}
