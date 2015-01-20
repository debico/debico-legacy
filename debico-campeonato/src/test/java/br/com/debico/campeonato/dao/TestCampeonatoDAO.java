package br.com.debico.campeonato.dao;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.test.support.AbstractCampeonatoUnitTest;
import br.com.debico.model.PartidaChave;
import br.com.debico.model.Placar;
import br.com.debico.model.Time;
import br.com.debico.model.campeonato.CampeonatoCopa;
import br.com.debico.model.campeonato.ChaveUnica;
import br.com.debico.model.campeonato.FaseEliminatoria;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class TestCampeonatoDAO extends AbstractCampeonatoUnitTest {

    @Inject
    private CampeonatoDAO campeonatoDAO;

    @Inject
    private ChaveDAO chaveDAO;

    @Inject
    private FaseDAO faseDAO;
    
    @Inject
    private TimeDAO timeDAO;

    @Test
    public void testInserirCampeonatoImpl() {        
        Time timeA = Time.TIME_A;
        Time timeB = Time.TIME_B;
        
        timeDAO.inserir(timeA);
        timeDAO.inserir(timeB);
          
        CampeonatoCopa campeonatoCopa = new CampeonatoCopa();
        campeonatoCopa.setNome("Copa do Mundo do Brasil 2014");
        campeonatoCopa.addTime(timeA);
        campeonatoCopa.addTime(timeB);
        
        campeonatoDAO.inserir(campeonatoCopa);

        FaseEliminatoria faseFinal = new FaseEliminatoria();
        faseFinal.setCampeonato(campeonatoCopa);
        
        faseDAO.inserir(faseFinal);

        PartidaChave partida = new PartidaChave(timeA, timeB, new Placar(2, 0));
        
        ChaveUnica chave = new ChaveUnica(faseFinal);
        chave.setFase(faseFinal);
        chave.setVencedor(timeA);
        
        partida.setChave(chave);
        
        chaveDAO.inserir(chave);

        LOGGER.debug("Campeonato Inserido: {}", campeonatoCopa);

    }

}
