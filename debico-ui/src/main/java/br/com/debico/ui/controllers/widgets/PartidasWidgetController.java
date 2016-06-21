package br.com.debico.ui.controllers.widgets;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.debico.campeonato.services.CampeonatoPontosCorridosService;
import br.com.debico.model.PartidaRodada;

@Controller
public class PartidasWidgetController {

    @Inject
    private CampeonatoPontosCorridosService campeonatoService;

    public PartidasWidgetController() {

    }

    @RequestMapping(value = "/widgets/partidas/{permalink}/rodada/{id}", method = RequestMethod.GET)
    public @ResponseBody List<PartidaRodada> selecionarPartidas(
            @PathVariable(value = "id") int id) {
        return campeonatoService.selecionarPartidasRodada(id);
    }

    @RequestMapping(value = "/widgets/partidas/{permalink}/rodada/seq/{ordinal}", method = RequestMethod.GET)
    public @ResponseBody List<PartidaRodada> selecionarPartidas(
            @PathVariable(value = "permalink") String permalink,
            @PathVariable(value = "ordinal") int ordem) {
        return campeonatoService.selecionarPartidasRodada(ordem, permalink);
    }
}
