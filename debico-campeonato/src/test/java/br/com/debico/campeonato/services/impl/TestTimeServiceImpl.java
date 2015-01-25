package br.com.debico.campeonato.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.services.TimeService;
import br.com.debico.campeonato.test.support.AbstractCampeonatoUnitTest;
import br.com.debico.model.Time;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class TestTimeServiceImpl extends AbstractCampeonatoUnitTest {

    @Inject
    private TimeService timeService;

    @Test
    public void testProcurarTimePorNome() {
        // cuidado que o H2 não distingue acentos e nem letras maiúsculas.
        List<Time> times = timeService.procurarTimePorNome("Cor");

        assertNotNull(times);
        assertFalse(times.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProcurarTimePorNome_poucosChars() {
        timeService.procurarTimePorNome("s");
    }

}
