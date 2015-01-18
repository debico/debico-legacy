package br.com.debico.core.dao.spring;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Classe de configuração do contexto Spring para implementações JPADAO via JPA.
 * <p/>
 * Adicionar as seguintes propriedades no classpath:
 * <ul>
 *  <li><code>br.com.debico.hibernate.dialect</code></li>
 *  <li><code>br.com.debico.hibernate.entity_packages</code></li>
 *  <li><code>br.com.debico.hibernate.show_sql</code> (opcional)</li>
 *  <li><code>br.com.debico.hibernate.hbm2ddl.auto</code> (opcional)</li>
 *  <li><code>br.com.debico.db.sql.scripts</code> (opcional)</li>
 * </ul>
 * 
 * @since 1.0.0-RC1
 * @author r_fernandes
 * 
 * @see DAOConfig
 */
@Configuration
public abstract class DAOJPAConfig extends DAOConfig {
    
    private Properties hibernateProperties; 
    
    public DAOJPAConfig() {
        this.hibernateProperties = new Properties();
    }

	@Bean
	public AbstractEntityManagerFactoryBean entityManagerFactoryBean() throws Exception {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		hibernateProperties.put("hibernate.dialect", environment.getProperty("br.com.debico.hibernate.dialect"));
		hibernateProperties.put("hibernate.show_sql", environment.getProperty("br.com.debico.hibernate.show_sql"));
		hibernateProperties.put("hibernate.hbm2ddl.auto", environment.getProperty("br.com.debico.hibernate.hbm2ddl.auto"));
		hibernateProperties.put("hibernate.hbm2ddl.import_files", environment.getProperty("br.com.debico.db.sql.scripts"));
		hibernateProperties.put("hibernate.hbm2ddl.import_files_sql_extractor", "org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");

		entityManagerFactoryBean.setDataSource(this.dataSource());
		entityManagerFactoryBean.setPackagesToScan(environment.getProperty("br.com.debico.hibernate.entity_packages"));
		entityManagerFactoryBean.setJpaProperties(hibernateProperties);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		return entityManagerFactoryBean;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	@Override
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(
				this.entityManagerFactoryBean().getObject());

		return jpaTransactionManager;
	} 
}
