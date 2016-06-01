package br.com.debico.campeonato.services;

import br.com.debico.core.DebicoException;

/**
 * Ocorre em caso de Exception durante a exploração (fetch, parse do HTML) de um
 * site com resultados de jogos.
 * 
 * @author Ricardo Zanini
 * @since 2.0.5
 *
 */
public class ExploraWebResultadosException extends DebicoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -507783485629012962L;

	public ExploraWebResultadosException() {

	}

	public ExploraWebResultadosException(String message) {
		super(message);
	}

	@SuppressWarnings("deprecation")
	public ExploraWebResultadosException(String message, Throwable inner) {
		super(message, inner);
	}

	public ExploraWebResultadosException(Throwable inner, String messageCode) {
		super(inner, messageCode);
	}

}
