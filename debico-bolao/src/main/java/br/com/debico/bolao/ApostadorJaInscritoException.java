package br.com.debico.bolao;

import java.util.Locale;

import org.springframework.context.MessageSource;

import br.com.debico.core.DebicoException;
import br.com.debico.core.MessagesCodes;
import br.com.debico.model.Apostador;
import br.com.debico.model.campeonato.Campeonato;

public class ApostadorJaInscritoException extends DebicoException {

	private static final long serialVersionUID = -8863398116210453970L;

	public ApostadorJaInscritoException(final MessageSource messageSource, final Apostador apostador, final Campeonato campeonato) {
		super(messageSource.getMessage(MessagesCodes.APOSTADOR_JA_INSCRITO,
				new Object[] { apostador, campeonato }, Locale.getDefault()));
	}

}
