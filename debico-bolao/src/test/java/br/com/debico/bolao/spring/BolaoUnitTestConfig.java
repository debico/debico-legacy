package br.com.debico.bolao.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.com.debico.test.spring.ServicesUnitTestConfig;

@Configuration
@Import(BolaoConfig.class)
public class BolaoUnitTestConfig extends ServicesUnitTestConfig {

	public BolaoUnitTestConfig() {

	}

}
