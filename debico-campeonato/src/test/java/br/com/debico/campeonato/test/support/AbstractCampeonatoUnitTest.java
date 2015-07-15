package br.com.debico.campeonato.test.support;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.CampeonatoBeans;
import br.com.debico.campeonato.services.CampeonatoService;
import br.com.debico.campeonato.spring.CampeonatoConfig;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.test.TestConstants;
import br.com.debico.test.spring.AbstractUnitTest;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(classes = { CampeonatoConfig.class })
public abstract class AbstractCampeonatoUnitTest extends AbstractUnitTest {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(AbstractCampeonatoUnitTest.class);

	protected static final String EMAIL_SEM_OPCAO_NOTIFICACAO = "sergio.tondin@gps-pamcary.com.br";

	protected CampeonatoPontosCorridos CAMPEONATO;

	@Inject
	@Named(CampeonatoBeans.CAMPEONATO_SERVICE)
	protected CampeonatoService campeonatoService;

	@Before
	public void setUp() throws Exception {
		CAMPEONATO = (CampeonatoPontosCorridos) campeonatoService
				.selecionarCampeonato(TestConstants.CAMPEONATO_ID);
	}

}
