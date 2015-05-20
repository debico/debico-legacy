package br.com.debico.social.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.com.debico.core.spring.config.ServicesConfig;
import br.com.debico.notify.spring.NotifyConfig;

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
@Import(NotifyConfig.class)
public class SocialConfig extends ServicesConfig {

    protected NotifyConfig notifyConfig;

    public SocialConfig() {

    }

}
