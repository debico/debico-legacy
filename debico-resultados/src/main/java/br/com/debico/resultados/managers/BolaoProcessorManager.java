package br.com.debico.resultados.managers;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.CampeonatoBeans;
import br.com.debico.campeonato.services.CampeonatoService;
import br.com.debico.core.helpers.CacheKeys;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.ContextImpl;
import br.com.debico.resultados.DefaultProcessorPipeline;
import br.com.debico.resultados.ManagerBeans;
import br.com.debico.resultados.Processor;
import br.com.debico.resultados.ProcessorBeans;
import br.com.debico.resultados.ParameterizeProcessorManager;
import br.com.debico.resultados.ProcessorPipeline;

import com.google.common.collect.Lists;

@Named(ManagerBeans.BOLAO_MANAGER)
@Transactional(readOnly = false)
class BolaoProcessorManager implements ParameterizeProcessorManager<Campeonato> {

    private final ProcessorPipeline processorPipeline;

    @Inject
    private List<CampeonatoProcessorManager<? extends Campeonato>> campeonatoProcessorManager;

    @Inject
    @Named(ProcessorBeans.COMPUTA_PALPITES)
    private Processor computarPalpites;

    @Inject
    @Named(CampeonatoBeans.CAMPEONATO_SERVICE)
    private CampeonatoService campeonatoService;

    public BolaoProcessorManager() {
	this.processorPipeline = new DefaultProcessorPipeline();
    }

    @PostConstruct
    public void init() {
	checkNotNull(campeonatoProcessorManager,
		"Nao ha processadores de campeonato definidos.");
	for (CampeonatoProcessorManager<? extends Campeonato> mgmt : campeonatoProcessorManager) {
	    this.processorPipeline.addProcessor(mgmt);
	}

	this.processorPipeline.addProcessor(computarPalpites);
    }

    @CacheEvict(value = { CacheKeys.TABELA_CAMPEONATO,
	    CacheKeys.RANKING_APOSTADORES, CacheKeys.DESEMPENHO_IND_APOSTADOR }, allEntries = true)
    public List<Context> start(Campeonato campeonato) {
	return Collections.singletonList(this.doStart(campeonato));
    }

    @CacheEvict(value = { CacheKeys.TABELA_CAMPEONATO,
	    CacheKeys.RANKING_APOSTADORES, CacheKeys.DESEMPENHO_IND_APOSTADOR }, allEntries = true)
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
	this.processorPipeline.execute(context);

	return context;
    }

}
