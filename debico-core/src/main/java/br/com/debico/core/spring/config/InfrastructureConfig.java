package br.com.debico.core.spring.config;

import javax.inject.Inject;

import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;

import br.com.debico.core.helpers.CacheKeys;
import br.com.debico.core.spring.profiles.Dev;
import br.com.debico.core.spring.profiles.Release;
import br.com.tecnobiz.spring.config.dao.base.ProfileBasedDaoConfig;

/**
 * Configuração dos Beans de Infra-Estrutura da aplicação.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
@EnableCaching
@Import(ProfileBasedDaoConfig.class)
@PropertySource(value = "/META-INF/debico.properties", ignoreResourceNotFound = true)
public final class InfrastructureConfig {

	/**
	 * Bean para o trato do Ambiente.
	 */
	@Inject
	protected Environment environment;

	@Inject
	protected ProfileBasedDaoConfig daoConfig;

	public InfrastructureConfig() {

	}

	/**
	 * Cache Manager ativado quando no profile {@link Release}.
	 * <p />
	 * Gerenciador do Cache dos serviços da aplicação.
	 * 
	 * @see <a
	 *      href="http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/cache.html">Cache
	 *      Abstraction</a>
	 * @return
	 */
	@Release
	@Bean
	public CacheManager cacheManagerGuava() {
		return new GuavaCacheManager(CacheKeys.recuperarTodas());
	}

	/**
	 * Cache Manager ativado quando no profile {@link Dev}.
	 * 
	 * @return
	 */
	@Dev
	@Bean
	public CacheManager cacheManagerNoOp() {
		return new NoOpCacheManager();
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public ReloadableResourceBundleMessageSource resourceBundleMessageSource() {
		ReloadableResourceBundleMessageSource rbms = new ReloadableResourceBundleMessageSource();
		rbms.setBasenames("classpath:/br/com/debico/core/i18n/exceptions");
		rbms.setAlwaysUseMessageFormat(false);

		return rbms;
	}

	@Bean
	public PasswordEncryptor passwordEncryptor() {
		return new StrongPasswordEncryptor();
	}

}
