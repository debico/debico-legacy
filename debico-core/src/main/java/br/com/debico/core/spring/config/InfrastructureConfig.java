package br.com.debico.core.spring.config;

import javax.inject.Inject;
import javax.management.MBeanServer;
import javax.sql.DataSource;

import net.sf.ehcache.management.ManagementService;

import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.NoOpCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jmx.support.MBeanServerFactoryBean;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.EhCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.debico.core.helpers.CacheKeys;
import br.com.debico.core.helpers.Roles;
import br.com.debico.core.spring.profiles.Dev;
import br.com.debico.core.spring.profiles.Release;
import br.com.debico.core.spring.security.MockAclService;
import br.com.tecnobiz.spring.config.dao.base.ProfileBasedDaoConfig;

/**
 * Configuração dos Beans de Infra-Estrutura da aplicação.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
@EnableCaching
@Import(ProfileBasedDaoConfig.class)
@ComponentScan("br.com.debico.core.spring.security.impl")
@PropertySource(value = "classpath:META-INF/debico.properties", ignoreResourceNotFound = false)
public final class InfrastructureConfig {

    /**
     * Bean para o trato do Ambiente.
     */
    @Inject
    protected Environment environment;

    @Inject
    protected ProfileBasedDaoConfig daoConfig;

    @Inject
    protected DataSource dataSource;

    public InfrastructureConfig() {

    }

    // ~ Cache
    // ===================================================================================================
    /**
     * Cache Manager ativado quando no profile {@link Release}.
     * <p />
     * Gerenciador do Cache dos serviços da aplicação.
     * 
     * @see <a
     *      href="http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/cache.html">Cache
     *      Abstraction</a>
     * @see <a
     *      href="http://www.mkyong.com/spring/spring-caching-and-ehcache-example/">Spring
     *      Caching and Ehcache example</a>
     * @return
     */
    @Release
    @Bean
    public CacheManager cacheManagerImpl(
	    net.sf.ehcache.CacheManager cacheManager) {
	return new EhCacheCacheManager(cacheManager);
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

    /**
     * EhCache CacheManager Factory
     * 
     * @return
     */
    @Bean
    @Release
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
	final EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
	cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
	cmfb.setShared(true);
	return cmfb;
    }
    
    // serviço de gerenciamento de cache
    @Bean(initMethod="init", destroyMethod="dispose")
    @Release
    public ManagementService ehCacheManagementService(net.sf.ehcache.CacheManager cacheManager, MBeanServer beanServer) {
	return new ManagementService(cacheManager, beanServer, true, true, true, true);
    }
    
    @Bean
    @Release
    public MBeanServerFactoryBean mbeanServerFactoryBean() {
	final MBeanServerFactoryBean factoryBean = new MBeanServerFactoryBean();
        factoryBean.setLocateExistingServerIfPossible(true);
        
	return factoryBean;
    }

    // ~ Misc.
    // ===================================================================================================
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

    // ~ ACL
    // ===================================================================================================
    @Release
    @Bean
    public EhCacheFactoryBean cacheFactoryBean(
	    net.sf.ehcache.CacheManager cacheManager) {
	EhCacheFactoryBean cacheFactoryBean = new EhCacheFactoryBean();
	cacheFactoryBean.setCacheManager(cacheManager);
	cacheFactoryBean.setCacheName(CacheKeys.ACL);

	return cacheFactoryBean;
    }

    @Release
    @Bean
    public AclCache aclCache(EhCacheFactoryBean cacheFactoryBean) {
	// @formatter:off
		return new EhCacheBasedAclCache(
					cacheFactoryBean.getObject(),
					new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger()), 
					new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority(Roles.ROLE_ADMIN)));
		// @formatter:on
    }

    @Release
    @Bean
    public LookupStrategy lookupStrategy(AclCache aclCache) {
	// @formatter:off
		return new BasicLookupStrategy(
					dataSource, 
					aclCache, 
					new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority(Roles.ROLE_ADMIN)), 
					new ConsoleAuditLogger());
		// @formatter:on
    }

    @Release
    @Bean
    public MutableAclService aclService(AclCache aclCache,
	    LookupStrategy lookupStrategy) {
	final JdbcMutableAclService aclService = new JdbcMutableAclService(
		dataSource, lookupStrategy, aclCache);
	aclService.setClassIdentityQuery("select LAST_INSERT_ID()");
	aclService.setSidIdentityQuery("select LAST_INSERT_ID()");

	return aclService;
    }

    @Dev
    @Bean
    public MutableAclService aclServiceNop() {
	return new MockAclService();
    }

}
