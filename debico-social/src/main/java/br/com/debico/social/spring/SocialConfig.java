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
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import br.com.debico.core.spring.config.ServicesConfig;
import br.com.debico.notify.spring.NotifyConfig;
import br.com.debico.social.utils.PropsKeys;

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

    @Inject
    protected NotifyConfig notifyConfig;

    @Inject
    private DataSource dataSource;

    @Inject
    private ConnectionSignUp connectionSignUp;

    @Inject
    private Environment globalEnv;

    public SocialConfig() {

    }

    @Override
    public void addConnectionFactories(
	    ConnectionFactoryConfigurer connectionFactoryConfigurer,
	    Environment env) {
	final GoogleConnectionFactory googleConnectionFactory = new GoogleConnectionFactory(
		env.getProperty(PropsKeys.GOOGLE_CONSUMER_KEY),
		env.getProperty(PropsKeys.GOOGLE_CONSUMER_SECRET));
	final FacebookConnectionFactory facebookConnectionFactory = new FacebookConnectionFactory(
		env.getProperty(PropsKeys.FACEBOOK_APP_ID),
		env.getProperty(PropsKeys.FACEBOOK_APP_SECRET));

	connectionFactoryConfigurer
		.addConnectionFactory(googleConnectionFactory);
	connectionFactoryConfigurer
		.addConnectionFactory(facebookConnectionFactory);
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(
	    ConnectionFactoryLocator connectionFactoryLocator) {
	JdbcUsersConnectionRepository connectionRepository = new JdbcUsersConnectionRepository(
		dataSource, connectionFactoryLocator, Encryptors.queryableText(
			globalEnv.getProperty(PropsKeys.ENC_PASS),
			globalEnv.getProperty(PropsKeys.ENC_SALT)));
	connectionRepository.setConnectionSignUp(connectionSignUp);

	return connectionRepository;
    }

    @Override
    public UserIdSource getUserIdSource() {
	return new AuthenticationNameUserIdSource();
    }

}
