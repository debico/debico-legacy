package br.com.debico.campeonato.factories.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;

import br.com.debico.campeonato.factories.EstruturaCampeonatoFactory;
import br.com.debico.campeonato.model.EstruturaCampeonato;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.Placar;
import br.com.debico.model.Time;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.model.campeonato.FaseGrupos;
import br.com.debico.model.campeonato.FaseUnica;
import br.com.debico.model.campeonato.ParametrizacaoCampeonato;
import br.com.debico.model.campeonato.PontuacaoTime;
import br.com.debico.model.campeonato.Rodada;
import br.com.debico.model.campeonato.Tabela;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import static com.google.common.base.Strings.emptyToNull;

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
 * Serão instanciados um {@link CampeonatoPontosCorridos} em {@link FaseGrupos}
 * e {@link Rodada} única.
 * <p/>
 * Considera que, após todos os jogos, o primeiro colocado é campeão e o último
 * seria "rebaixado".
 * <p/>
 * Pelo menos dois times são necessários.
 * <p />
 * Todos os jogos acontecem a partir de amanhã e um por dia.
 * 
 */
public class QuadrangularSimplesFactory implements EstruturaCampeonatoFactory {

	public QuadrangularSimplesFactory() {

	}

	@Override
	public EstruturaCampeonato criarCampeonato(String nomeCampeonato,
			List<Time> times) {
		checkNotNull(emptyToNull(nomeCampeonato));
		checkNotNull(times);
		checkArgument(times.size() >= 2);

		// parametrizacao
		ParametrizacaoCampeonato param = ParametrizacaoCampeonato
				.parametrizacaoPrimeiroUltimo(0);

		CampeonatoPontosCorridos campeonato = CampeonatoFactorySupport
				.newCampeonatoPonstosCorridos(nomeCampeonato);
		campeonato.setParametrizacao(param);
		campeonato.addTime(times);

		EstruturaCampeonato estruturaCampeonato = new EstruturaCampeonato(
				campeonato);

		FaseUnica faseUnica = FaseUnica.novaFaseUnica(campeonato);
		Tabela tabela = Tabela.novaTabelaUnica(faseUnica);
		Rodada rodada = Rodada.novaRodadaUnica(tabela);

		estruturaCampeonato.setFases(Collections.singletonList(faseUnica));
		estruturaCampeonato.setRankings(Collections.singletonList(tabela));
		estruturaCampeonato.setRodadas(Collections.singletonList(rodada));

		List<PontuacaoTime> pontuacao = new ArrayList<PontuacaoTime>();
		List<PartidaRodada> partidas = new ArrayList<PartidaRodada>();
		PartidaRodada partida = null;
		int proximo = 0;

		for (int i = 0; i < times.size(); i++) {
			proximo = (i == times.size() - 1 ? 0 : i + 1);
			partida = new PartidaRodada(times.get(i), times.get(proximo),
					new Placar());
			partida.setDataHoraPartida(new DateTime().plusDays(i + 1).toDate());
			partida.setFase(faseUnica);
			partida.setRodada(rodada);

			pontuacao.add(new PontuacaoTime(tabela, times.get(i)));
			partidas.add(partida);
		}

		estruturaCampeonato.setPontuacao(pontuacao);
		estruturaCampeonato.setPartidas(partidas);

		return estruturaCampeonato;
	}

}
