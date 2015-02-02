package br.com.debico.campeonato.services.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.CampeonatoDAO;
import br.com.debico.campeonato.dao.FaseDAO;
import br.com.debico.campeonato.dao.PartidaDAO;
import br.com.debico.campeonato.dao.PontuacaoTimeDAO;
import br.com.debico.campeonato.dao.RankingDAO;
import br.com.debico.campeonato.dao.RodadaDAO;
import br.com.debico.campeonato.model.EstruturaCampeonato;
import br.com.debico.campeonato.services.EstruturaCampeonatoService;
import br.com.debico.model.Partida;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.FaseImpl;
import br.com.debico.model.campeonato.PontuacaoTime;
import br.com.debico.model.campeonato.Ranking;
import br.com.debico.model.campeonato.Rodada;

@Named
@Transactional(readOnly = false)
class EstruturaCampeonatoServiceImpl implements EstruturaCampeonatoService {

	@Inject
	private PontuacaoTimeDAO pontuacaoTimeDAO;

	@Inject
	private RodadaDAO rodadaDAO;

	@Inject
	private FaseDAO faseDAO;

	@Inject
	private RankingDAO rankingDAO;

	@Inject
	private PartidaDAO partidaDAO;

	@Inject
	private CampeonatoDAO campeonatoDAO;

	public EstruturaCampeonatoServiceImpl() {

	}

	@Override
	public Campeonato inserirNovaEstrutura(
			EstruturaCampeonato estruturaCampeonato) {

		campeonatoDAO.inserir(estruturaCampeonato.getCampeonato());

		this.inserirFase(estruturaCampeonato.getFases());
		this.inserirRanking(estruturaCampeonato.getRankings());
		this.inserirRodada(estruturaCampeonato.getRodadas());
		this.inserirPontuacaoTime(estruturaCampeonato.getPontuacao());
		this.inserirPartidas(estruturaCampeonato.getPartidas());

		return estruturaCampeonato.getCampeonato();
	}

	private void inserirFase(final List<? extends FaseImpl> fases) {
		for (FaseImpl fase : fases) {
			faseDAO.inserir(fase);
		}
	}

	private void inserirRodada(final List<? extends Rodada> rodadas) {
		for (Rodada rodada : rodadas) {
			rodadaDAO.inserir(rodada);
		}
	}

	private void inserirRanking(final List<? extends Ranking> rankings) {
		for (Ranking ranking : rankings) {
			rankingDAO.inserir(ranking);
		}
	}

	private void inserirPontuacaoTime(final List<PontuacaoTime> pontuacoes) {
		for (PontuacaoTime pontuacaoTime : pontuacoes) {
			pontuacaoTimeDAO.inserirPontuacao(pontuacaoTime);
		}
	}

	private void inserirPartidas(final List<? extends Partida> partidas) {
		for (Partida partida : partidas) {
			partidaDAO.inserir(partida);
		}
	}

}
