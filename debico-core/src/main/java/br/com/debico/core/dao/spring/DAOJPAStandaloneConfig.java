package br.com.debico.core.dao.spring;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.debico.core.spring.profiles.Release;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * @see <a href="http://stackoverflow.com/questions/17401583/optional-propertysource-location">Optional @PropertySource location</a>
 * @author ricardozanini
 *
 */
@Release
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:/br/com/debico/core/debico-standalone.properties")
public class DAOJPAStandaloneConfig extends DAOJPAConfig {

	/**
	 * If you want to use something mature and performant (e.g. not Apache DBCP), use BoneCP.
	 * 
	 * @see <a href="http://jolbox.com">BoneCP</a>
	 * @see <a href="http://jolbox.com/bonecp/downloads/site/apidocs/com/jolbox/bonecp/BoneCPConfig.html">BoneCPConfig</a>
	 * @see <a href="http://stackoverflow.com/questions/9745165/what-pooled-data-source-should-i-use-for-spring-3-1-0-hibernate-4-0-1-final-an">What pooled data source should I use?</a>
	 */
	@Override
	public DataSource dataSource() throws Exception {
		BoneCPDataSource dataSource = new BoneCPDataSource();

		dataSource.setDriverClass(environment.getProperty("br.com.debico.db.driver"));
		dataSource.setJdbcUrl(environment.getProperty("br.com.debico.db.url"));
		dataSource.setUsername(environment.getProperty("br.com.debico.db.user"));
		dataSource.setPassword(environment.getProperty("br.com.debico.db.password"));
		dataSource.setMaxConnectionsPerPartition(Integer.parseInt(environment.getProperty("br.com.debico.db.initialsize")));
		dataSource.setConnectionTestStatement(environment.getProperty("br.com.debico.db.validation_query"));

		return dataSource;
	}

}
