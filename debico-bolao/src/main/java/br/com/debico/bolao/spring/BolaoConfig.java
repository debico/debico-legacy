package br.com.debico.bolao.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.com.debico.campeonato.spring.CampeonatoConfig;
import br.com.debico.core.spring.config.ServicesConfig;
import br.com.debico.notify.spring.NotifyConfig;
import br.com.debico.social.spring.SocialConfig;

/**
 * Deve ser utilizado por clientes que desejam utilizar os beans disponíveis no
 * <code>debico-bolao</code>.
 * <p/>
 * Tais camadas deverão importar {@link ServicesConfig}.
 * 
 * @author ricardozanini
 *
 */
@Configuration
@Import({ CampeonatoConfig.class, SocialConfig.class, NotifyConfig.class } )
@ComponentScan({ "br.com.debico.bolao.services", "br.com.debico.bolao.dao",
		"br.com.debico.bolao.brms" })
public class BolaoConfig {

	public BolaoConfig() {

	}

}
