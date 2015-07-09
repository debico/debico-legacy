package br.com.debico.bolao.spring;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.com.debico.bolao.dao.mappers.ApostadorPontuacaoRodadaRowMapper;
import br.com.debico.bolao.model.ApostadorPontuacaoRodada;
import br.com.debico.core.spring.config.InfrastructureConfig;
// no futuro, se precisar dividir a configuração do batch, verificar na anotação como fazer.
@EnableBatchProcessing
@Configuration
@Import(InfrastructureConfig.class)
public class BatchProcessorsConfig {
    
    @Inject
    protected DataSource dataSource;

    public BatchProcessorsConfig() {
        
    }
    
    @Bean
    public Job sumarizarPontuacaoApostadorPorRodada( JobBuilderFactory jobs ){
        return jobs.get("sumarizarPontuacaoApostadorPorRodada")
                .incrementer(new RunIdIncrementer())
                .flow(null)
                .end()
                .build();
    }
    
    @Bean
    public Step step1SumarizarPontuacaoApostadorPorRodada(StepBuilderFactory steps) {
        return steps.get("step1SPAPR").chunk(100).reader(this.pontuacaoSumarizadaReader()).build();
    }
    
    @Bean
    public ItemReader<ApostadorPontuacaoRodada> pontuacaoSumarizadaReader() {
        final JdbcCursorItemReader<ApostadorPontuacaoRodada> cursor = new JdbcCursorItemReader<ApostadorPontuacaoRodada>();
        cursor.setDataSource(dataSource);
        cursor.setIgnoreWarnings(true);
        cursor.setRowMapper(new ApostadorPontuacaoRodadaRowMapper());
        cursor.setSaveState(false);
        cursor.setSql("");
        return cursor;
    }
    
    public ItemWriter<ApostadorPontuacaoRodada> pontucaoSumarizadaWriter() {
        final JdbcBatchItemWriter<ApostadorPontuacaoRodada> itemWriter = new JdbcBatchItemWriter<ApostadorPontuacaoRodada>();
        
        return itemWriter;
    }

}
