package br.com.debico.campeonato.services;

import java.util.ArrayList;
import java.util.List;

import br.com.debico.model.PartidaRodada;
import br.com.debico.model.Time;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.model.campeonato.FaseGrupos;
import br.com.debico.model.campeonato.FaseUnica;
import br.com.debico.model.campeonato.Grupo;
import br.com.debico.model.campeonato.ParametrizacaoCampeonato;
import br.com.debico.model.campeonato.Rodada;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import static com.google.common.base.Strings.emptyToNull;

/**
 * Responsável por criar uma estrutura coesa de um objeto do tipo
 * {@link Campeonato} a depender do cenário e/ou contexto desejado.
 * 
 * @author ricardozanini
 *
 */
public final class CampeonatoFactory {

	private CampeonatoFactory() {

	}

	/**
	 * Dado uma lista de times já criados, persistidos e com Identificadores
	 * definidos, cria um quadrangular simples onde todos enfrentam todos em uma
	 * única fase em uma única rodada. Não há ida e volta.
	 * <p/>
	 * Exemplo:
	 * <p/>
	 * Dado os times: ARGENTINA, BRASIL, ALEMANHA. Temos:
	 * <p/>
	 * Fase Única, onde:
	 * <p/>
	 * ARGENTINA x BRASIL <br/>
	 * BRASIL x ALEMANHA <br/>
	 * ALEMANHA x ARGENTINA
	 * <p/>
	 * Todos serão mandantes pelo menos uma vez. A ordem dos jogos é aleatória.
	 * <p/>
	 * Serão instanciados um {@link CampeonatoPontosCorridos} em
	 * {@link FaseGrupos} e {@link Rodada} única.
	 * <p/>
	 * Considera que, após todos os jogos, o primeiro colocado é campeão e o
	 * último seria "rebaixado".
	 * <p/>
	 * Pelo menos dois times são necessários.
	 * 
	 * @param times
	 * @return
	 */
	public static CampeonatoPontosCorridos quadrangularSimples(
			final String nomeCampeonato, final List<Time> times) {
		checkNotNull(emptyToNull(nomeCampeonato));
		checkNotNull(times);
		checkArgument(times.size() >= 2);

		// parametrizacao
		ParametrizacaoCampeonato param = new ParametrizacaoCampeonato();
		param.setPosicaoLimiteElite(1);
		param.setPosicaoLimiteInferior(1);

		CampeonatoPontosCorridos campeonato = new CampeonatoPontosCorridos(
				nomeCampeonato);
		campeonato.setAtivo(true);
		campeonato.setFinalizado(false);
		campeonato.addTime(times);
		campeonato.setParametrizacao(param);

		FaseUnica faseUnica = FaseUnica.novaFaseUnica();
		faseUnica.setCampeonato(campeonato);

		Grupo grupo = Grupo.novoGrupoUnico(faseUnica);

		Rodada rodada = Rodada.novaRodadaUnica(grupo);

		List<PartidaRodada> partidas = new ArrayList<PartidaRodada>();

		for (Time timeA : times) {
			for (Time timeB : times) {
				if (!timeA.equals(timeB)) {
					for (PartidaRodada partida : partidas) {
						if (!(partida.getMandante().equals(timeA) && partida
								.getVisitante().equals(timeB))
								|| !(partida.getMandante().equals(timeB) && partida
										.getVisitante().equals(timeA))) {
							partidas.add(new PartidaRodada(timeA, timeB));
						}
					}

					break;
				}
			}
		}

		return campeonato;
	}

}
