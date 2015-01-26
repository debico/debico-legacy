package br.com.debico.bolao.brms.impl;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.brms.ProcessadorRegrasBolao;
import br.com.debico.bolao.services.ApostadorPontuacaoService;
import br.com.debico.bolao.services.PalpiteService;
import br.com.debico.bolao.test.support.AbstractBolaoUnitTest;
import br.com.debico.core.DebicoException;
import br.com.debico.model.Apostador;
import br.com.debico.model.ApostadorPontuacao;
import br.com.debico.model.Palpite;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.Placar;
import br.com.debico.model.Usuario;
import br.com.debico.social.services.UsuarioService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class TestCalculoPalpitesService extends AbstractBolaoUnitTest {

	@Inject
	@Named("processadorRegrasBolaoImpl")
	private ProcessadorRegrasBolao processadorRegrasBolao;

	@Inject
	private PalpiteService palpiteService;

	@Inject
	private ApostadorPontuacaoService apostadorPontuacaoService;
	
	@Inject
	private UsuarioService usuarioService;

	public Apostador ADRIANO, ZAPA, ZANINI;
    public PartidaRodada PARTIDA1, PARTIDA2, PARTIDA3;
	
    @Before
	public void setUp() throws Exception {
		super.setUp();
		
		ZANINI = new Apostador("Zanini", new Usuario("zanini@bolao.com.br", "Bolao1234"));
		ZAPA = new Apostador("Zaparoli", new Usuario("zapa@bolao.com.br", "Bolao1234"));
		ADRIANO = new Apostador("Adriano", new Usuario("adriano@bolao.com.br", "Bolao1234"));
	}


    /*
     * Partidas:
     * 
     * partida1 = new PartidaRodada(BRASIL, ARGENTINA, new Placar(2, 0));
     * partida2 = new PartidaRodada(EUA, HOLANDA, new Placar(1, 3)); 
     * partida3 = new PartidaRodada(NIGERIA, ITALIA, new Placar(1, 1));
     * 
     * Resultados esperados a partir dos palpites abaixo:
     * 
     * Apostador{Zaparoli} 6(P) 1(A) 0(V) 0(E) 1(G) 1(R) 
     * Apostador{Adriano}  6(P) 0(A) 2(V) 0(E) 2(G) 1(R) 
     * Apostador{Zanini}   3(P) 0(A) 0(V) 1(E) 1(G) 2(R)
     */
	@Test
	public void testComputarPalpites() throws DebicoException {
		// palpites
		usuarioService.cadastrarApostadorUsuario(ADRIANO, "Bolao1234");
		usuarioService.cadastrarApostadorUsuario(ZANINI, "Bolao1234");
		usuarioService.cadastrarApostadorUsuario(ZAPA, "Bolao1234");
		
		// inscricao
		apostadorPontuacaoService.inscreverApostadorCampeonato(ADRIANO, CAMPEONATO);
		apostadorPontuacaoService.inscreverApostadorCampeonato(ZANINI, CAMPEONATO);
		apostadorPontuacaoService.inscreverApostadorCampeonato(ZAPA, CAMPEONATO);

		palpiteService.palpitar(
				new Palpite(ZANINI, PARTIDA1, new Placar(0, 3)), CAMPEONATO);
		palpiteService.palpitar(
				new Palpite(ZANINI, PARTIDA2, new Placar(1, 1)), CAMPEONATO);
		palpiteService.palpitar(
				new Palpite(ZANINI, PARTIDA3, new Placar(3, 3)), CAMPEONATO);

		palpiteService.palpitar(new Palpite(ZAPA, PARTIDA1, new Placar(2, 0)),
				CAMPEONATO);
		palpiteService.palpitar(new Palpite(ZAPA, PARTIDA2, new Placar(2, 1)),
				CAMPEONATO);
		palpiteService.palpitar(new Palpite(ZAPA, PARTIDA3, new Placar(2, 1)),
				CAMPEONATO);

		palpiteService.palpitar(
				new Palpite(ADRIANO, PARTIDA1, new Placar(1, 0)), CAMPEONATO);
		palpiteService.palpitar(
				new Palpite(ADRIANO, PARTIDA2, new Placar(0, 3)), CAMPEONATO);
		palpiteService.palpitar(
				new Palpite(ADRIANO, PARTIDA3, new Placar(0, 2)), CAMPEONATO);

		// execução
		processadorRegrasBolao.processarResultados(CAMPEONATO);

		List<ApostadorPontuacao> apostadores = apostadorPontuacaoService.listarRanking(CAMPEONATO);

		// asserts
		assertNotNull(apostadores);

		Collections.sort(apostadores);
		LOGGER.info("Relacao dos apostadores: {}", apostadores);

		assertTrue(apostadores.size() == 3);

		assertEquals(ZAPA, apostadores.get(0).getApostador());
		assertEquals(ADRIANO, apostadores.get(1).getApostador());
		assertEquals(ZANINI, apostadores.get(2).getApostador());

		assertEquals((Integer) 6, apostadores.get(0).getPontosTotal());
		assertEquals((Integer) 6, apostadores.get(1).getPontosTotal());
		assertEquals((Integer) 3, apostadores.get(2).getPontosTotal());
	}

}
