package br.com.debico.core;


/**
 * Exception geral lancada pela API.
 * 
 * @author r_fernandes
 *
 */
public abstract class DebicoException extends Exception {

    private static final long serialVersionUID = 973747437420133526L;

    public DebicoException(final String message, final Throwable inner) {
        super(message, inner);
    }

    public DebicoException(final String message) {
        super(message);
    }

    public DebicoException(final Throwable inner) {
        super(inner);
    }

}
