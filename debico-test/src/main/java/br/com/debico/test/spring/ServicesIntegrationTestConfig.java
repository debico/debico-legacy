package br.com.debico.test.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import br.com.debico.core.dao.spring.EnableDAO;
import br.com.debico.core.dao.spring.TipoDAOConfig;
import br.com.debico.core.spring.profiles.Dev;

/**
 * Configuração da camada de serviços para ser utilizada em ambientes de testes de integração.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
@Dev
@Configuration
@PropertySource({ "/debico-test.properties", "/debico-embedded.properties" })
@EnableDAO(type = TipoDAOConfig.JPA_EMBEDDED_DATABASE)
public class ServicesIntegrationTestConfig {
	
}
