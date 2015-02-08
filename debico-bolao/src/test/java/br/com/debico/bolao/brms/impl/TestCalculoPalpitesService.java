package br.com.debico.bolao.brms.impl;

import java.util.ArrayList;
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
import br.com.debico.campeonato.model.EstruturaCampeonato;
import br.com.debico.campeonato.services.EstruturaCampeonatoService;
import br.com.debico.core.DebicoException;
import br.com.debico.model.Apostador;
import br.com.debico.model.ApostadorPontuacao;
import br.com.debico.model.Palpite;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.Placar;
import br.com.debico.model.Time;
import br.com.debico.model.Usuario;
import br.com.debico.model.campeonato.CampeonatoImpl;
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

	@Inject
	private EstruturaCampeonatoService estruturaCampeonatoService;

	private CampeonatoImpl campeonato;
	private Apostador adriano, zapa, zanini;
	private PartidaRodada partida1, partida2, partida3;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		zanini = new Apostador("Zanini", new Usuario("zanini@bolao.com.br",
				"Bolao1234"));
		zapa = new Apostador("Zaparoli", new Usuario("zapa@bolao.com.br",
				"Bolao1234"));
		adriano = new Apostador("Adriano", new Usuario("adriano@bolao.com.br",
				"Bolao1234"));

		List<Time> times = new ArrayList<Time>();

		Time brasil = new Time("Brasil");
		Time argentina = new Time("Argentina");
		Time italia = new Time("Italia");

		times.add(argentina);
		times.add(italia);
		times.add(brasil);
		EstruturaCampeonato estruturaCampeonato = this.novaEstruturaCampeonato(times);
		
		this.partida1 = (PartidaRodada) estruturaCampeonato.getPartidas().get(0);
		this.partida2 = (PartidaRodada) estruturaCampeonato.getPartidas().get(1);
		this.partida3 = (PartidaRodada) estruturaCampeonato.getPartidas().get(2);
		
		this.partida1.getPlacar().setGolsMandante(2);
		this.partida1.getPlacar().setGolsVisitante(0);
		this.partida2.getPlacar().setGolsMandante(1);
		this.partida2.getPlacar().setGolsVisitante(3);
		this.partida3.getPlacar().setGolsMandante(1);
		this.partida3.getPlacar().setGolsVisitante(1);
		
		this.campeonato = (CampeonatoImpl) estruturaCampeonatoService
				.inserirNovaEstrutura(estruturaCampeonato);
	}

	/*
	 * Partidas:
	 * 
	 * partida1 = new PartidaRodada(BRASIL, ARGENTINA, new Placar(2, 0));
	 * partida2 = new PartidaRodada(EUA, HOLANDA, new Placar(1, 3)); partida3 =
	 * new PartidaRodada(NIGERIA, ITALIA, new Placar(1, 1));
	 * 
	 * Resultados esperados a partir dos palpites abaixo:
	 * 
	 * Apostador{Zaparoli} 6(P) 1(A) 0(V) 0(E) 1(G) 1(R) Apostador{Adriano} 6(P)
	 * 0(A) 2(V) 0(E) 2(G) 1(R) Apostador{Zanini} 3(P) 0(A) 0(V) 1(E) 1(G) 2(R)
	 */
	@Test
	public void testComputarPalpites() throws DebicoException {
		
		// palpites
		usuarioService.cadastrarApostadorUsuario(adriano, "Bolao1234");
		usuarioService.cadastrarApostadorUsuario(zanini, "Bolao1234");
		usuarioService.cadastrarApostadorUsuario(zapa, "Bolao1234");

		// inscricao
		apostadorPontuacaoService.inscreverApostadorCampeonato(adriano,
				campeonato);
		apostadorPontuacaoService.inscreverApostadorCampeonato(zanini,
				campeonato);
		apostadorPontuacaoService
				.inscreverApostadorCampeonato(zapa, campeonato);

		palpiteService.palpitar(
				new Palpite(zanini, partida1, new Placar(0, 3)), campeonato);
		palpiteService.palpitar(
				new Palpite(zanini, partida2, new Placar(1, 1)), campeonato);
		palpiteService.palpitar(
				new Palpite(zanini, partida3, new Placar(3, 3)), campeonato);

		palpiteService.palpitar(new Palpite(zapa, partida1, new Placar(2, 0)),
				campeonato);
		palpiteService.palpitar(new Palpite(zapa, partida2, new Placar(2, 1)),
				campeonato);
		palpiteService.palpitar(new Palpite(zapa, partida3, new Placar(2, 1)),
				campeonato);

		palpiteService.palpitar(
				new Palpite(adriano, partida1, new Placar(1, 0)), campeonato);
		palpiteService.palpitar(
				new Palpite(adriano, partida2, new Placar(0, 3)), campeonato);
		palpiteService.palpitar(
				new Palpite(adriano, partida3, new Placar(0, 2)), campeonato);

		// execução
		processadorRegrasBolao.processarResultados(campeonato);

		List<ApostadorPontuacao> apostadores = apostadorPontuacaoService
				.listarRanking(campeonato);

		// asserts
		assertNotNull(apostadores);

		Collections.sort(apostadores);
		LOGGER.info("Relacao dos apostadores: {}", apostadores);

		assertTrue(apostadores.size() == 3);

		assertEquals(zapa, apostadores.get(0).getApostador());
		assertEquals(adriano, apostadores.get(1).getApostador());
		assertEquals(zanini, apostadores.get(2).getApostador());

		assertEquals((Integer) 6, apostadores.get(0).getPontosTotal());
		assertEquals((Integer) 6, apostadores.get(1).getPontosTotal());
		assertEquals((Integer) 3, apostadores.get(2).getPontosTotal());
	}

}
