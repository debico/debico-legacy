package br.com.debico.test.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import br.com.debico.core.spring.profiles.Dev;

/**
 * Configuração da camada de serviços para ser utilizada em ambientes de testes
 * de unidade.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
@Dev
@Configuration
@PropertySource({ "/debico-test.properties", "/debico-embedded.properties" })
public class ServicesUnitTestConfig {

}
