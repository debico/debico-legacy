package br.com.debico.campeonato.factories;

import java.util.Iterator;
import java.util.ServiceLoader;

public final class EstruturaCampeonatoProvider {

	private final ServiceLoader<EstruturaCampeonatoFactory> loader;

	public EstruturaCampeonatoProvider() {
		loader = ServiceLoader.load(EstruturaCampeonatoFactory.class);
	}

	/**
	 * Recupera a primeira Factory que encontrar em
	 * <code>/META-INF/services/br.com.debico.campeonato.factories.EstruturaCampeonatoFactory</code>
	 * 
	 * @return
	 */
	public EstruturaCampeonatoFactory getDefaultFactory() {
		Iterator<EstruturaCampeonatoFactory> it = loader.iterator();

		while (it.hasNext()) {
			return it.next();
		}

		throw new IllegalStateException(
				"Nenhum provider de EstruturaCampeonatoFactory encontrado.");
	}

}
