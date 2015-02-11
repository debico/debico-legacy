package br.com.debico.core.spring.config;

import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServicesConfig.class })
public class TestServicesConfig {

	@Inject
	private ApplicationContext applicationContext;

	@BeforeClass
	public static void before() {

	}

	/**
	 * Verifica se a factory está OK dentro do contexto.
	 */
	@Test
	public void test() {
		assertNotNull(applicationContext);
		assertNotNull(applicationContext.getBean(CacheManager.class));
	}

}
