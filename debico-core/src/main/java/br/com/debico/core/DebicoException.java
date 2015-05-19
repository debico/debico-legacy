package br.com.debico.core;

import static com.google.common.base.Strings.emptyToNull;

import java.util.Locale;

import org.springframework.context.MessageSource;

import com.google.common.base.Strings;

/**
 * Exception geral lancada pela API.
 * 
 * @author r_fernandes
 * @since 1.0.0
 */
public abstract class DebicoException extends Exception {

    private static final long serialVersionUID = 973747437420133526L;

    private String messageCode;
    private Object[] messageArgs;

    public DebicoException() {
	super();
	this.messageArgs = new Object[] {};
    }

    /**
     * @deprecated utilize {@link #DebicoException(String, Throwable, String)}
     */
    public DebicoException(final String message, final Throwable inner) {
	super(message, inner);
    }

    public DebicoException(final String message) {
	super(message);
    }

    /**
     * @deprecated utilize {@link #DebicoException(Throwable, String)}
     */
    public DebicoException(final Throwable inner) {
	super(inner);
    }

    /**
     * @deprecated utilize {@link #DebicoException(Throwable, String)}
     */
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
    public final String getMessageCode() {
	return emptyToNull(messageCode);
    }

    public final Object[] getMessageArgs() {
	return messageArgs;
    }

    public final void setMessageArgs(Object... messageArgs) {
	this.messageArgs = messageArgs;
    }

    public final void setMessageCode(String messageCode) {
	this.messageCode = messageCode;
    }

    public final boolean hasMessageCode() {
	return !Strings.isNullOrEmpty(messageCode);
    }

    /**
     * Recupera a mensagem formatada.
     * 
     * @param messageSource
     * @return
     */
    public final String getMessage(final MessageSource messageSource) {
	if (hasMessageCode()) {
	    return messageSource.getMessage(messageCode, messageArgs,
		    Locale.getDefault());
	}

	return this.getLocalizedMessage();
    }

}
