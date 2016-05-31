package br.com.debico.core.helpers;

import java.util.Locale;

/**
 * @since 2.0.5
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
public final class DefaultLocale {

    private DefaultLocale() {
	
    }
    
    /**
     * {@link Locale} padrão da aplicação. Utilizado para conversão de Strings, formatação e etc.
     */
    public static final Locale LOCALE = Locale.forLanguageTag("pt-BR");

}
