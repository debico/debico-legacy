package br.com.debico.resultados.managers;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.DefaultProcessorPipeline;
import br.com.debico.resultados.Processor;
import br.com.debico.resultados.ProcessorBeans;
import br.com.debico.resultados.ProcessorManager;
import br.com.debico.resultados.ProcessorPipeline;

@Named
@Transactional(readOnly = false)
class BolaoProcessorManager implements ProcessorManager<Campeonato> {

    private final ProcessorPipeline processorPipeline;

    @Inject
    private List<CampeonatoProcessorManager<? extends Campeonato>> campeonatoProcessorManager;
    
    @Inject
    @Named(ProcessorBeans.COMPUTA_PALPITES)
    private Processor computarPalpites;

    public BolaoProcessorManager() {
	this.processorPipeline = new DefaultProcessorPipeline();
    }

    @Override
    public void init() {
	checkNotNull(campeonatoProcessorManager,
		"Nao ha processadores de campeonato definidos.");
	for (CampeonatoProcessorManager<? extends Campeonato> mgmt : campeonatoProcessorManager) {
	    this.processorPipeline.addProcessor(mgmt);
	}
	
	this.processorPipeline.addProcessor(computarPalpites);
    }

    @Override
    public List<Context> start(Campeonato parameter) {
	this.processorPipeline.doProcess(null);
	
	return null;
    }

    @Override
    public List<Context> start() {
	return null;
    }

}
