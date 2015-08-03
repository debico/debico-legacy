package br.com.debico.resultados.managers;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.cache.annotation.CacheEvict;

import br.com.debico.core.helpers.CacheKeys;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.DefaultProcessorPipeline;
import br.com.debico.resultados.ManagerBeans;
import br.com.debico.resultados.ParameterizeProcessorManager;
import br.com.debico.resultados.Processor;
import br.com.debico.resultados.ProcessorBeans;
import br.com.debico.resultados.ProcessorPipeline;

/**
 * Computa as estatísticas após o processamento das regras do bolão. Execução em
 * separado por tratar de um sumarizador de estatísticas dentro da
 * infra-estrutura batch que requer a sua própria transação.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.4
 */
@Named(ManagerBeans.SUMARIZADOR_MANAGER)
class SumarizadorBolaoProcessorManager implements
	ParameterizeProcessorManager<Campeonato> {

    private final ProcessorPipeline processorPipeline;

    @Inject
    @Named(ManagerBeans.BOLAO_MANAGER)
    private ParameterizeProcessorManager<Campeonato> bolaoManager;

    @Inject
    @Named(ProcessorBeans.SUMARIZA_PONTOS_APOSTADOR_RODADA)
    private Processor sumarizarPontosRodadaApostador;

    public SumarizadorBolaoProcessorManager() {
	processorPipeline = new DefaultProcessorPipeline();
    }

    @PostConstruct
    @Override
    public void init() {
	checkNotNull(bolaoManager, "BolaoManager interno nao pode ser nulo");

	this.processorPipeline.addProcessor(sumarizarPontosRodadaApostador);
    }

    @CacheEvict(value = { CacheKeys.TABELA_CAMPEONATO,
	    CacheKeys.RANKING_APOSTADORES, CacheKeys.DESEMPENHO_IND_APOSTADOR }, allEntries = true)
    @Override
    public List<Context> start() {
	final List<Context> contexts = this.bolaoManager.start();

	for (Context c : contexts) {
	    this.processorPipeline.execute(c);
	}

	return contexts;
    }

    @CacheEvict(value = { CacheKeys.TABELA_CAMPEONATO,
	    CacheKeys.RANKING_APOSTADORES, CacheKeys.DESEMPENHO_IND_APOSTADOR }, allEntries = true)
    @Override
    public List<Context> start(Campeonato parameter) {
	final List<Context> contexts = this.bolaoManager.start(parameter);

	for (Context c : contexts) {
	    this.processorPipeline.execute(c);
	}

	return contexts;
    }

}
