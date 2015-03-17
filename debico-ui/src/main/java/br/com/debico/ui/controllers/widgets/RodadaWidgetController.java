package br.com.debico.ui.controllers.widgets;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.debico.campeonato.services.RodadaService;
import br.com.debico.model.campeonato.Rodada;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Controller para prover funções públicas aos Widgets de Rodada.
 * 
 * @author ricardozanini
 *
 */
@Controller
@Api(value = "rodadas", description = "Coleção de consultas das Rodadas dos Campeonatos.")
public class RodadaWidgetController {

    @Inject
    private RodadaService rodadaService;

    public RodadaWidgetController() {
    }

    @ApiOperation("Seleciona as Rodadas do Campeonato")
    @RequestMapping(value = "/widgets/rodada/rodadas", method = RequestMethod.GET)
    public @ResponseBody List<Rodada> selecionarRodadas(
            @RequestParam(value = "campeonato", required = true) String permalink) {
        return rodadaService.selecionarRodadas(permalink);
    }

}
