package br.com.debico.bolao;

import java.util.Locale;

import org.springframework.context.MessageSource;

import br.com.debico.core.DebicoException;
import br.com.debico.core.MessagesCodes;

/**
 * Lançada em caso de tentativa de realizar um palpite já feito para determinada
 * partida.
 * 
 * @author ricardozanini
 * @since 1.0.0
 */
public class PalpiteExistenteException extends DebicoException {

	private static final long serialVersionUID = 660731192475155603L;

	public PalpiteExistenteException(final MessageSource messageSource) {
		super(messageSource.getMessage(MessagesCodes.PALPITE_EXISTENTE, null,
				Locale.getDefault()));
	}

}
