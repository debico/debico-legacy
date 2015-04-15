package br.com.debico.core;

import static com.google.common.base.Strings.emptyToNull;

/**
 * Exception geral lancada pela API.
 * 
 * @author r_fernandes
 * @since 1.0.0
 */
public abstract class DebicoException extends Exception {

	private static final long serialVersionUID = 973747437420133526L;

	private String messageCode;

	/**
	 * @deprecated utilize {@link #DebicoException(String, Throwable, String)}
	 */
	public DebicoException(final String message, final Throwable inner) {
		super(message, inner);
	}

	/**
	 * @deprecated utilize {@link #DebicoException(String, String)}
	 */
	public DebicoException(final String message) {
		super(message);
	}

	/**
	 * @deprecated utilize {@link #DebicoException(Throwable, String)}
	 */
	public DebicoException(final Throwable inner) {
		super(inner);
	}

	public DebicoException(final String message, final Throwable inner,
			final String messageCode) {
		super(message, inner);
		this.messageCode = messageCode;
	}

	public DebicoException(final String message, final String messageCode) {
		super(message);
		this.messageCode = messageCode;
	}

	public DebicoException(final Throwable inner, final String messageCode) {
		super(inner);
		this.messageCode = messageCode;
	}

	/**
	 * Código da mensagem de um arquivo i81n. Amplamente utilizado em views.
	 * 
	 * @return nulo se não possuir um código.
	 */
	public String getMessageCode() {
		return emptyToNull(messageCode);
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	
	public boolean hasMessageCode() {
		return emptyToNull(this.messageCode) != null;
	}

}
