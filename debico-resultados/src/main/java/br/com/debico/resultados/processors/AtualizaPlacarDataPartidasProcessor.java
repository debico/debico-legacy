package br.com.debico.resultados.processors;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.services.CampeonatoPontosCorridosService;
import br.com.debico.campeonato.services.PartidaService;
import br.com.debico.campeonato.services.RodadaService;
import br.com.debico.campeonato.util.PartidaUtils;
import br.com.debico.core.DebicoException;
import br.com.debico.model.PartidaBase;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.campeonato.Rodada;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.ProcessorBeans;

@Named(ProcessorBeans.ATUALIZA_PLACAR_DATA_PARTIDAS)
@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
final class AtualizaPlacarDataPartidasProcessor extends ProcessorSupport {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AtualizaPlacarDataPartidasProcessor.class);

    private RodadaService rodadaService;
    private CampeonatoPontosCorridosService campeonatoService;
    private PartidaService partidaService;

    public AtualizaPlacarDataPartidasProcessor() {
    }

    @Inject
    public void setCampeonatoService(
	    CampeonatoPontosCorridosService campeonatoService) {
	this.campeonatoService = campeonatoService;
    }

    @Inject
    public void setRodadaService(RodadaService rodadaService) {
	this.rodadaService = rodadaService;
    }

    @Inject
    public void setPartidaService(PartidaService partidaService) {
	this.partidaService = partidaService;
    }

    @PostConstruct
    public void init() {
	checkNotNull(this.rodadaService);
	checkNotNull(this.campeonatoService);
    }

    @Override
    public void execute(Context context) throws DebicoException {
	LOGGER.debug(
		"[execute] Iniciando a atualizacao das partidas com base na Web {}",
		context.getCampeonato());
	final List<PartidaBase> partidasWeb = context.getPartidas();
	final List<Rodada> rodadas = this.rodadaService
		.selecionarRodadasNaoCalculadas(context.getCampeonato());

	if (!verificaNecessidadeProcessamento(partidasWeb, rodadas)) {
	    LOGGER.debug(
		    "[execute] Nao ha dados para serem processados {}. Saindo.",
		    context.getCampeonato());
	    return;
	}

	for (Rodada rodada : rodadas) {
	    final List<PartidaRodada> partidasRodada = this.campeonatoService
		    .selecionarPartidasRodada(rodada.getId());
	    for (PartidaBase partidaWeb : partidasWeb) {
		final PartidaBase partida = PartidaUtils.procuraPartida(
			partidasRodada, partidaWeb);
		this.atualizaDataHorario(partida, partidaWeb);
		this.atualizaPlacar(partida, partidaWeb);
	    }
	}
	LOGGER.debug(
		"[execute] Fim da atualizacao das partidas com base na Web {}",
		context.getCampeonato());
	this.executeNext(context);
    }

    private boolean verificaNecessidadeProcessamento(
	    Collection<PartidaBase> partidasWeb,
	    Collection<Rodada> rodadasNaoCalculadas) {
	return !(partidasWeb == null || partidasWeb.isEmpty())
		&& !(rodadasNaoCalculadas == null || rodadasNaoCalculadas
			.isEmpty());
    }

    private void atualizaDataHorario(PartidaBase partida, PartidaBase partidaWeb) {
	if (partida != null && partidaWeb.getDataHoraPartida() != null) {
	    LOGGER.trace("[execute] Atualizando data e horario da partida {}",
		    partida);
	    this.partidaService.atualizarDataHorario(partida.getId(),
		    partidaWeb.getDataHoraPartida());
	}
    }

    private void atualizaPlacar(PartidaBase partida, PartidaBase partidaWeb) {
	//só atualiza se não tiver placar
	if (partida != null && partidaWeb.getPlacar() != null && partida.getPlacar() == null) {
	    LOGGER.trace("[execute] Atualizando placar da partida {}", partida);
	    this.partidaService.salvarPlacar(partida.getId(),
		    partidaWeb.getPlacar());
	}
    }
}
