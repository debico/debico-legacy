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
import br.com.debico.ui.annotations.WidgetController;

/**
 * Controller para prover funções públicas aos Widgets de Rodada.
 * 
 * @author ricardozanini
 *
 */
@Controller
@WidgetController
public class RodadaWidgetController {
	
	
	@Inject
	private RodadaService rodadaService;

	public RodadaWidgetController() {
	}

	@RequestMapping(value = "/widgets/rodada/rodadas", method = RequestMethod.GET)
	public @ResponseBody List<Rodada> selecionarRodadas(@RequestParam(value="campeonato", required=true) String permalink) {
		return rodadaService.selecionarRodadas(permalink);
	}
	

}
