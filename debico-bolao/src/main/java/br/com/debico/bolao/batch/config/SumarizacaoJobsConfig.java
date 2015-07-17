package br.com.debico.bolao.batch.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.PreparedStatementSetter;

import br.com.debico.bolao.batch.BolaoJobNames;
import br.com.debico.bolao.dao.mappers.ApostadorPontuacaoRodadaRowMapper;
import br.com.debico.bolao.dao.names.TBApostadorPontuacaoRodada;
import br.com.debico.bolao.dao.setters.ApostadorPontuacaoRodadaParameterSourceProvider;
import br.com.debico.bolao.model.ApostadorPontuacaoRodada;
import br.com.debico.campeonato.services.RodadaService;
import br.com.debico.core.dao.DAOUtils;
import br.com.debico.core.spring.config.InfrastructureConfig;

@Configuration
public class SumarizacaoJobsConfig {

    @Inject
    @Named("pontuacaoSumarizadaReaderBean")
    protected ItemReader<ApostadorPontuacaoRodada> pontuacaoSumarizadaReaderBean;

    @Inject
    @Named("pontucaoSumarizadaWriterBean")
    protected ItemWriter<ApostadorPontuacaoRodada> pontucaoSumarizadaWriterBean;

    @Inject
    @Named("removedorRodadaTaskletBean")
    protected MethodInvokingTaskletAdapter removedorRodadaTaskletBean;

    @Inject
    protected InfrastructureConfig infrastructureConfig;

    // ~ CONFIG
    @Bean
    public Job sumarizarPontuacaoApostadorPorRodada(JobBuilderFactory jobs,
	    StepBuilderFactory steps) {
	return jobs.get(BolaoJobNames.SUMARIZAR_PONTUACAO_APOSTADOR_RODADA)
		.incrementer(new RunIdIncrementer())
		.flow(this.step1ExcluirPontuacaoRodada(steps))
		.next(this.step2SumarizarPontuacaoApostadorPorRodada(steps))
		.end().build();
    }

    // ~ STEP 1
    @Bean
    public Step step1ExcluirPontuacaoRodada(StepBuilderFactory steps) {
	return steps.get("step1ExcluirPontuacaoRodada")
		.tasklet(removedorRodadaTaskletBean).build();
    }

    @StepScope
    @Bean(name = "removedorRodadaTaskletBean")
    public MethodInvokingTaskletAdapter removedorRodadaTasklet(
	    @Value("#{jobParameters['rodada_id']}") final int rodadaId,
	    RodadaService rodadaService) {
	final MethodInvokingTaskletAdapter taskletAdapter = new MethodInvokingTaskletAdapter();
	taskletAdapter.setTargetMethod("excluirRodada");
	taskletAdapter.setTargetObject(rodadaService);
	taskletAdapter.setArguments(new Object[] { rodadaId });
	return taskletAdapter;
    }

    // ~ STEP 2
    @Bean
    public Step step2SumarizarPontuacaoApostadorPorRodada(
	    StepBuilderFactory steps) {
	return steps
		.get("step2SumarizarPontuacaoApostadorPorRodada")
		.<ApostadorPontuacaoRodada, ApostadorPontuacaoRodada> chunk(100)
		.reader(pontuacaoSumarizadaReaderBean)
		.writer(pontucaoSumarizadaWriterBean).build();
    }

    @Bean(name = "pontuacaoSumarizadaReaderBean")
    public JdbcCursorItemReader<ApostadorPontuacaoRodada> pontuacaoSumarizadaReader(
	    @Named("pontuacaoSumarizadaReaderPSBean") PreparedStatementSetter ps,
	    DataSource dataSource) {
	final JdbcCursorItemReader<ApostadorPontuacaoRodada> cursor = new JdbcCursorItemReader<ApostadorPontuacaoRodada>();
	cursor.setDataSource(dataSource);
	cursor.setIgnoreWarnings(true);
	cursor.setRowMapper(new ApostadorPontuacaoRodadaRowMapper());
	cursor.setSaveState(false);
	cursor.setPreparedStatementSetter(ps);
	cursor.setSql("SELECT * FROM tb_palpite");
	return cursor;
    }

    @Bean(name = "pontucaoSumarizadaWriterBean")
    public JdbcBatchItemWriter<ApostadorPontuacaoRodada> pontucaoSumarizadaWriter(
	    DataSource dataSource) {
	final JdbcBatchItemWriter<ApostadorPontuacaoRodada> itemWriter = new JdbcBatchItemWriter<ApostadorPontuacaoRodada>();
	itemWriter.setDataSource(dataSource);
	itemWriter
		.setItemSqlParameterSourceProvider(new ApostadorPontuacaoRodadaParameterSourceProvider());
	itemWriter.setSql(DAOUtils.generateSQLInsertNamedParameters(
		"tb_apostador_pontuacao_rodada",
		TBApostadorPontuacaoRodada.TODOS));
	return itemWriter;
    }

    @StepScope
    @Bean(name = "pontuacaoSumarizadaReaderPSBean")
    public PreparedStatementSetter pontuacaoSumarizadaReaderPS(
	    @Value("#{jobParameters['rodada_id']}") final int rodadaId) {
	return new PreparedStatementSetter() {
	    @Override
	    public void setValues(PreparedStatement ps) throws SQLException {
		ps.setInt(0, rodadaId);
	    }
	};
    }

}
