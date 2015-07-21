package br.com.debico.bolao.test.support;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;

import br.com.debico.bolao.spring.BolaoConfig;
import br.com.debico.campeonato.factories.impl.QuadrangularSimplesFactory;
import br.com.debico.campeonato.model.EstruturaCampeonato;
import br.com.debico.campeonato.services.CampeonatoService;
import br.com.debico.model.Time;
import br.com.debico.test.spring.AbstractUnitTest;

@ContextConfiguration(classes = { BolaoConfig.class })
public class AbstractBolaoUnitTest extends AbstractUnitTest {

    protected static final String EMAIL_PRIMEIRO_RANKING = "abacafrehley@gmail.com";
    protected static final String EMAIL_ULTIMO_RANKING = "fhbernardo@yahoo.com.br";
    protected static final String EMAIL_SEM_OPCAO_NOTIFICACAO = "sergio.tondin@gps-pamcary.com.br";
    protected static final int CAMPEONATO_ID = 1;

    protected EstruturaCampeonato novaEstruturaCampeonato(List<Time> times) {
        return new QuadrangularSimplesFactory().criarCampeonato(
                "Campeonato do Teste Unidade", times);
    }

    @Inject
    @Named("campeonatoServiceImpl")
    protected CampeonatoService campeonatoService;

    @Before
    public void setUp() throws Exception {

    }
}
