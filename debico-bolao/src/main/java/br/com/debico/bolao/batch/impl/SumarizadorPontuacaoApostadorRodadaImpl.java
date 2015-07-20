package br.com.debico.bolao.batch.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import br.com.debico.bolao.batch.BolaoJobConstants;
import br.com.debico.bolao.batch.SumarizadorPontuacaoApostadorRodada;
import br.com.debico.model.campeonato.AbstractRodada;
import br.com.debico.model.campeonato.Campeonato;

import com.google.common.collect.Maps;

/**
 * Atua como um <code>wrapper</code> para a execução do Job configurado via
 * Spring Batch.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.4
 */
@Named
class SumarizadorPontuacaoApostadorRodadaImpl implements
	SumarizadorPontuacaoApostadorRodada {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(SumarizadorPontuacaoApostadorRodadaImpl.class);

    private Job job;

    private SimpleJobLauncher jobLauncher;

    @Inject
    private JobRegistry jobRegistry;

    @Inject
    private JobRepository jobRepository;

    public SumarizadorPontuacaoApostadorRodadaImpl() {

    }

    private void setUpJob() throws NoSuchJobException {
	if (this.job == null) {
	    LOGGER.debug("[init] Inicializacao do Runner {}", this);
	    checkNotNull(jobRegistry, "JobRegistry nao definido");
	    checkNotNull(jobRepository, "JobRepository nao definido");

	    this.job = jobRegistry
		    .getJob(BolaoJobConstants.JOB_SUMARIZAR_PONTUACAO_APOSTADOR_RODADA);
	    this.jobLauncher = new SimpleJobLauncher();
	    this.jobLauncher.setJobRepository(jobRepository);
	    this.jobLauncher.setTaskExecutor(new SyncTaskExecutor());
	    LOGGER.debug("[init] Fim da inicializacao do runner {}", this);
	}
    }

    // rodamos de forma assíncrona para forçar não executar dentro da transação
    // do cliente.
    // http://stackoverflow.com/questions/26862521/spring-batch-transaction-exceptionexisting-transaction-detected-in-jobrepositor
    @Async
    @Override
    public Future<List<AbstractRodada>> sumarizarAsync(Campeonato campeonato,
	    List<? extends AbstractRodada> rodadas) {
	return new AsyncResult<List<AbstractRodada>>(this.doSumarizar(
		campeonato, rodadas));
    }

    @Override
    public void sumarizarSync(Campeonato campeonato,
	    List<? extends AbstractRodada> rodadas) {
	this.doSumarizar(campeonato, rodadas);
    }

    @SuppressWarnings("unchecked")
    private List<AbstractRodada> doSumarizar(Campeonato campeonato,
	    List<? extends AbstractRodada> rodadas) {
	try {
	    this.setUpJob();
	} catch (NoSuchJobException nsje) {
	    throw new IllegalStateException(
		    "Erro ao configurar o JOB. Parece que o contexto Batch nao foi inicializado",
		    nsje);
	}

	for (AbstractRodada rodada : rodadas) {
	    final Map<String, JobParameter> params = Maps.newHashMap();
	    params.put(BolaoJobConstants.PARAM_RODADA_ID,
		    new JobParameter(Long.valueOf(rodada.getId())));
	    params.put(BolaoJobConstants.PARAM_CAMPEONATO_ID, new JobParameter(
		    Long.valueOf(campeonato.getId())));
	    try {
		jobLauncher.run(job, new JobParameters(params));
	    } catch (JobExecutionException e) {
		LOGGER.debug("[sumarizar] Impossivel executar o job", e);
	    }
	}

	return (List<AbstractRodada>) rodadas;
    }
}
