package br.com.debico.social;

import java.util.Locale;

import org.springframework.context.MessageSource;

import br.com.debico.core.DebicoException;

public class CadastroApostadorException extends DebicoException {

    private static final long serialVersionUID = 7607860001594021242L;

    public CadastroApostadorException(final MessageSource messageSource, final String messageCode) {
        super(messageSource.getMessage(messageCode, null, Locale.getDefault()));
    }

    public CadastroApostadorException(final MessageSource messageSource, final String messageCode, final Object... args) {
        super(messageSource.getMessage(messageCode, args, Locale.getDefault()));
    }

    public CadastroApostadorException(final MessageSource messageSource, final String messageCode, final Object arg) {
        super(messageSource.getMessage(messageCode, new Object[] { arg }, Locale.getDefault()));
    }

}
