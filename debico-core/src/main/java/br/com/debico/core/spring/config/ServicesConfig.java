package br.com.debico.core.spring.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import br.com.debico.core.helpers.CacheKeys;


/**
 * Classe de configuração das APIs de serviço da aplicação.
 * 
 * @author r_fernandes
 * @since 1.0.0
 */
@Configuration
@PropertySource("classpath:/br/com/debico/core/debico.properties")
public class ServicesConfig extends AbstractServiceConfig {
        
    @Override
    @Bean
    public CacheManager cacheManager() {
        return new GuavaCacheManager(CacheKeys.recuperarTodas());
    }


}