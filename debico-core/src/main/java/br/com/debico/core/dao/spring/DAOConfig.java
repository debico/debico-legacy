package br.com.debico.core.dao.spring;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.PlatformTransactionManager;


/**
 * Classe base de configuração para a camada de dados.
 * <p/>
 * Algumas referências:
 * <ul>
 * <li>http://gordondickens.com/wordpress/2012/06/12/spring-3-1-environment-profiles/</li>
 * <li>http://docs.spring.io/spring/docs/3.2.x/javadoc-api/org/springframework/jdbc/datasource/DriverManagerDataSource.html</li>
 * <li>http://docs.spring.io/spring/docs/3.1.x/javadoc-api/org/springframework/context/annotation/Configuration.html</li>
 * <li>http://docs.spring.io/spring/docs/3.1.x/javadoc-api/org/springframework/context/annotation/Profile.html</li>
 * <li>http://gordondickens.com/wordpress/2013/02/28/database-config-spring-3-2-environment-profiles/comment-page-1/</li>
 * </ul>
 * 
 * @author r_fernandes
 * @since 1.0.0-RC1
 */
@Configuration
public abstract class DAOConfig {
    
    @Inject
    protected Environment environment;
    
    public void setEnvironment(final Environment environment) {
        this.environment = environment;
    }
    
    @Bean(destroyMethod = "close")
    public abstract DataSource dataSource() throws Exception;
    
    @Bean
    public abstract PlatformTransactionManager transactionManager() throws Exception;

}
