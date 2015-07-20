package br.com.debico.resultados.managers;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.debico.bolao.services.ApostadorPontuacaoService;
import br.com.debico.bolao.services.PalpiteService;
import br.com.debico.campeonato.factories.impl.QuadrangularSimplesFactory;
import br.com.debico.campeonato.model.EstruturaCampeonato;
import br.com.debico.campeonato.services.EstruturaCampeonatoService;
import br.com.debico.model.Apostador;
import br.com.debico.model.ApostadorPontuacao;
import br.com.debico.model.Palpite;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.Placar;
import br.com.debico.model.Time;
import br.com.debico.model.Usuario;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.resultados.ManagerBeans;
import br.com.debico.resultados.ProcessorManager;
import br.com.debico.resultados.config.ResultadosConfig;
import br.com.debico.social.services.UsuarioService;
import br.com.debico.test.spring.AbstractUnitTest;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

@ContextConfiguration(classes = { ResultadosConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestBolaoProcessorManager extends AbstractUnitTest {

    @Inject
    private EstruturaCampeonatoService estruturaCampeonatoService;

    @Inject
    private PalpiteService palpiteService;

    @Inject
    private ApostadorPontuacaoService apostadorPontuacaoService;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    @Named(ManagerBeans.BOLAO_MANAGER)
    private ProcessorManager<Campeonato> processorManager;

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
    public void testStart() throws Exception {
	CampeonatoImpl campeonato;
	Apostador adriano, zapa, zanini;
	PartidaRodada partida1, partida2, partida3;

	List<Time> times = new ArrayList<Time>();

	Time brasil = new Time("Brasil");
	Time argentina = new Time("Argentina");
	Time italia = new Time("Italia");

	times.add(argentina);
	times.add(italia);
	times.add(brasil);
	EstruturaCampeonato estruturaCampeonato = new QuadrangularSimplesFactory()
		.criarCampeonato("Campeonato do Teste Unidade", times);

	partida1 = (PartidaRodada) estruturaCampeonato.getPartidas().get(0);
	partida2 = (PartidaRodada) estruturaCampeonato.getPartidas().get(1);
	partida3 = (PartidaRodada) estruturaCampeonato.getPartidas().get(2);

	partida1.getPlacar().setGolsMandante(2);
	partida1.getPlacar().setGolsVisitante(0);
	partida2.getPlacar().setGolsMandante(1);
	partida2.getPlacar().setGolsVisitante(3);
	partida3.getPlacar().setGolsMandante(1);
	partida3.getPlacar().setGolsVisitante(1);

	campeonato = (CampeonatoImpl) estruturaCampeonatoService
		.inserirNovaEstrutura(estruturaCampeonato);

	zanini = new Apostador("Zanini", new Usuario("zanini@bolao.com.br",
		"Bolao1234"));
	zapa = new Apostador("Zaparoli", new Usuario("zapa@bolao.com.br",
		"Bolao1234"));
	adriano = new Apostador("Adriano", new Usuario("adriano@bolao.com.br",
		"Bolao1234"));

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
	processorManager.start(campeonato);

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

	final List<Palpite> palpites = palpiteService
		.recuperarPalpites(campeonato);

	assertThat(palpites, not(empty()));

	final Palpite[] palpitesZanini = Collections2.filter(palpites,
		new Predicate<Palpite>() {
		    public boolean apply(Palpite input) {
			return input.getApostador().getNome().equals("Zanini");
		    }
		}).toArray(new Palpite[0]);

	assertTrue(palpitesZanini[0].getAcertos().isErrado());
	assertTrue(palpitesZanini[1].getAcertos().isGol());
	assertTrue(palpitesZanini[2].getAcertos().isEmpate());

	assertFalse(palpitesZanini[0].getAcertos().isEmpate());
	assertFalse(palpitesZanini[0].getAcertos().isGol());
	assertFalse(palpitesZanini[0].getAcertos().isPlacar());
	assertFalse(palpitesZanini[0].getAcertos().isVencedor());
    }

}
