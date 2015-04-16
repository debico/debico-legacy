package br.com.debico.social;


/**
 * Ocorre em caso de erros relacionados ao relacionamento Liga e Apostador. 
 * 
 * @author ricardozanini
 *
 */
public class InscricaoLigaException extends CadastroLigaException {

	private static final long serialVersionUID = 1340982469242014556L;

	public InscricaoLigaException(String message, Throwable inner,
			String messageCode) {
		super(message, inner, messageCode);
	}

	public InscricaoLigaException(String message, String messageCode) {
		super(message, messageCode);
	}

	public InscricaoLigaException(Throwable inner, String messageCode) {
		super(inner, messageCode);
	}

}
