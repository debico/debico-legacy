package br.com.debico.core.spring.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ServicesConfig.class })
public class TestServicesConfig {

	@Inject
	private ApplicationContext applicationContext;

	@BeforeClass
	public static void before() {
		System.setProperty("spring.profiles.active", "release");
	}

	/**
	 * Verifica se a factory está OK dentro do contexto.
	 */
	@Test
	public void test() {
		assertNotNull(applicationContext);
		assertNotNull(applicationContext.getBean(DataSource.class));
	}

}
