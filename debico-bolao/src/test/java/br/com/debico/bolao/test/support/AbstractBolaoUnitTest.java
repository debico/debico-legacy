package br.com.debico.bolao.test.support;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.spring.BolaoConfig;
import br.com.debico.campeonato.factories.EstruturaCampeonatoProvider;
import br.com.debico.campeonato.model.EstruturaCampeonato;
import br.com.debico.campeonato.services.CampeonatoService;
import br.com.debico.model.Time;
import br.com.debico.test.spring.AbstractUnitTest;
import br.com.debico.test.spring.ServicesUnitTestConfig;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(classes = { BolaoConfig.class,
		ServicesUnitTestConfig.class })
public class AbstractBolaoUnitTest extends AbstractUnitTest {

	private final EstruturaCampeonatoProvider provider;
	
	public AbstractBolaoUnitTest() {
		provider = new EstruturaCampeonatoProvider();
	}
	
	protected EstruturaCampeonato novaEstruturaCampeonato(List<Time> times) {
		return provider.getDefaultFactory().criarCampeonato("Campeonato do Teste Unidade", times);
	}

	@Inject
	@Named("campeonatoServiceImpl")
	protected CampeonatoService campeonatoService;

	@Before
	public void setUp() throws Exception {

	}
}
