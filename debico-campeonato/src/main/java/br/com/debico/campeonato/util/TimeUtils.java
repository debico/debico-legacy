package br.com.debico.campeonato.util;

import static com.google.common.base.Objects.firstNonNull;

import java.util.Collection;

import br.com.debico.model.Time;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;

/**
 * @since 2.0.5
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
public final class TimesUtil {

    private TimesUtil() {

    }

    public static Time procuraTime(String nome, Collection<Time> times) {
	return Iterables.get(
		Collections2.filter(times, new BuscaTimeNomePredicate(nome)),
		0, null);
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
