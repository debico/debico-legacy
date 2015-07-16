package br.com.debico.bolao.spring;

import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import br.com.debico.bolao.batch.config.SumarizacaoJobsConfig;
import br.com.debico.core.spring.config.InfrastructureConfig;
import br.com.tecnobiz.spring.config.dao.base.ProfileBasedDaoConfig;
import br.com.tecnobiz.spring.config.dao.profiles.ContainerJPA;
import br.com.tecnobiz.spring.config.dao.profiles.EmbeddedJPA;
import br.com.tecnobiz.spring.config.dao.profiles.StandaloneJPA;

// funciona em conjunto com JPA. Caso necessário outro modelo, criar outra classe de configuração.
@EmbeddedJPA
@ContainerJPA
@StandaloneJPA
/*
 * TODO ficar de olho em https://jira.spring.io/browse/BATCH-2161 por causa do
 * WARN do log
 */
@EnableBatchProcessing(modular = true)
@Configuration
@ComponentScan("br.com.debico.bolao.batch.impl")
@Import(ProfileBasedDaoConfig.class)
public class ModularBatchProcessorsConfig extends DefaultBatchConfigurer {

    @Inject
    protected InfrastructureConfig infrastructureConfig;

    @Inject
    protected EntityManagerFactory entityManagerFactory;

    protected DataSource dataSource;

    // infelizmente é necessário recriar o TransactionManager por conta
    // da configuração do batch sobrescrever a definição do bean
    @Override
    public PlatformTransactionManager getTransactionManager() {
	return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public ApplicationContextFactory sumarizacaoJobs() {
	return new GenericApplicationContextFactory(SumarizacaoJobsConfig.class);
    }

}
