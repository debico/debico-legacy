package br.com.debico.resultados.managers;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.CampeonatoBeans;
import br.com.debico.campeonato.services.CampeonatoService;
import br.com.debico.core.DebicoException;
import br.com.debico.core.helpers.CacheKeys;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.ContextImpl;
import br.com.debico.resultados.DefaultProcessorPipeline;
import br.com.debico.resultados.ManagerBeans;
import br.com.debico.resultados.Processor;
import br.com.debico.resultados.ProcessorBeans;
import br.com.debico.resultados.ProcessorManager;
import br.com.debico.resultados.ProcessorPipeline;

import com.google.common.collect.Lists;

/**
 * Efetua o fetch dos dados de partida de uma fonte Web e atualiza os dados de
 * partidas em rodadas ainda não processadas.
 * 
 * @since 2.0.5
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
@Named(ManagerBeans.ATUALIZACAO_DADOS_PARTIDA_MANAGER)
@Transactional(readOnly = false)
class AtualizacaoDadosPartidaDaWebProcessorManager implements ProcessorManager {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(AtualizacaoDadosPartidaDaWebProcessorManager.class);

    private final ProcessorPipeline processorPipeline;

    @Inject
    @Named(ProcessorBeans.RECUPERA_PARTIDAS_WEB)
    private Processor recuperaPartidasWebProcessor;

    @Inject
    @Named(ProcessorBeans.ATUALIZA_PLACAR_DATA_PARTIDAS)
    private Processor atualizaPlacarDataPartidaProcessor;

    @Inject
    @Named(CampeonatoBeans.CAMPEONATO_SERVICE)
    private CampeonatoService campeonatoService;

    public AtualizacaoDadosPartidaDaWebProcessorManager() {
	this.processorPipeline = new DefaultProcessorPipeline();
    }

    @PostConstruct
    public void init() {
	LOGGER.debug("[init] Inicializando o manager {}", this);
	checkNotNull(this.recuperaPartidasWebProcessor);
	checkNotNull(this.atualizaPlacarDataPartidaProcessor);
	checkNotNull(this.campeonatoService);
	// na ordem em que são processadas.
	this.processorPipeline.addProcessor(this.recuperaPartidasWebProcessor);
	this.processorPipeline
		.addProcessor(this.atualizaPlacarDataPartidaProcessor);
	LOGGER.debug("[init] Manager {} inicializado com sucesso!", this);
    }

    @CacheEvict(value = CacheKeys.PARTIDAS_RODADA, allEntries = true)
    public List<Context> start() {
	final List<Context> contexts = Lists.newArrayList();
	final List<CampeonatoImpl> campeonatos = campeonatoService
		.selecionarCampeonatosAtivos();

	for (Campeonato campeonato : campeonatos) {
	    contexts.add(this.doStart(campeonato));
	}

	return contexts;
    }

    private Context doStart(Campeonato campeonato) {
	final Context context = new ContextImpl(campeonato);
	try {
	    this.processorPipeline.execute(context);
	} catch (DebicoException e) {
	    // TODO: enviar email
	    LOGGER.error(
		    "[doStart] Erro ao tentar iniciar o processamento de fetch e atualizao de partidas",
		    e);
	}

	return context;
    }

}
