package br.com.debico.resultados.managers;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.services.CampeonatoPontosCorridosService;
import br.com.debico.campeonato.services.RodadaService;
import br.com.debico.core.helpers.CacheKeys;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.ContextImpl;
import br.com.debico.resultados.DefaultProcessorPipeline;
import br.com.debico.resultados.Processor;
import br.com.debico.resultados.ProcessorBeans;
import br.com.debico.resultados.ProcessorPipeline;

import com.google.common.collect.Lists;

@Named
@Transactional(readOnly = false)
class PontosCorridosProcessorManager extends
	CampeonatoProcessorManagerSupport<CampeonatoPontosCorridos> {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(PontosCorridosProcessorManager.class);

    private final ProcessorPipeline processorPipeline;

    @Inject
    private CampeonatoPontosCorridosService campeonatoPontosCorridosService;

    @Inject
    private RodadaService rodadaService;

    @Inject
    @Named(ProcessorBeans.CALCULA_ESTATISTICA_TIME)
    private Processor calculaEstatisticaTime;

    @Inject
    @Named(ProcessorBeans.CALCULA_POSICAO_TIMES)
    private Processor calculaPosicaoTimes;

    @Inject
    @Named(ProcessorBeans.DEFINE_STATUS_PARTIDAS)
    private Processor defineStatusPartidas;

    public PontosCorridosProcessorManager() {
	this.processorPipeline = new DefaultProcessorPipeline();
    }
    
    @Override
    protected Class<CampeonatoPontosCorridos> getType() {
        return CampeonatoPontosCorridos.class;
    }

    @PostConstruct
    public void init() {
	LOGGER.debug("[init] Inicializando o manager {}", this);
	checkNotNull(this.calculaEstatisticaTime);
	checkNotNull(this.calculaPosicaoTimes);
	checkNotNull(this.defineStatusPartidas);
	// na ordem em que são processadas.
	this.processorPipeline.addProcessor(defineStatusPartidas);
	this.processorPipeline.addProcessor(calculaEstatisticaTime);
	this.processorPipeline.addProcessor(calculaPosicaoTimes);
	LOGGER.debug("[init] Manager {} inicializado com sucesso!", this);
    }

    /**
     * Inicia o processamento de todos os campeonatos do tipo Pontos Corridos
     * que estão ativos.
     */
    @CacheEvict(CacheKeys.TABELA_CAMPEONATO)
    public List<Context> start() {
	final List<Context> contexts = Lists.newArrayList();
	final List<CampeonatoPontosCorridos> campeonatoPontosCorridos = campeonatoPontosCorridosService
		.selecionarCampeonatosPontosCorridosAtivos();

	for (CampeonatoPontosCorridos campeonato : campeonatoPontosCorridos) {
	    contexts.add(this.doStart(campeonato));
	}

	return contexts;
    }

    @CacheEvict(CacheKeys.TABELA_CAMPEONATO)
    public List<Context> start(CampeonatoPontosCorridos campeonato) {
	return Collections.singletonList(this.doStart(campeonato));
    }

    private Context doStart(CampeonatoPontosCorridos campeonato) {
	LOGGER.debug(
		"[doStart] Inicializando o processamento do campeonato {}",
		campeonato);
	final Context context = new ContextImpl(campeonato);
	context.setRodadas(rodadaService
		.selecionarRodadasNaoCalculadas(campeonato));

	this.processorPipeline.doProcess(context);
	LOGGER.debug("[doStart] Fim do processamento de {}", campeonato);

	return context;
    }

    /**
     * Atua como um {@link Processor}, por essa razão não cria o contexto. É
     * esperado o campeonato no contexto.
     */
    @CacheEvict(CacheKeys.TABELA_CAMPEONATO)
    public void execute(Context context) {
	checkNotNull(context.getCampeonato(), "O campeonato deve ser definido");

	if (!this.supports(context.getCampeonato())) {
	    LOGGER.debug(
		    "[execute] Campeonato {} nao suportado.",
		    context.getCampeonato());
	    this.executeNext(context);
	}

	LOGGER.debug(
		"[execute] Inicializando o processamento do campeonato {}",
		context.getCampeonato());

	if (context.getRodadas().isEmpty()) {
	    context.setRodadas(rodadaService
		    .selecionarRodadasNaoCalculadas(context.getCampeonato()));
	}

	this.processorPipeline.doProcess(context);
	LOGGER.debug("[execute] Fim do processamento de {}",
		context.getCampeonato());

	this.executeNext(context);
    }

}
