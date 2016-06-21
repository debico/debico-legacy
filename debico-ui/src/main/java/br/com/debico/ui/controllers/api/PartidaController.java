package br.com.debico.ui.controllers.api;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.debico.campeonato.services.PartidaService;
import br.com.debico.model.Partida;
import br.com.debico.model.Placar;

@RestController
public class PartidaController {

    @Inject
    private PartidaService partidaService;

    @RequestMapping(value = "/api/partida/{id}/placar/", method = RequestMethod.PUT)
    public @ResponseBody Partida salvarPlacar(@PathVariable final int id,
            @RequestBody final Placar placar) {
        return partidaService.salvarPlacar(id, placar);
    }

    @RequestMapping(value = "/api/partida/{id}", method = RequestMethod.PATCH)
    public Partida atualizarDataHorario(@PathVariable final int id, @RequestBody final Date dataHoraPartida) {
        return partidaService.atualizarDataHorario(id, dataHoraPartida);
    }
    
}
