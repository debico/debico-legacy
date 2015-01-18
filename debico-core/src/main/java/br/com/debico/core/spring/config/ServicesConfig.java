package br.com.debico.core.spring.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import br.com.debico.core.dao.spring.EnableDAO;
import br.com.debico.core.dao.spring.TipoDAOConfig;
import br.com.debico.core.helpers.CacheKeys;
import br.com.debico.core.spring.profiles.Release;


/**
 * Classe de configuração das APIs de serviço da aplicação.
 * 
 * @author r_fernandes
 * @since 1.0.0
 */
@Release
@Configuration
@EnableDAO(type = TipoDAOConfig.JPA_STANDALONE)
@PropertySource("classpath:/br/com/debico/core/debico.properties")
public class ServicesConfig extends AbstractServiceConfig {
        
    @Override
    @Bean
    public CacheManager cacheManager() {
        return new GuavaCacheManager(CacheKeys.recuperarTodas());
    }


}