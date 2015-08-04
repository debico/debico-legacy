package br.com.debico.social.services;

import java.util.List;

import br.com.debico.social.CadastroLigaException;
import br.com.debico.social.model.Liga;

import com.google.common.base.Optional;

/**
 * Serviços de interesse da Liga.
 * 
 * @author ricardozanini
 *
 */
public interface LigaService {
	
	// TODO: adicionar cache.
	/**
	 * Seleciona todas as ligas de determinado usuário.
	 * 
	 * @param idUsuario Identificador de usuario do apostador.
	 * @return lista com as ligas
	 */
	List<Liga> consultarLiga(final int idUsuario);
	
	Optional<Liga> recuperarLiga(final long idLiga);

	
	/**
	 * Cadastra uma nova liga.
	 * 
	 * @param nome
	 *            da liga
	 * @param idUsuario
	 *            identificador de usuario do apostador administrador
	 * @return A liga cadastrada com o id definido.
	 */
	Liga cadastrarNovaLiga(final String nome, final int idUsuario)
			throws CadastroLigaException;
	
	/**
	 * Atualiza o nome da liga (gerando um novo permalink).
	 * 
	 * @param idLiga identificador
	 * @param nome da liga
     * @param idUsuario
     *            identificador de usuario do apostador administrador
     * @return {@link Liga} alterada.
	 */
	Liga atualizarLiga(final long idLiga, final String nome, final int idUsuario) throws CadastroLigaException;

	
}
