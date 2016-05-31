package br.com.debico.campeonato.services.impl;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Strings.emptyToNull;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.inject.Named;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.services.ExploraWebResultadosJogosService;
import br.com.debico.campeonato.util.PlacarUtils;
import br.com.debico.campeonato.util.TimesUtil;
import br.com.debico.core.helpers.DefaultLocale;
import br.com.debico.model.PartidaBase;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.StatusPartida;
import br.com.debico.model.Time;
import br.com.debico.model.campeonato.Campeonato;

import com.google.common.base.Charsets;

/**
 * Efetua a busca no site http://www.tabeladobrasileirao.net/ pelos resultados
 * do Campeonato Brasileiro.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.5
 */
@Named
@Transactional(readOnly = true)
class ExploraTabelaBrasileiraoResultadosJogosService implements
	ExploraWebResultadosJogosService<PartidaRodada> {

    private URL siteURL;
    private static final String PROTOCOL_HTTP = "http";
    private static final DateTimeFormatter FMT = DateTimeFormat.forPattern(
	    "dd/MM/yyyy HH:mm").withLocale(DefaultLocale.LOCALE);
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

    public ExploraTabelaBrasileiraoResultadosJogosService() {

    }

    @Override
    public void setPesquisaURL(URL siteURL) {
	this.siteURL = siteURL;
    }

    private boolean isProtocolPesquisaURLHTTP() {
	return this.siteURL.getProtocol().contains(PROTOCOL_HTTP);
    }

    /**
     * Formata a data de acordo com os dados da tabela HTML do Site.
     * 
     * @param data
     * @param hora
     * @param ano
     * @return
     */
    private Date formatarData(String data, String hora, int ano) {
	data = firstNonNull(data, "").trim();
	hora = firstNonNull(hora, "").trim();
	if (emptyToNull(data) == null) {
	    return null;
	}
	if (emptyToNull(hora) == null) {
	    hora = HORA_PADRAO_JOGO;
	}
	return DateTime.parse(String.format("%s/%s %s", data, ano, hora), FMT)
		.toDate();
    }

    private boolean verificarIntegridadePartida(final PartidaBase partida) {
	return partida.getVisitante() != null && partida.getMandante() != null
		&& partida.getDataHoraPartida() != null;
    }

    @Override
    public List<PartidaRodada> recuperarPartidasFinalizadas(Campeonato campeonato) {
	final List<PartidaRodada> partidas = new ArrayList<>();
	final Set<Time> times = campeonato.getTimes();
	final int anoAtual = DateTime.now().year().get();

	try {
	    Document doc = null;
	    if (isProtocolPesquisaURLHTTP()) {
		doc = Jsoup.connect(this.siteURL.getPath()).get();
	    } else {
		doc = Jsoup.parse(this.siteURL.openStream(),
			Charsets.UTF_8.name(), "");
	    }

	    final Element tabelaResultados = doc.select("table#jogos").first();
	    final Elements linhaJogos = tabelaResultados.select("tr");
	    for (Element jogo : linhaJogos) {
		if (!jogo.hasClass("titulo")) {
		    Time timeMandante = TimesUtil.procuraTime(
			    jogo.child(IDX_MANDANTE).text(), times);
		    Time timeVisitante = TimesUtil.procuraTime(
			    jogo.child(IDX_VISITANTE).text(), times);
		    PartidaRodada partida = new PartidaRodada();
		    partida.setMandante(timeMandante);
		    partida.setVisitante(timeVisitante);
		    partida.setStatus(StatusPartida.ND);
		    partida.setComputadaCampeonato(false);
		    partida.setLocal(jogo.child(IDX_LOCAL1).text());
		    partida.setPlacar(PlacarUtils.novoPlacarOuNull(
			    jogo.child(IDX_GOLS_MANDANTE).text(),
			    jogo.child(IDX_GOLS_VISITANTE).text()));
		    partida.setDataHoraPartida(formatarData(jogo
			    .child(IDX_DATA).text(), jogo.child(IDX_HORA)
			    .text(), anoAtual));
		    if (verificarIntegridadePartida(partida)) {
			partidas.add(partida);
		    }
		}
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return partidas;
    }
}
