package br.com.debico.ui.controllers.widgets;

import java.security.Principal;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.debico.bolao.model.PartidaRodadaPalpiteView;
import br.com.debico.bolao.services.PalpiteService;
import br.com.debico.bolao.services.PartidaPalpiteService;
import br.com.debico.campeonato.services.CampeonatoService;
import br.com.debico.core.DebicoException;
import br.com.debico.model.PalpiteLite;
import br.com.debico.ui.annotations.WidgetController;

@Controller
@WidgetController
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
    
    @RequestMapping(method = RequestMethod.GET, value="/widgets/palpite/{permalink}/rodada/{id}")
    public @ResponseBody List<PartidaRodadaPalpiteView> palpitesRodada(
            @PathVariable("permalink") final String permalink, 
            @PathVariable("id") final int idRodada,
            final Principal principal) {
        return partidaPalpiteService.recuperarPalpitesRodada(idRodada, principal.getName());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/widgets/palpite/{permalink}/rodada/seq/{ordinal}")
    public @ResponseBody List<PartidaRodadaPalpiteView> palpitesRodadaOrdinal(
            @PathVariable(value="permalink") final String permalink, 
            @PathVariable(value="ordinal") final int ordem,
            final Principal principal) {
        
        return partidaPalpiteService.recuperarPalpitesRodadaPorOrdinal(permalink, ordem, principal.getName());
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/widgets/palpite/{permalink}")
    public @ResponseBody PalpiteLite palpitar(
            @PathVariable(value="permalink") final String permalink, 
            @RequestBody final PalpiteLite palpiteIO,
            final Principal principal) throws DebicoException {
        
        palpiteIO.setApostadorEmail(principal.getName());
        return palpiteService.palpitar(palpiteIO, campeonatoService.selecionarCampeonato(permalink));
    }

}
