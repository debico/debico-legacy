package br.com.debico.campeonato.dao;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.test.support.AbstractCampeonatoUnitTest;
import br.com.debico.model.campeonato.Rodada;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRodadaDAO extends AbstractCampeonatoUnitTest {

    @Inject
    private RodadaDAO rodadaDAO;

    @Test
    public void testSelecionarRodadaPorData() {
	// primeira rodada da base de teste: 2014-04-19 e 2014-04-20
	final Rodada rodadaDia19 = rodadaDAO.selecionarRodadaPorData(
		CAMPEONATO, new DateTime("2014-04-19").toDate());
	final Rodada rodadaDia20 = rodadaDAO.selecionarRodadaPorData(
		CAMPEONATO, new DateTime("2014-04-20").toDate());

	// nos dois dias, tem de ser a mesma rodada!
	assertEquals(rodadaDia19, rodadaDia20);

	final Rodada rodadaDia21 = rodadaDAO.selecionarRodadaPorData(
		CAMPEONATO, new DateTime("2014-04-21").toDate());

	// outro dia, outra rodada
	assertNotEquals(rodadaDia19, rodadaDia21);
	// mas tem de ser sequencial
	assertThat(rodadaDia19.getOrdem(),
		is(equalTo(rodadaDia21.getOrdem() - 1)));

    }

}
