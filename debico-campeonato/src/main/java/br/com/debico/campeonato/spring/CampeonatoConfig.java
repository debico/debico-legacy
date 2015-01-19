package br.com.debico.campeonato.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import br.com.debico.core.spring.config.ServicesConfig;

@Configuration
@ComponentScan({ "br.com.debico.campeonato.dao.impl",
		"br.com.debico.campeonato.impl", "br.com.debico.campeonato.brms.impl" })
@ImportResource({ "classpath:/br/com/debico/campeonato/brms/spring/applicatonContext-brms.xml" })
public class CampeonatoConfig extends ServicesConfig {

	public CampeonatoConfig() {
		
	}

}
