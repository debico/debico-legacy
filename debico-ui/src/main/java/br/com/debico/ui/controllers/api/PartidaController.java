package br.com.debico.ui.controllers.api;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.debico.campeonato.services.PartidaService;
import br.com.debico.model.Partida;
import br.com.debico.model.Placar;
import br.com.debico.ui.annotations.APIController;

@Controller
@APIController
public class PartidaController {

    @Inject
    private PartidaService partidaService;

    @RequestMapping(value = "/api/partida/{id}/placar/", method = RequestMethod.PUT)
    public @ResponseBody Partida salvarPlacar(@PathVariable final int id, @RequestBody final Placar placar) {
        return partidaService.salvarPlacar(id, placar);
    }

}
