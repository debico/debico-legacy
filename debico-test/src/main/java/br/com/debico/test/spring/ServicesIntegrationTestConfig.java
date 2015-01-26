package br.com.debico.test.spring;

import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.debico.core.dao.spring.EnableDAO;
import br.com.debico.core.dao.spring.TipoDAOConfig;
import br.com.debico.core.helpers.CacheKeys;
import br.com.debico.core.spring.config.AbstractServiceConfig;
import br.com.debico.core.spring.profiles.Dev;

/**
 * Configuração da camada de serviços para ser utilizada em ambientes de testes de integração.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
@Dev
@Configuration
@EnableDAO(type = TipoDAOConfig.JPA_EMBEDDED_DATABASE)
public class ServicesIntegrationTestConfig extends AbstractServiceConfig {
	
	@Override
	@Bean
	public CacheManager cacheManager() {
		return new GuavaCacheManager(CacheKeys.recuperarTodas());
	}

}
