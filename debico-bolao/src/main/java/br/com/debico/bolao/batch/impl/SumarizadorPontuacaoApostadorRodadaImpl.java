package br.com.debico.bolao.batch.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collections;
import java.util.List;

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
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import br.com.debico.bolao.batch.BolaoJobNames;
import br.com.debico.bolao.batch.SumarizadorPontuacaoApostadorRodada;
import br.com.debico.model.campeonato.AbstractRodada;

@Named
class SumarizadorPontuacaoApostadorRodadaImpl implements
	SumarizadorPontuacaoApostadorRodada {

    private static final String RODADA_ID_PARAM_NAME = "rodada_id";
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
		    .getJob(BolaoJobNames.SUMARIZAR_PONTUACAO_APOSTADOR_RODADA);
	    this.jobLauncher = new SimpleJobLauncher();
	    this.jobLauncher.setJobRepository(jobRepository);
	    this.jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
	    LOGGER.debug("[init] Fim da inicializacao do runner {}", this);
	}
    }

    @Override
    public void sumarizar(List<? extends AbstractRodada> rodadas) {
	try {
	    this.setUpJob();
	} catch (NoSuchJobException nsje) {
	    throw new IllegalStateException(
		    "Erro ao configurar o JOB. Parece que a factory nao foi inicializada",
		    nsje);
	}

	for (AbstractRodada rodada : rodadas) {
	    try {
		jobLauncher
			.run(job,
				new JobParameters(Collections.singletonMap(
					RODADA_ID_PARAM_NAME, new JobParameter(
						Long.valueOf(rodada.getId())))));
	    } catch (JobExecutionException e) {
		LOGGER.debug("[sumarizar] Impossivel executar o job", e);
	    }
	}

    }
}
