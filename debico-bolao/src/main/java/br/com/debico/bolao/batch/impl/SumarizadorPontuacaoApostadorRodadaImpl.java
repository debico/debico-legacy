package br.com.debico.bolao.batch.impl;

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
import org.springframework.batch.core.launch.JobLauncher;

import br.com.debico.bolao.batch.SumarizadorPontuacaoApostadorRodada;
import br.com.debico.model.campeonato.AbstractRodada;

@Named
class SumarizadorPontuacaoApostadorRodadaImpl implements
	SumarizadorPontuacaoApostadorRodada {

    private static final String RODADA_ID_PARAM_NAME = "rodada_id";
    private static final Logger LOGGER = LoggerFactory.getLogger(SumarizadorPontuacaoApostadorRodadaImpl.class);
    
    @Inject
    @Named("sumarizarPontuacaoApostadorRodadaJob")
    private Job job;

    @Inject
    @Named("sumarizarPontuacaoApostadorRodadaLauncher")
    private JobLauncher jobLauncher;

    public SumarizadorPontuacaoApostadorRodadaImpl() {

    }

    @Override
    public void sumarizar(List<? extends AbstractRodada> rodadas) {
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
