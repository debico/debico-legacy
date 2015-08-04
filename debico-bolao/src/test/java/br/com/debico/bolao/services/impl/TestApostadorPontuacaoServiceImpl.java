package br.com.debico.bolao.services.impl;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.ApostadorJaInscritoException;
import br.com.debico.bolao.services.ApostadorPontuacaoService;
import br.com.debico.bolao.test.support.AbstractBolaoUnitTest;
import br.com.debico.model.AbstractApostadorPontuacao;
import br.com.debico.model.Apostador;
import br.com.debico.model.ApostadorPontuacao;
import br.com.debico.model.Usuario;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.social.CadastroApostadorException;
import br.com.debico.social.CadastroLigaException;
import br.com.debico.social.model.Liga;
import br.com.debico.social.services.LigaService;
import br.com.debico.social.services.UsuarioService;
import br.com.debico.test.TestConstants;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestApostadorPontuacaoServiceImpl extends AbstractBolaoUnitTest {

    @Inject
    private ApostadorPontuacaoService apostadorService;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private LigaService ligaService;

    @Test(expected = ApostadorJaInscritoException.class)
    public void testInscreverApostadorCampeonato()
            throws CadastroApostadorException, ApostadorJaInscritoException {
        final CampeonatoImpl campeonato = campeonatoService
                .selecionarCampeonato(1);

        Apostador apostador = new Apostador("Peter Parker", new Usuario(
                "bruce.banner@shield.com", "IAmHulk456"));
        usuarioService.cadastrarApostadorUsuario(apostador, "IAmHulk456");

        apostadorService.inscreverApostadorCampeonato(apostador, campeonato);

        // de novo! \o/
        apostadorService.inscreverApostadorCampeonato(apostador, campeonato);
    }

    @Test
    public void testListarPorLiga_Vazia() {
        List<ApostadorPontuacao> ranking = apostadorService
                .listarRankingPorLiga(CAMPEONATO_ID, 1);
        assertNotNull(ranking);
        assertTrue(ranking.isEmpty()); // sem liga, sem pessoas.
    }

    @Test
    public void testListarPorLiga() throws CadastroLigaException {
        final Liga liga = ligaService.cadastrarNovaLiga(
                "Liga da Justiça MODAFOCA", TestConstants.MEU_ID_USUARIO);
        List<ApostadorPontuacao> ranking = apostadorService
                .listarRankingPorLiga(CAMPEONATO_ID, liga.getId());
        assertNotNull(ranking);
        assertFalse(ranking.isEmpty()); // pelo menos eu \o/
        assertTrue(ranking.size() == 1);
    }

    @Test
    public void testListarPorRodadaELiga() throws CadastroLigaException {
        final Liga liga = ligaService.cadastrarNovaLiga(
                "Liga da Justiça MODAFOCA", TestConstants.MEU_ID_USUARIO);
        List<AbstractApostadorPontuacao> ranking = apostadorService
                .listarRankingPorRodadaELiga(39, liga.getId());
        // vira vazio porque temos que processar a sumarizacao.
        // a sumarizacao eh testada em outros cenarios
        assertNotNull(ranking);
    }

}
