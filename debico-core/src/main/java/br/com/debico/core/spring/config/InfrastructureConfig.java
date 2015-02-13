package br.com.debico.core.spring.config;

import javax.inject.Inject;

import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;

import br.com.debico.core.dao.spring.DAOConfig;
import br.com.debico.core.dao.spring.EnableDAO;
import br.com.debico.core.helpers.CacheKeys;
import br.com.debico.core.spring.profiles.Dev;
import br.com.debico.core.spring.profiles.Release;

/**
 * Configuração dos Beans de Infra-Estrutura da aplicação.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
@EnableCaching
public final class InfrastructureConfig {

    /**
     * Bean para o trato do Ambiente.
     */
    @Inject
    protected Environment environment;

    /**
     * Configuração da DAO.
     * 
     * @see EnableDAO
     */
    @Inject
    protected DAOConfig daoConfig;

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
