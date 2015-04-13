package br.com.debico.campeonato.factories.impl;

import br.com.debico.core.helpers.WebUtils;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;

/**
 * Helper para as factories.
 * 
 * @author ricardozanini
 * @since 2.0.0
 */
final class CampeonatoFactorySupport {

	private CampeonatoFactorySupport() {

	}
	
	/**
	 * @return uma instância simples de {@link CampeonatoImpl} a partir do nome enviado.
	 */
	public static final CampeonatoImpl newCampeonato(final String nome) {
		CampeonatoImpl campeonato = new CampeonatoImpl(nome);
		campeonato.setAtivo(true);
		campeonato.setFinalizado(false);
		campeonato.setPermalink(WebUtils.toPermalink(nome));
		
		return campeonato;
	}
	
	public static final CampeonatoPontosCorridos newCampeonatoPonstosCorridos(final String nome) {
		CampeonatoPontosCorridos campeonato = new CampeonatoPontosCorridos(nome);
		campeonato.setAtivo(true);
		campeonato.setFinalizado(false);
		campeonato.setPermalink(WebUtils.toPermalink(nome));
		
		return campeonato;
	}

}
