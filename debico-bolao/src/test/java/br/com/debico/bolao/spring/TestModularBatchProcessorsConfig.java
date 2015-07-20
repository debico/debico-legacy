package br.com.debico.bolao.spring;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.debico.bolao.batch.BolaoJobConstants;
import br.com.debico.test.spring.AbstractUnitTest;

@ContextConfiguration(classes = ModularBatchProcessorsConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestModularBatchProcessorsConfig extends AbstractUnitTest {

    @Inject
    private JobRegistry jobRegistry;

    @Test
    public void test() throws NoSuchJobException {
	Job job = jobRegistry
		.getJob(BolaoJobConstants.JOB_SUMARIZAR_PONTUACAO_APOSTADOR_RODADA);
	assertNotNull(job);
    }

}
