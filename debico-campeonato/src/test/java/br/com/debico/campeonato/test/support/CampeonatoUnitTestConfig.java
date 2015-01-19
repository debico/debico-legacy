package br.com.debico.campeonato.test.support;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;

import br.com.debico.test.spring.ServicesUnitTestConfig;

/**
 * Configuração da camada de serviços para ser utilizada em ambientes de testes
 * de unidade.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
@Configuration
@ComponentScan({ "br.com.debico.campeonato.dao.impl",
		"br.com.debico.campeonato.impl", "br.com.debico.campeonato.brms.impl" })
@ImportResource({ "classpath:/br/com/debico/campeonato/brms/spring/applicatonContext-brms.xml" })
public class CampeonatoUnitTestConfig extends ServicesUnitTestConfig {

	public CampeonatoUnitTestConfig() {

	}

	/**
	 * Não implementar o cache em testes de unidade para não comprometer a
	 * adição e remoção de elementos, a não ser que implementado corretamente o
	 * 
	 * @CacheEvict.
	 */
	@Override
	public CacheManager cacheManager() {
		return new NoOpCacheManager();
	}

	@Bean
	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
	public CampeonatoTestFactory campeonatoTestFactory() {
		return new CampeonatoTestFactory();
	}

}
