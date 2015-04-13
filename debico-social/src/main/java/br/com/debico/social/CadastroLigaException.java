package br.com.debico.social;

import br.com.debico.core.DebicoException;

/**
 * Exception para o caso de problemas com o cadastro da Liga.
 * 
 * @author ricardozanini
 * @since 2.0.0
 */
public class CadastroLigaException extends DebicoException {

	private static final long serialVersionUID = -1333930546489264610L;

	public CadastroLigaException(String message, Throwable inner) {
		super(message, inner);
	}

	public CadastroLigaException(String message) {
		super(message);
	}

	public CadastroLigaException(Throwable inner) {
		super(inner);
	}

}
