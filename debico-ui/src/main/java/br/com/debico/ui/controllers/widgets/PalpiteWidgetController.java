package br.com.debico.ui.controllers.widgets;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.debico.bolao.model.view.PartidaRodadaPalpiteView;
import br.com.debico.bolao.services.PalpiteService;
import br.com.debico.bolao.services.PartidaPalpiteService;
import br.com.debico.campeonato.services.CampeonatoService;
import br.com.debico.core.DebicoException;
import br.com.debico.model.to.PalpiteTO;
import br.com.debico.ui.thymeleaf.UsuarioAuthUtils;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@Api(value = "palpite", description = "Realiza as funcionalidades relacionadas com os Palpites do Bolão.")
public class PalpiteWidgetController {

    @Inject
    @Named("campeonatoServiceImpl")
    private CampeonatoService campeonatoService;

    @Inject
    private PalpiteService palpiteService;

    @Inject
    private PartidaPalpiteService partidaPalpiteService;

    public PalpiteWidgetController() {

    }

    @ApiOperation("Consulta os Palpites por Rodada")
    @RequestMapping(method = RequestMethod.GET, value = "/widgets/palpite/{permalink}/rodada/{id}")
    public @ResponseBody List<PartidaRodadaPalpiteView> palpitesRodada(
            @PathVariable("permalink") final String permalink,
            @PathVariable("id") final int idRodada) {
        return partidaPalpiteService.recuperarPalpitesRodada(idRodada,
                UsuarioAuthUtils.userId());
    }

    @ApiOperation("Consulta os Palpites por sequencial da Rodada")
    @RequestMapping(method = RequestMethod.GET, value = "/widgets/palpite/{permalink}/rodada/seq/{ordinal}")
    public @ResponseBody List<PartidaRodadaPalpiteView> palpitesRodadaOrdinal(
            @PathVariable(value = "permalink") final String permalink,
            @PathVariable(value = "ordinal") final int ordem) {

        return partidaPalpiteService.recuperarPalpitesRodadaPorOrdinal(
                permalink, ordem, UsuarioAuthUtils.userId());
    }

    @ApiOperation("Realiza um palpite")
    @RequestMapping(method = RequestMethod.POST, value = "/widgets/palpite/{permalink}")
    public @ResponseBody PalpiteTO palpitar(
            @PathVariable(value = "permalink") final String permalink,
            @RequestBody final PalpiteTO palpiteIO) throws DebicoException {

        palpiteIO.setIdUsuario(UsuarioAuthUtils.userId());
        return palpiteService.palpitar(palpiteIO,
                campeonatoService.selecionarCampeonato(permalink));
    }

}
