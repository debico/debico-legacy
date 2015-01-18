package br.com.debico.bolao.impl;

import javax.inject.Inject;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.ApostadorPontuacaoService;
import br.com.debico.social.UsuarioService;
import br.com.debico.test.spring.AbstractUnitTest;

@Transactional
@TransactionConfiguration(defaultRollback = true)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestApostadorPontuacaoServiceImpl extends AbstractUnitTest {

	// criar o campeonato fake por aqui.
	
    @Inject
    private ApostadorPontuacaoService apostadorService;
    
    @Inject
    private UsuarioService usuarioService;

//    @Before
//    public void setUp() throws Exception {
//        super.setUp();
//        apostadorService = getTargetObject(apostadorService, ApostadorPontuacaoServiceImpl.class);
//    }
//    
//    @Test(expected = ApostadorJaInscritoException.class)
//    public void testInscreverApostadorCampeonato() throws CadastroApostadorException, ApostadorJaInscritoException {
//    	Apostador apostador = new Apostador("Peter Parker", new Usuario("bruce.banner@shield.com", "IAmHulk456"));
//        usuarioService.cadastrarApostadorUsuario(apostador, "IAmHulk456");
//
//        apostadorService.inscreverApostadorCampeonato(apostador, CAMPEONATO);
//
//        // de novo! \o/
//        apostadorService.inscreverApostadorCampeonato(apostador, CAMPEONATO);
//    }

}
