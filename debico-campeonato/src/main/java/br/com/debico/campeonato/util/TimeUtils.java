package br.com.debico.campeonato.util;

import static com.google.common.base.Objects.firstNonNull;

import java.util.Collection;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import br.com.debico.model.Time;

/**
 * @since 2.0.5
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
public final class TimeUtils {

	private TimeUtils() {

	}

	public static Time procuraTime(String nome, Collection<Time> times) {
		return Iterables.find(times, new BuscaTimeNomePredicate(nome), null);
	}

	private static class BuscaTimeNomePredicate implements Predicate<Time> {
		private final String nome;

		public BuscaTimeNomePredicate(final String nome) {
			this.nome = firstNonNull(nome, "").trim();
		}

		@Override
		public boolean apply(Time input) {
			return input.getNome().equalsIgnoreCase(nome);
		}
	}

}
