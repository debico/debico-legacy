package br.com.debico.campeonato.model;

import java.util.Collections;
import java.util.List;

import br.com.debico.model.Partida;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.model.campeonato.Fase;
import br.com.debico.model.campeonato.FaseImpl;
import br.com.debico.model.campeonato.PontuacaoTime;
import br.com.debico.model.campeonato.Ranking;
import br.com.debico.model.campeonato.Rodada;

/**
 * Uma estrutura que contém os componentes de um campeonato uma vez
 * estruturados. Normalmente utilizada para o tráfego de dados ou persistência
 * em base.
 * <p/>
 * Sequência de persistência:
 * <ol>
 * <li>{@link CampeonatoImpl}</li>
 * <li>{@link Fase}</li>
 * <li>{@link Ranking}</li>
 * <li>{@link Rodada}</li>
 * </ol>
 * 
 * @author ricardozanini
 *
 */
public final class EstruturaCampeonato {

	private final CampeonatoImpl campeonato;

	private List<? extends FaseImpl> fases;

	private List<? extends Ranking> rankings;

	private List<Rodada> rodadas;

	private List<? extends Partida> partidas;
	
	private List<PontuacaoTime> pontuacao;

	public EstruturaCampeonato(final CampeonatoImpl campeonato) {
		this.campeonato = campeonato;
	}

	public CampeonatoImpl getCampeonato() {
		return campeonato;
	}

	public List<? extends FaseImpl> getFases() {
		return Collections.unmodifiableList(fases);
	}

	public void setFases(List<? extends FaseImpl> fases) {
		this.fases = Collections.unmodifiableList(fases);
	}

	public List<? extends Ranking> getRankings() {
		return Collections.unmodifiableList(rankings);
	}

	public void setRankings(List<? extends Ranking> rankings) {
		this.rankings = Collections.unmodifiableList(rankings);
	}

	public List<Rodada> getRodadas() {
		return Collections.unmodifiableList(rodadas);
	}

	public void setRodadas(List<Rodada> rodadas) {
		this.rodadas = Collections.unmodifiableList(rodadas);
	}

	public List<? extends Partida> getPartidas() {
		return Collections.unmodifiableList(partidas);
	}

	public void setPartidas(List<? extends Partida> partidas) {
		this.partidas = Collections.unmodifiableList(partidas);
	}
	
	public List<PontuacaoTime> getPontuacao() {
		return Collections.unmodifiableList(pontuacao);
	}
	
	public void setPontuacao(List<PontuacaoTime> pontuacao) {
		this.pontuacao = Collections.unmodifiableList(pontuacao);
	}

}
