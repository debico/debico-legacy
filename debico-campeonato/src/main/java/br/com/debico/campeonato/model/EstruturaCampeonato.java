package br.com.debico.campeonato.model;

import java.util.Collections;
import java.util.List;

import br.com.debico.model.PartidaBase;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.Fase;
import br.com.debico.model.campeonato.Ranking;
import br.com.debico.model.campeonato.Rodada;

/**
 * Uma estrutura que contém os componentes de um campeonato uma vez
 * estruturados. Normalmente utilizada para o tráfego de dados ou persistência
 * em base.
 * <p/>
 * Sequência de persistência:
 * <ol>
 * <li>{@link Campeonato}</li>
 * <li>{@link Fase}</li>
 * <li>{@link Ranking}</li>
 * <li>{@link Rodada}</li>
 * </ol>
 * 
 * @author ricardozanini
 *
 */
public final class EstruturaCampeonato {

	private final Campeonato campeonato;

	private List<? extends Fase> fases;

	private List<? extends Ranking> rankings;

	private List<Rodada> rodadas;

	private List<? extends PartidaBase> partidas;

	public EstruturaCampeonato(final Campeonato campeonato) {
		this.campeonato = campeonato;
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public List<? extends Fase> getFases() {
		return Collections.unmodifiableList(fases);
	}

	public void setFases(List<? extends Fase> fases) {
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

	public List<? extends PartidaBase> getPartidas() {
		return Collections.unmodifiableList(partidas);
	}

	public void setPartidas(List<? extends PartidaBase> partidas) {
		this.partidas = Collections.unmodifiableList(partidas);
	}

}
