package br.com.debico.campeonato.services.impl;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Strings.emptyToNull;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Charsets;

import br.com.debico.campeonato.CampeonatoBeans;
import br.com.debico.campeonato.services.CampeonatoService;
import br.com.debico.campeonato.services.ExploraWebResultadosException;
import br.com.debico.campeonato.services.ExploraWebResultadosJogosService;
import br.com.debico.campeonato.services.RodadaService;
import br.com.debico.campeonato.util.PlacarUtils;
import br.com.debico.campeonato.util.RodadaUtils;
import br.com.debico.campeonato.util.TimeUtils;
import br.com.debico.core.helpers.DefaultLocale;
import br.com.debico.model.PartidaBase;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.StatusPartida;
import br.com.debico.model.Time;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.Rodada;

/**
 * Efetua a busca no site http://www.tabeladobrasileirao.net/ pelos resultados
 * do Campeonato Brasileiro.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.5
 */
@Named
@Transactional(readOnly = true)
class ExploraTabelaBrasileiraoNetResultadosJogosService implements ExploraWebResultadosJogosService<PartidaRodada> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExploraTabelaBrasileiraoNetResultadosJogosService.class);
	private static final String PROTOCOL_HTTP = "http";
	private static final DateTimeFormatter FMT = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm")
			.withLocale(DefaultLocale.LOCALE);

	private static final AdicionadorPartida ADICIONADOR_PARTIDA_SEM_PLACAR = new AdicionadorPartidaSemPlacar();
	private static final AdicionadorPartida ADICIONADOR_PARTIDA_COM_PLACAR = new AdicionadorPartidaComPlacar();
	/**
	 * Hora padrão caso não encontre no site.
	 */
	private static final String HORA_PADRAO_JOGO = "16:00";
	/*
	 * Índices de busca na linha da tabela do HTML
	 */
	private static final int IDX_RODADA = 0;
	private static final int IDX_DATA = 1;
	private static final int IDX_HORA = 3;
	private static final int IDX_MANDANTE = 4;
	private static final int IDX_GOLS_MANDANTE = 5;
	private static final int IDX_GOLS_VISITANTE = 7;
	private static final int IDX_VISITANTE = 8;
	private static final int IDX_LOCAL1 = 9;

	@SuppressWarnings("unused")
	private static final int IDX_LOCAL2 = 10;

	@Inject
	private RodadaService rodadaService;

	@Inject
	@Named(CampeonatoBeans.CAMPEONATO_SERVICE)
	private CampeonatoService campeonatoService;

	public ExploraTabelaBrasileiraoNetResultadosJogosService() {

	}

	private boolean isProtocolPesquisaURLHTTP(URL siteURL) {
		return siteURL.getProtocol().contains(PROTOCOL_HTTP);
	}

	/**
	 * Formata a data de acordo com os dados da tabela HTML do Site.
	 * 
	 * @param diaMes
	 * @param hora
	 * @param ano
	 * @return
	 */
	private Date formatarData(String diaMes, String hora, int ano) {
		diaMes = firstNonNull(diaMes, "").trim();
		hora = firstNonNull(hora, "").trim();
		if (emptyToNull(diaMes) == null) {
			return null;
		}
		if (emptyToNull(hora) == null) {
			hora = HORA_PADRAO_JOGO;
		}
		return DateTime.parse(String.format("%s/%s %s", diaMes, ano, hora), FMT).toDate();
	}

	/**
	 * Com base no dia e mês distribuido pelo site, tentamos adivinhar o ano da
	 * data com base nas informações do campeonato.
	 * 
	 * @param diaMes
	 * @param campeonato
	 * @return
	 */
	private Date recuperarDataPartida(String diaMes, String hora, Campeonato campeonato) {
		int anoAtual = DateTime.now().getYear();

		// nao temos o ano da partida
		if (campeonato.getDataInicio() == null || campeonato.getDataFim() == null) {
			return formatarData(diaMes, hora, anoAtual);
		}

		final int anoIni = LocalDateTime.fromDateFields(campeonato.getDataInicio()).getYear();
		final int anoFim = LocalDateTime.fromDateFields(campeonato.getDataFim()).getYear();
		// campeonato no mesmo ano, a data da partida só pode ser dentro desse
		// periodo.
		if (anoIni == anoFim) {
			return formatarData(diaMes, hora, anoIni);
		}

		final Date data1 = formatarData(diaMes, hora, anoIni);
		final Date data2 = formatarData(diaMes, hora, anoFim);

		if (data1.after(campeonato.getDataInicio()) && data1.before(campeonato.getDataFim())) {
			return data1;
		} else {
			return data2;
		}

	}

	private List<PartidaRodada> doRecuperarPartidas(int campeonatoId, AdicionadorPartida callback, URL siteURL)
			throws ExploraWebResultadosException {
		final Campeonato campeonato = campeonatoService.selecionarCampeonato(campeonatoId);
		LOGGER.debug("[doRecuperarPartidas] Tentando recuperar partidas no site/arquivo {} do campeonato {}", siteURL,
				campeonato);
		final List<PartidaRodada> partidas = new ArrayList<>();
		final Set<Time> times = campeonato.getTimes();
		final List<Rodada> rodadas = rodadaService.selecionarRodadasNaoCalculadasIncuindoSemPlacar(campeonato);
		LOGGER.debug("[doRecuperarPartidas] Rodadas recuperadas [{}]: {}", rodadas.size(), rodadas);

		try {
			Document doc = null;
			if (isProtocolPesquisaURLHTTP(siteURL)) {
				LOGGER.debug("[doRecuperarPartidas] Tentando efetuar a conexao em {}", siteURL);
				doc = Jsoup.connect(siteURL.toExternalForm()).get();
			} else {
				LOGGER.debug("[doRecuperarPartidas] Tentando ler o arquivo {}", siteURL);
				doc = Jsoup.parse(siteURL.openStream(), Charsets.UTF_8.name(), "");
			}

			final Element tabelaResultados = doc.select("table#jogos").first();
			final Elements linhaJogos = tabelaResultados.select("tr");
			for (Element jogo : linhaJogos) {
				if (!jogo.hasClass("titulo")) {
					LOGGER.trace("Realizando o parse da tag {}", jogo);
					PartidaRodada partida = new PartidaRodada();
					partida.setMandante(TimeUtils.procuraTime(jogo.child(IDX_MANDANTE).text(), times));
					partida.setVisitante(TimeUtils.procuraTime(jogo.child(IDX_VISITANTE).text(), times));
					partida.setRodada(RodadaUtils.procuraRodadaPorOrdem(jogo.child(IDX_RODADA).ownText(), rodadas));
					partida.setStatus(StatusPartida.ND);
					partida.setComputadaCampeonato(false);
					partida.setLocal(jogo.child(IDX_LOCAL1).text());
					partida.setPlacar(PlacarUtils.novoPlacarOuNull(jogo.child(IDX_GOLS_MANDANTE).text(),
							jogo.child(IDX_GOLS_VISITANTE).text()));
					partida.setDataHoraPartida(
							recuperarDataPartida(jogo.child(IDX_DATA).text(), jogo.child(IDX_HORA).text(), campeonato));
					LOGGER.trace("Partida criada apos o parse {}", jogo);
					callback.adicionarPartida(partidas, partida);
				}
			}
		} catch (IOException e) {
			throw new ExploraWebResultadosException("Nao foi possivel conectar no site", e);
		} catch (Exception e) {
			throw new ExploraWebResultadosException("Erro em tempo de execucao durante o parse", e);
		}
		return partidas;
	}

	@Override
	public List<PartidaRodada> recuperarPartidas(int campeonatoId, URL siteURL) throws ExploraWebResultadosException {
		return this.doRecuperarPartidas(campeonatoId, ADICIONADOR_PARTIDA_SEM_PLACAR, siteURL);
	}

	@Override
	public List<PartidaRodada> recuperarPartidasFinalizadas(int campeonatoId, URL siteURL)
			throws ExploraWebResultadosException {
		// finalizada se possui placar, certo?
		return this.doRecuperarPartidas(campeonatoId, ADICIONADOR_PARTIDA_COM_PLACAR, siteURL);
	}

	// ----------- Inner
	private static abstract class AdicionadorPartida {
		abstract void adicionarPartida(Collection<PartidaRodada> partidas, PartidaRodada partida);

		protected boolean verificarIntegridadePartida(final PartidaBase partida) {
			return partida.getVisitante() != null && partida.getMandante() != null
					&& partida.getDataHoraPartida() != null;
		}
	}

	private static final class AdicionadorPartidaSemPlacar extends AdicionadorPartida {
		@Override
		public void adicionarPartida(Collection<PartidaRodada> partidas, PartidaRodada partida) {

			if (verificarIntegridadePartida(partida)) {
				partidas.add(partida);
				LOGGER.debug("[adicionarPartida] Partida adicionada {}", partida);
				return;
			}
			LOGGER.debug("[adicionarPartida] Partida nao adicionada {}", partida);
		}
	}

	private static final class AdicionadorPartidaComPlacar extends AdicionadorPartida {
		@Override
		public void adicionarPartida(Collection<PartidaRodada> partidas, PartidaRodada partida) {
			if (verificarIntegridadePartida(partida) && partida.getPlacar() != null) {
				partidas.add(partida);
				LOGGER.debug("[adicionarPartida] Partida adicionada {}", partida);
				return;
			}
			LOGGER.debug("[adicionarPartida] Partida nao adicionada {}", partida);
		}
	}

}
