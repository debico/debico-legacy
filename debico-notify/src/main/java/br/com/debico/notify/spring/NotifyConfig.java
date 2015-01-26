package br.com.debico.notify.spring;

import java.util.Properties;

import javax.inject.Inject;
import javax.mail.Session;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

import br.com.debico.core.spring.config.ServicesConfig;
import br.com.debico.core.spring.profiles.Dev;
import br.com.debico.core.spring.profiles.Release;

/**
 * Configuração dos beans do módulo <code>debico-notify</code>. Deve ser
 * importado por clientes que desejam utilizar instâncias desses beans.
 * 
 * @author ricardozanini
 *
 */
@Configuration
@ComponentScan({ "br.com.debico.notify.services", "br.com.debico.notify.dao" })
public class NotifyConfig {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServicesConfig.class);

	@Inject
	private Environment environment;

	public NotifyConfig() {

	}
	
	/**
     * @see <a href="http://ollivander.franzoni.eu/2011/08/30/mock-javamail-primer/">Mock Javamail Primer</a>
     * @return
     */
	@Bean
	@Dev
	public JavaMailSender mailSender() {
		final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		return mailSender;
	}
	
	@Bean
	@Release
	public JavaMailSender mailSender(final Session session) {
		final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setSession(session);

		return mailSender;
	}

	@Bean
	@Release
	public Session session() {
		JndiTemplate jndiTemplate = new JndiTemplate();
		Session session = null;

		try {
			session = jndiTemplate.lookup(
					environment.getProperty("br.com.debico.jndi.mail.session"),
					Session.class);
		} catch (NamingException e) {
			LOGGER.error("[session] Erro ao obter a sessao de email.", e);
		}

		return session;
	}
	
	@Bean
	public VelocityEngineFactoryBean velocityEngine() {
	    final VelocityEngineFactoryBean bean = new VelocityEngineFactoryBean();
	    final Properties props = new Properties();
	    
	    props.put("resource.loader", "class");
	    props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    props.put("eventhandler.referenceinsertion.class", "org.apache.velocity.app.event.implement.EscapeHtmlReference");
	    
	    bean.setVelocityProperties(props);
	    
	    return bean;
	}

}
