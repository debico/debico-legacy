package br.com.debico.core.dao.spring;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.debico.core.spring.profiles.Dev;

@Dev
@Configuration
@PropertySource(value = "classpath:/debico-embedded.properties", ignoreResourceNotFound = true)
@EnableTransactionManagement
public class DAOJPAEmbeddedConfig extends DAOJPAConfig {

    @Override
    @Bean(destroyMethod = "shutdown")
    public DataSource dataSource() throws Exception {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = 
                builder
                    .setType(EmbeddedDatabaseType.H2)
                    .build();

        return db;
    }
}
