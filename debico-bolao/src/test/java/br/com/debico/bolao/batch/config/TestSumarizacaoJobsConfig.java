package br.com.debico.bolao.batch.config;

import java.util.Map;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.batch.BolaoJobConstants;
import br.com.debico.bolao.test.support.AbstractBolaoUnitTest;

import com.google.common.collect.Maps;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        StepScopeTestExecutionListener.class })
public class TestSumarizacaoJobsConfig extends AbstractBolaoUnitTest {

    private JobLauncherTestUtils jobLauncherTestUtils;

    @Inject
    private JobRegistry jobRegistry;

    @Inject
    private JobRepository jobRepository;

    @Before
    public void setUp() throws NoSuchJobException {
        final SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SyncTaskExecutor());

        jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils
                .setJob(jobRegistry
                        .getJob(BolaoJobConstants.JOB_SUMARIZAR_PONTUACAO_APOSTADOR_RODADA));
    }

    @Test
    public void testSumarizarPontuacaoApostadorPorRodada() throws Exception {
        final Map<String, JobParameter> parameters = Maps.newHashMap();

        parameters.put(BolaoJobConstants.PARAM_CAMPEONATO_ID, new JobParameter(
                1L));
        parameters
                .put(BolaoJobConstants.PARAM_RODADA_ID, new JobParameter(77L));

        final JobParameters jobParameters = new JobParameters(parameters);
        final JobExecution jobExecution = jobLauncherTestUtils
                .launchJob(jobParameters);

        assertThat(jobExecution.getStatus(), is(BatchStatus.COMPLETED));
    }
}
