package br.com.debico.ui.utils;

import java.util.Locale;

import org.springframework.context.MessageSource;

import br.com.debico.core.DebicoException;

public final class MessageSourceUtils {

	private MessageSourceUtils() {

	}

	/**
	 * Recupera a mensagem do {@link MessageSource} caso exista, se não retorna
	 * a mensagem da exceção.
	 * 
	 * @param ex
	 * @param messageSource
	 * @return
	 */
	public static String getMessageFromEx(final DebicoException ex,
			final MessageSource messageSource) {

		if (ex.hasMessageCode()) {
			return messageSource.getMessage(ex.getMessageCode(), null,
					Locale.getDefault());
		}

		return ex.getLocalizedMessage();
	}

}
