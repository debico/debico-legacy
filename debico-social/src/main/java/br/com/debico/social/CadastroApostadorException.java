package br.com.debico.social;

import java.util.Locale;

import org.springframework.context.MessageSource;

import br.com.debico.core.DebicoException;

public class CadastroApostadorException extends DebicoException {

    private static final long serialVersionUID = 7607860001594021242L;

    /**
     * @deprecated utilize {@link #CadastroApostadorException(String, Object...)}
     */
    public CadastroApostadorException(final MessageSource messageSource, final String messageCode) {
        super(messageSource.getMessage(messageCode, null, Locale.getDefault()));
    }

    /**
     * @deprecated utilize {@link #CadastroApostadorException(String, Object...)}
     */
    public CadastroApostadorException(final MessageSource messageSource, final String messageCode, final Object... args) {
        super(messageSource.getMessage(messageCode, args, Locale.getDefault()));
    }

    /**
     * @deprecated utilize {@link #CadastroApostadorException(String, Object...)}
     */
    public CadastroApostadorException(final MessageSource messageSource, final String messageCode, final Object arg) {
        super(messageSource.getMessage(messageCode, new Object[] { arg }, Locale.getDefault()));
    }
    
    public CadastroApostadorException(final String messageCode, final Object... args) {
	super();
	this.setMessageCode(messageCode);
	this.setMessageArgs(args);
    }

}
