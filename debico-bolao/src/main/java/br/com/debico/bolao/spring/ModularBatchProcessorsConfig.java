package br.com.debico.bolao.spring;

import javax.inject.Inject;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.com.debico.core.spring.config.InfrastructureConfig;

// TODO ficar de olho em https://jira.spring.io/browse/BATCH-2161 por causa do WARN do log.
@EnableBatchProcessing(modular = true)
@Configuration
@ComponentScan("br.com.debico.bolao.batch.impl")
@Import(InfrastructureConfig.class)
public class ModularBatchProcessorsConfig extends DefaultBatchConfigurer {

    @Inject
    protected InfrastructureConfig infrastructureConfig;

    @Bean
    public ApplicationContextFactory sumarizacaoJobs() {
	return new GenericApplicationContextFactory(
		BatchSumarizacaoJobsConfig.class);
    }

}
