package br.com.debico.ui.controllers.internal;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.debico.campeonato.services.CampeonatoPontosCorridosService;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.ui.controllers.AbstractViewController;

@Controller
public class PalpitesPontosCorridosController extends AbstractViewController {
    
    @Inject
    private CampeonatoPontosCorridosService campeonatoService;


	@Override
	protected String getViewName() {
		return "palpites_pc";
	}
        
    @RequestMapping(method = RequestMethod.GET, value="/campeonatos/pc/{permalink}/palpites")
    public ModelAndView palpites(@PathVariable("permalink") final String permalink) {
        final CampeonatoPontosCorridos campeonato = (CampeonatoPontosCorridos) campeonatoService.selecionarCampeonato(permalink);
        
        getModelAndView().addObject("campeonato", campeonato);
        getModelAndView().addObject("rodada", campeonatoService.selecionarRodadaAtual(campeonato));
        
        return getModelAndView();
    }
    
   
}
