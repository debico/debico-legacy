package br.com.debico.bolao.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.test.support.AbstractBolaoUnitTest;
import br.com.debico.campeonato.dao.CampeonatoDAO;
import br.com.debico.campeonato.dao.FaseDAO;
import br.com.debico.campeonato.dao.PartidaDAO;
import br.com.debico.campeonato.dao.TimeDAO;
import br.com.debico.model.Apostador;
import br.com.debico.model.Palpite;
import br.com.debico.model.PartidaChave;
import br.com.debico.model.Placar;
import br.com.debico.model.Time;
import br.com.debico.model.Usuario;
import br.com.debico.model.campeonato.CampeonatoCopa;
import br.com.debico.model.campeonato.FaseEliminatoria;
import br.com.debico.social.dao.ApostadorDAO;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class TestPalpiteDAO extends AbstractBolaoUnitTest {

    @Inject
    private PalpiteDAO palpiteDAO;

    @Inject
    private ApostadorDAO apostadorDAO;

    @Inject
    private TimeDAO timeDAO;

    @Inject
    private PartidaDAO partidaDAO;

    @Inject
    private CampeonatoDAO campeonatoDAO;

    @Inject
    private FaseDAO faseDAO;

    @Test
    public void testInserir() {
        Apostador apostador = new Apostador("Peter Parker", new Usuario(
                "peter@oscorp.com"));
        apostadorDAO.inserir(apostador);

        Time mandante = new Time("Brasil");
        Time visitante = new Time("Croacia");

        timeDAO.inserir(mandante);
        timeDAO.inserir(visitante);

        CampeonatoCopa libertadores = new CampeonatoCopa();
        libertadores.addTime(visitante);
        libertadores.addTime(mandante);

        campeonatoDAO.inserir(libertadores);

        FaseEliminatoria eliminatoria = new FaseEliminatoria();
        eliminatoria.setCampeonato(libertadores);
        faseDAO.inserir(eliminatoria);

        PartidaChave partida = new PartidaChave(mandante, visitante,
                new Placar(2, 0));
        partida.setFase(eliminatoria);

        partidaDAO.inserir(partida);

        Palpite palpite = new Palpite(apostador, partida, new Placar(2, 1));

        palpiteDAO.inserir(palpite);

        List<Palpite> palpites = palpiteDAO.selecionarTodos();

        assertNotNull(palpites);
        assertTrue(palpites.size() > 0);
    }

}