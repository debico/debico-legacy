package br.com.debico.campeonato.spring;

import javax.inject.Inject;
import javax.inject.Named;

import org.kie.api.KieBase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import br.com.debico.core.brms.BRMSExecutor;
import br.com.debico.core.brms.impl.DroolsBRMSExecutor;

/**
 * Configuração da Factory do Spring que deve ser importada por camadas que
 * desejam utilizar os beans do módulo <code>debico-campeonato</code>.
 * <p/>
 * Para que a criação dos Beans seja bem-sucedida, importar em classes que devem
 * extender asconfigurações providas pelo <code>debico-core</code>:
 * {@link br.com.debico.core.spring.config.ServicesConfig}
 * 
 * 
 * @author ricardozanini
 * @since 2.0.0
 * 
 */
@Configuration
@ComponentScan({ "br.com.debico.campeonato.dao",
		"br.com.debico.campeonato.services", "br.com.debico.campeonato.brms" })
@ImportResource({ "classpath:/br/com/debico/campeonato/brms/spring/applicatonContext-brms.xml" })
public class CampeonatoConfig {

	@Inject
	@Named("campeonatoKBase")
	protected KieBase kieBase;

	public CampeonatoConfig() {

	}
	
	@Bean
	@Named("campeonatoBrmsExecutor")
	public BRMSExecutor brmsExecutor() {
		DroolsBRMSExecutor brmsExecutor = new DroolsBRMSExecutor();
		brmsExecutor.setKieBase(kieBase);

		return brmsExecutor;
	}

}
