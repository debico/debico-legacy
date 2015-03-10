package br.com.debico.ui.controllers.widgets;

import java.security.Principal;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.debico.bolao.model.PontuacaoRodadaApostadorSerie;
import br.com.debico.bolao.services.DesempenhoApostadorService;
import br.com.debico.campeonato.services.CampeonatoService;
import br.com.debico.ui.annotations.WidgetController;

/**
 * Controller para recuperar os índices de desempenho do apostador. 
 * <p/>
 * Cada widget pode fazer referencia a esse controller de forma que for conveniente.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
@Controller
@WidgetController
public class DesempenhoApostadorWidgetsController {
    
    @Inject
    private DesempenhoApostadorService desempenhoApostadorService;
    
    @Inject
    @Named("campeonatoServiceImpl")
    private CampeonatoService campeonatoService;

    public DesempenhoApostadorWidgetsController() {

    }
    
    @RequestMapping(value="/widgets/desempenho/apostador_rodada",  method = RequestMethod.GET)
    public @ResponseBody PontuacaoRodadaApostadorSerie procurarPorNome(
            @RequestParam(value="campeonato", required = true) String campeonato, 
            final Principal principal) {
        return desempenhoApostadorService.recuperarDesempenhoPontuacaoPorRodada(
                campeonatoService.selecionarCampeonato(campeonato),
                principal.getName());
    }

}
