package br.com.debico.bolao.test.support;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.spring.BolaoUnitTestConfig;
import br.com.debico.campeonato.services.CampeonatoService;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.test.spring.AbstractUnitTest;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(classes = { BolaoUnitTestConfig.class })
public class AbstractBolaoUnitTest extends AbstractUnitTest {

	public AbstractBolaoUnitTest() {

	}

	private static final int CAMPEONATO_ID = 1;

	protected CampeonatoPontosCorridos CAMPEONATO;

	@Inject
	@Named("campeonatoServiceImpl")
	protected CampeonatoService campeonatoService;

	@Before
	public void setUp() throws Exception {
		CAMPEONATO = (CampeonatoPontosCorridos) campeonatoService
				.selecionarCampeonato(CAMPEONATO_ID);
	}
}
