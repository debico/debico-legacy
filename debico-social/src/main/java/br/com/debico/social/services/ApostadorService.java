package br.com.debico.social.services;

import java.util.List;

import br.com.debico.model.Apostador;
import br.com.debico.social.CadastroApostadorException;

public interface ApostadorService {

	void atualizarApostador(final Apostador apostador)
			throws CadastroApostadorException;

	/**
	 * Recupera um apostador a partir do seu email.
	 * 
	 * @param email
	 * @return instância de {@link Apostador} ou <code>null</code> caso não
	 *         encontrado.
	 * @since 1.1.0
	 */
	Apostador selecionarApostadorPorEmail(String email);

	/**
	 * Recupera o perfil completo do apostador a partir do seu email.
	 * <p/>
	 * Do contrário de {@link #selecionarApostadorPorEmail(String)}, trás toda a
	 * informação sobre o {@link Apostador}.
	 * <p/>
	 * Utilizar com parcimônia.
	 * 
	 * @param email
	 * @return instância de {@link Apostador} ou <code>null</code> caso não
	 *         encontrado.
	 * @since 1.2.0
	 */
	Apostador selecionarPerfilApostadorPorEmail(String email);

	Apostador selecionarApostadorPorId(int id);

	Apostador selecionarApostadorPorIdUsuario(int idUsuario);

	/**
	 * Efetua a pesquisa por apostadores cadastrados no banco de dados
	 * utilizando o nome ou parte dele. Limita os resultados em 20 registros.
	 * 
	 * @param parteNome
	 * @return lista dos apostadores encontrados.
	 */
	List<Apostador> pesquisarApostadoresPorParteNome(String nomeParcial);
}
