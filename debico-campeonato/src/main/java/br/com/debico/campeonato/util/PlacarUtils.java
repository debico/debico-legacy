package br.com.debico.campeonato.util;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Strings.emptyToNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.debico.model.Placar;

/**
 * @since 2.0.5
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
public final class PlacarUtils {
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(PlacarUtils.class);

    private PlacarUtils() {
    }

    /**
     * Normaliza as Strings de gol (provavelmente vindo de alguma importação
     * externa) e os transforma em instância de {@link Placar}.
     * 
     * @param golsMandante
     * @param golsVisitante
     * @return {@link Placar} válido ou null se as Strings não estiverem de
     *         acordo.
     */
    public static final Placar novoPlacarOuNull(String golsMandante,
	    String golsVisitante) {
	golsMandante = firstNonNull(golsMandante, "").trim();
	golsVisitante = firstNonNull(golsVisitante, "").trim();
	if (emptyToNull(golsMandante) == null
		|| emptyToNull(golsVisitante) == null) {
	    return null;
	}

	try {
	    return new Placar(Integer.parseInt(golsMandante),
		    Integer.parseInt(golsVisitante));
	} catch (NumberFormatException ex) {
	    LOGGER.debug(
		    "Erro na conversao de placar gols mandante: '{}', gols visitante: '{}'",
		    golsMandante, golsVisitante);
	    return null;
	}
    }
}
