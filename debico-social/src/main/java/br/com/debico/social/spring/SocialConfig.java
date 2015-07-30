package br.com.debico.social.spring;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import br.com.debico.core.spring.config.ServicesConfig;
import br.com.debico.notify.spring.NotifyConfig;

/**
 * Deve ser importada por clientes que precisem dos Beans de
 * <code>debico-social</code>.
 * <p />
 * Clientes devem extender {@link ServicesConfig}.
 * 
 * @author ricardozanini
 * @see <a
 *      href="http://docs.spring.io/spring-social/docs/current/reference/htmlsingle">Spring
 *      Social</a>
 */
@Configuration
@EnableSocial
@ComponentScan({ "br.com.debico.social.dao", "br.com.debico.social.services" })
@Import({ NotifyConfig.class, ServicesConfig.class })
@PropertySource(value = "classpath:/META-INF/debico-social.properties", ignoreResourceNotFound = false)
public class SocialConfig extends SocialConfigurerAdapter {

    private static final String PROP_CONSUMER_KEY = "br.com.debico.social.google.consumerKey";
    private static final String PROP_CONSUMER_SECRET = "br.com.debico.social.google.consumerSecret";
    private static final String PROP_ENC_PASS = "br.com.debico.social.encryptor.pass";
    private static final String PROP_ENC_SALT = "br.com.debico.social.encryptor.salt";

    @Inject
    protected NotifyConfig notifyConfig;

    @Inject
    private DataSource dataSource;

    @Inject
    private Environment globalEnv;

    public SocialConfig() {

    }

    @Override
    public void addConnectionFactories(
	    ConnectionFactoryConfigurer connectionFactoryConfigurer,
	    Environment env) {
	final GoogleConnectionFactory connectionFactory = new GoogleConnectionFactory(
		env.getProperty(PROP_CONSUMER_KEY),
		env.getProperty(PROP_CONSUMER_SECRET));
	
	connectionFactoryConfigurer.addConnectionFactory(connectionFactory);
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(
	    ConnectionFactoryLocator connectionFactoryLocator) {
	JdbcUsersConnectionRepository connectionRepository = new JdbcUsersConnectionRepository(
		dataSource, connectionFactoryLocator, Encryptors.text(
			globalEnv.getProperty(PROP_ENC_PASS),
			globalEnv.getProperty(PROP_ENC_SALT)));
	return connectionRepository;
    }

    @Override
    public UserIdSource getUserIdSource() {
	return new AuthenticationNameUserIdSource();
    }

}
