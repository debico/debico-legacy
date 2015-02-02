package br.com.debico.campeonato.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.FaseDAO;
import br.com.debico.campeonato.dao.PartidaDAO;
import br.com.debico.campeonato.dao.PontuacaoTimeDAO;
import br.com.debico.campeonato.dao.RankingDAO;
import br.com.debico.campeonato.dao.RodadaDAO;
import br.com.debico.campeonato.model.EstruturaCampeonato;
import br.com.debico.campeonato.services.CampeonatoFactory;
import br.com.debico.campeonato.services.CampeonatoPontosCorridosService;
import br.com.debico.campeonato.services.EstruturaCampeonatoService;
import br.com.debico.core.helpers.CacheKeys;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.Time;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.model.campeonato.FaseUnica;
import br.com.debico.model.campeonato.PontuacaoTime;
import br.com.debico.model.campeonato.Rodada;
import br.com.debico.model.campeonato.Tabela;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import static com.google.common.base.Strings.emptyToNull;

@Named
@Transactional(readOnly = false)
class CampeonatoPontosCorridosServiceImpl extends CampeonatoServiceImpl
		implements CampeonatoPontosCorridosService {

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
	private EstruturaCampeonatoService estruturaCampeonatoService;

	public Rodada selecionarRodadaAtual(
			CampeonatoPontosCorridos campeonatoPontosCorridos) {
		LOGGER.debug(
				"[selecionarRodadaAtual] Tentando recuperar a rodada atual do campeonato {}",
				campeonatoPontosCorridos);
		checkNotNull(campeonatoPontosCorridos);

		final Rodada rodada = rodadaDAO
				.selecionarRodadaAtual(campeonatoPontosCorridos);

		if (rodada == null) {
			return this.selecionarUltimaRodada(campeonatoPontosCorridos);
		}

		return rodada;
	}

	public Rodada selecionarUltimaRodada(
			CampeonatoPontosCorridos campeonatoPontosCorridos) {
		LOGGER.debug(
				"[selecionarUltimaRodada] Tentando recuperar a ultima rodada do campeonato {}",
				campeonatoPontosCorridos);
		checkNotNull(campeonatoPontosCorridos);

		return rodadaDAO.selecionarUltimaRodada(campeonatoPontosCorridos);
	}

	@Cacheable(CacheKeys.PARTIDAS_RODADA)
	public List<PartidaRodada> selecionarPartidasRodada(int idRodada) {
		LOGGER.debug(
				"[selecionarPartidasRodada] Tentando recuperar as partidas da rodada {}",
				idRodada);
		checkArgument(idRodada > 0, "O id deve ser maior do que 0.");

		return partidaDAO.selecionarPartidasPorRodada(idRodada);
	}

	@Cacheable(CacheKeys.PARTIDAS_RODADA)
	public List<PartidaRodada> selecionarPartidasRodada(int ordinalRodada,
			final String campeonatoPermalink) {
		LOGGER.debug(
				"[selecionarPartidasRodada] Tentando recuperar as partidas da rodada com ordinal {} e campeonato {}",
				ordinalRodada, campeonatoPermalink);
		checkArgument(ordinalRodada > 0, "O ordinal deve ser maior que 0.");
		checkNotNull(emptyToNull(campeonatoPermalink));

		return partidaDAO.selecionarPartidasPorRodada(ordinalRodada,
				campeonatoPermalink);
	}

	@Cacheable(CacheKeys.TABELA_CAMPEONATO)
	public List<PontuacaoTime> selecionarTabela(
			CampeonatoPontosCorridos campeonatoPontosCorridos) {
		LOGGER.debug(
				"[selecionarTabela] Tentando selecionar a Tabela do campeonato {}",
				campeonatoPontosCorridos);

		checkNotNull(campeonatoPontosCorridos);
		List<PontuacaoTime> ranking = pontuacaoTimeDAO
				.selecionarPontuacaoCampeonato(campeonatoPontosCorridos);

		Collections.sort(ranking);

		return ranking;
	}

	@Override
	public CampeonatoPontosCorridos criarQuadrangularSimples(
			String nomeCampeonato, List<Time> times) {
		final EstruturaCampeonato estrutura = CampeonatoFactory.quadrangularSimples(nomeCampeonato, times);
		
		estruturaCampeonatoService.inserirNovaEstrutura(estrutura);
		
		return (CampeonatoPontosCorridos) estrutura.getCampeonato();
	}

	/**
	 * @deprecated mover para {@link CampeonatoFactory}
	 */
	public void definirFaseUnica(
			CampeonatoPontosCorridos campeonatoPontosCorridos) {
		checkNotNull(campeonatoPontosCorridos);
		this.validarEstruturaCampeonato(campeonatoPontosCorridos);

		campeonatoPontosCorridos = (CampeonatoPontosCorridos) this
				.selecionarCampeonato(campeonatoPontosCorridos.getId());

		final FaseUnica faseUnica = FaseUnica.novaFaseUnica();
		faseUnica.setCampeonato(campeonatoPontosCorridos);
		faseDAO.inserir(faseUnica);

		final Tabela tabela = new Tabela(faseUnica);
		rankingDAO.inserir(tabela);

		Rodada rodadaUnica = null;

		for (int i = 1; i <= QUANTIDADE_RODADAS_PADRAO; i++) {
			Rodada rodada = Rodada.novaRodadaNumerada(i);
			rodada.setRanking(tabela);
			rodadaDAO.inserir(rodada);

			if (i == 1) {
				rodadaUnica = rodada;
			}
		}

		final Set<Time> times = campeonatoPontosCorridos.getTimes();

		for (Time timeA : times) {
			pontuacaoTimeDAO.inserirPontuacao(new PontuacaoTime(tabela, timeA));
			// todos contra todos.
			for (Time timeB : times) {
				if (!timeA.equals(timeB)) {
					PartidaRodada partidaRodada = new PartidaRodada(timeA,
							timeB);
					partidaRodada.setRodada(rodadaUnica);
					partidaRodada.setFase(faseUnica);

					partidaDAO.inserir(partidaRodada);
				}
			}
		}
	}

	/**
	 * Realiza a validação da estrutura do campeonato: deve possuir a quantidade
	 * correta de times.
	 * 
	 * @param campeonatoPontosCorridos
	 */
	protected void validarEstruturaCampeonato(
			final CampeonatoPontosCorridos campeonatoPontosCorridos) {
		checkArgument(campeonatoPontosCorridos.getId() > 0,
				"Eh preciso um campeonato carregado e valido.");
		checkNotNull(campeonatoPontosCorridos.getTimes());
		checkArgument(
				campeonatoPontosCorridos.getTimes().size() == QUANTIDADE_TIMES_PADRAO,
				String.format(
						"Incorreta quantidade de times no campeonato. O n\u00famero de times deve ser %s",
						QUANTIDADE_TIMES_PADRAO));
	}

}
