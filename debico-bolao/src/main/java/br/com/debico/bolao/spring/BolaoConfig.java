package br.com.debico.bolao.spring;

import javax.inject.Inject;
import javax.inject.Named;

import org.kie.api.KieBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import br.com.debico.campeonato.spring.CampeonatoConfig;
import br.com.debico.core.brms.BRMSExecutor;
import br.com.debico.core.brms.impl.DroolsBRMSExecutor;
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
@Import({ CampeonatoConfig.class, SocialConfig.class, NotifyConfig.class })
@ImportResource("/br/com/debico/bolao/brms/spring/applicatonContext-brms.xml")
@ComponentScan({ "br.com.debico.bolao.services", "br.com.debico.bolao.dao",
		"br.com.debico.bolao.brms" })
public class BolaoConfig {

	@Inject
	@Named("bolaoKBase")
	protected KieBase kieBase;

	public BolaoConfig() {

	}

	@Bean
	@Named("bolaoBrmsExecutor")
	public BRMSExecutor brmsExecutor() {
		DroolsBRMSExecutor brmsExecutor = new DroolsBRMSExecutor();
		brmsExecutor.setKieBase(kieBase);

		return brmsExecutor;
	}

}
