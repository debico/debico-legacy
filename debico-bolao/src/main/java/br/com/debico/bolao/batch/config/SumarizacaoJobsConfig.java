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

import br.com.debico.bolao.batch.BolaoJobConstants;
import br.com.debico.bolao.dao.mappers.ApostadorPontuacaoRodadaRowMapper;
import br.com.debico.bolao.dao.names.TBApostadorPontuacaoRodada;
import br.com.debico.bolao.dao.setters.ApostadorPontuacaoRodadaParameterSourceProvider;
import br.com.debico.bolao.model.ApostadorPontuacaoRodada;
import br.com.debico.bolao.services.ApostadorPontuacaoService;
import br.com.debico.core.dao.DAOUtils;
import br.com.debico.core.spring.config.InfrastructureConfig;

import com.google.common.primitives.Ints;

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
        return jobs
                .get(BolaoJobConstants.JOB_SUMARIZAR_PONTUACAO_APOSTADOR_RODADA)
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
            @Value("#{jobParameters['rodada_id']}") final Long rodadaId,
            ApostadorPontuacaoService apostadorPontuacaoService) {
        final MethodInvokingTaskletAdapter taskletAdapter = new MethodInvokingTaskletAdapter();
        taskletAdapter.setTargetMethod("removerPontuacaoRodada");
        taskletAdapter.setTargetObject(apostadorPontuacaoService);
        taskletAdapter
                .setArguments(new Object[] { Ints.checkedCast(rodadaId) });
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
        cursor.setSql(getSQLItemReader());
        return cursor;
    }

    private String getSQLItemReader() {
        final StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT ")
                .append(" SUM(P.IN_EMPATE) AS NU_EMPATE,")
                .append(" SUM(P.IN_ERRADO) AS NU_ERRADOS,")
                .append(" SUM(P.IN_GOL) AS NU_GOLS,")
                .append(" SUM(P.IN_PLACAR) AS NU_PLACAR,")
                .append(" SUM(P.NU_PONTOS) AS NU_PONTOS,")
                .append(" SUM(P.IN_VENCEDOR) AS NU_VENCEDOR,")
                .append(" A.ID_CAMPEONATO, A.ID_APOSTADOR, R.ID_RODADA")
                .append(" FROM")
                .append(" tb_apostador_campeonato AS A LEFT join")
                .append(" tb_palpite as P ON (A.ID_APOSTADOR = P.ID_APOSTADOR) inner join")
                .append(" tb_partida as R ON (P.ID_PARTIDA = R.ID_PARTIDA)")
                .append(" WHERE R.ID_RODADA = ? AND A.ID_CAMPEONATO = ?")
                .append(" GROUP BY A.ID_CAMPEONATO, A.ID_APOSTADOR, R.ID_RODADA");

        return buffer.toString();
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
            @Value("#{jobParameters['rodada_id']}") final Long rodadaId,
            @Value("#{jobParameters['campeonato_id']}") final Long campeonatoId) {
        return new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setLong(1, rodadaId);
                ps.setLong(2, campeonatoId);
            }
        };
    }

}
