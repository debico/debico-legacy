package br.com.debico.core.spring.config;

import javax.inject.Inject;

import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;

@EnableCaching
public abstract class AbstractServiceConfig {

	@Inject
    protected Environment environment;
    
    /**
     * Gerenciador do Cache dos serviços da aplicação.
     * 
     * @see <a href="http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/cache.html">Cache Abstraction</a>
     * @return
     */
    @Bean
    public abstract CacheManager cacheManager();
    
    
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
