package br.com.debico.campeonato.services.impl;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.PartidaDAO;
import br.com.debico.campeonato.services.CampeonatoPontosCorridosService;
import br.com.debico.campeonato.services.PartidaService;
import br.com.debico.campeonato.test.support.AbstractCampeonatoUnitTest;
import br.com.debico.core.DebicoException;
import br.com.debico.model.Partida;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.Placar;
import br.com.debico.model.Time;
import br.com.debico.model.campeonato.Rodada;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestPartidaServiceImpl extends AbstractCampeonatoUnitTest {

	@Inject
	private PartidaService partidaService;
	
	@Inject
	private PartidaDAO partidaDAO;
	
	@Inject
	private CampeonatoPontosCorridosService campeonatoPontosCorridosService;
		
	@Test
	public void testSalvarPlacar() throws DebicoException {		
	    Rodada rodada = campeonatoPontosCorridosService.selecionarUltimaRodada(CAMPEONATO);
	    
	    final Time timeA = new Time("Solteiros");
	    final Time timeB = new Time("Casados");
	    
		PartidaRodada partida = new PartidaRodada(timeA, timeB);
		partida.setFase(rodada.getRanking().getFase());
		partida.setRodada(rodada);
		
		partidaDAO.inserir(partida);
		
		Partida partidaSalva = partidaService.salvarPlacar(partida.getId(), new Placar(7, 1));
		assertNotNull(partidaSalva);
		assertTrue(partidaSalva instanceof PartidaRodada);
		assertTrue(partidaSalva.getId() > 0);
	}

}
