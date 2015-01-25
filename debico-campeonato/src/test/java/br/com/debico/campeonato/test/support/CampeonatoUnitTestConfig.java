package br.com.debico.campeonato.test.support;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

import br.com.debico.campeonato.spring.CampeonatoConfig;
import br.com.debico.test.spring.ServicesUnitTestConfig;

/**
 * Configuração da camada de serviços para ser utilizada em ambientes de testes
 * de unidade.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
@Configuration
@Import({ CampeonatoConfig.class })
public class CampeonatoUnitTestConfig extends ServicesUnitTestConfig {

	public CampeonatoUnitTestConfig() {

	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public CampeonatoTestFactory campeonatoTestFactory() {
		return new CampeonatoTestFactory();
	}

}
