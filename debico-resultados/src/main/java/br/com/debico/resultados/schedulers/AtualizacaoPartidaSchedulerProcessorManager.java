package br.com.debico.resultados.schedulers;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.scheduling.annotation.Scheduled;

import br.com.debico.resultados.ManagerBeans;
import br.com.debico.resultados.ProcessorManager;
import br.com.debico.resultados.SchedulerProcessorManager;

/**
 * Scheduler que delega o processamento de atualização de dados da partida para
 * o manager responsável.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.5
 */
@Named
class AtualizacaoPartidaSchedulerProcessorManager implements
	SchedulerProcessorManager {

    @Inject
    @Named(ManagerBeans.ATUALIZACAO_DADOS_PARTIDA_MANAGER)
    private ProcessorManager atualizacaoDadosPartidaProcessorManager;

    public AtualizacaoPartidaSchedulerProcessorManager() {

    }

    /**
     * Executa todos os dias às 6 da manhã.
     */
    @Override
    @Scheduled(cron = "0 0 6 ? * *")
    public void start() {
	this.atualizacaoDadosPartidaProcessorManager.start();
    }

}
