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
	 * @param emailUsuario
	 * @return lista com as ligas
	 */
	List<Liga> consultarLiga(final String emailUsuario);
	
	Optional<Liga> recuperarLiga(final long idLiga);

	
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
	 * Atualiza o nome da liga (gerando um novo permalink).
	 * 
	 * @param idLiga identificador
	 * @param nome da liga
	 * @param emailAdmin do administrador da liga. Apenas ele pode alterar a liga.
	 * @return {@link Liga} alterada.
	 */
	Liga atualizarLiga(final long idLiga, final String nome, final String emailAdmin) throws CadastroLigaException;

	
}
