package br.com.debico.campeonato.util;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Strings.emptyToNull;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;

import br.com.debico.model.campeonato.Rodada;

/**
 * @since 2.0.5
 * @author rzanini
 *
 */
public final class RodadaUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(RodadaUtils.class);

	private RodadaUtils() {

	}

	public static Rodada procuraRodadaPorOrdem(String ordem, final Collection<Rodada> rodadas) {
		ordem = firstNonNull(ordem, "");
		if (emptyToNull(ordem) == null) {
			return null;
		}

		try {
			return Iterators.find(rodadas.iterator(), new BuscaRodadaOrdemPredicate(Integer.parseInt(ordem)), null);
		} catch (NumberFormatException ex) {
			LOGGER.debug("Impossivel efetuar a busca por Rodada [ordem]: '{}'", ordem);
			return null;
		}
	}

	private static final class BuscaRodadaOrdemPredicate implements Predicate<Rodada> {
		private final int ordem;

		public BuscaRodadaOrdemPredicate(int ordem) {
			this.ordem = ordem;
		}

		@Override
		public boolean apply(Rodada input) {
			if (input == null) {
				return false;
			}
			return ordem == input.getOrdem();
		}
	}
}
