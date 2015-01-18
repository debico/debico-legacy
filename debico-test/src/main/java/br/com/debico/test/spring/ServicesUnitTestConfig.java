package br.com.debico.test.spring;

import org.springframework.cache.CacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import br.com.debico.core.dao.spring.EnableDAO;
import br.com.debico.core.dao.spring.TipoDAOConfig;

/**
 * Configuração da camada de serviços para ser utilizada em ambientes de testes
 * de unidade.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
@Configuration
@EnableDAO(type = TipoDAOConfig.JPA_EMBEDDED_DATABASE)
@PropertySource({ "/debico-test.properties", "/debico-embedded.properties" })
public class ServicesUnitTestConfig extends ServicesIntegrationTestConfig {

	public ServicesUnitTestConfig() {

	}

	/**
	 * Não implementar o cache em testes de unidade para não comprometer a
	 * adição e remoção de elementos, a não ser que implementado corretamente o
	 * @CacheEvict.
	 */
	@Override
	public CacheManager cacheManager() {
		return new NoOpCacheManager();
	}

}
