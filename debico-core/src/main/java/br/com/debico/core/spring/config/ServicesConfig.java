package br.com.debico.core.spring.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Classe base de configuração das APIs de serviço da aplicação.
 * 
 * @author r_fernandes
 * @since 1.0.0
 */
@Configuration
@Import(InfrastructureConfig.class)
public class ServicesConfig {

    @Inject
    protected InfrastructureConfig infrastructureConfig;

}