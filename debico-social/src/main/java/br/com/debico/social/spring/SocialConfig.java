package br.com.debico.social.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.debico.core.spring.config.ServicesConfig;

/**
 * Deve ser importada por clientes que precisem dos Beans de
 * <code>debico-social</code>.
 * <p />
 * Clientes devem extender {@link ServicesConfig}.
 * 
 * @author ricardozanini
 *
 */
@Configuration
@ComponentScan({ "br.com.debico.social.dao", "br.com.debico.social.services" })
public class SocialConfig extends ServicesConfig {

	public SocialConfig() {

	}

}
