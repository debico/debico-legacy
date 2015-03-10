package br.com.debico.ui.controllers.internal;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.debico.bolao.services.ApostadorPontuacaoService;
import br.com.debico.campeonato.services.CampeonatoService;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.ui.controllers.AbstractViewController;

/**
 * Carrega o ranking dos Apostadores, por campeonato.
 * 
 * @author ricardozanini
 *
 */
@Controller
public class ApostadorPontuacaoController extends AbstractViewController {
	
	@Inject
	@Named("campeonatoServiceImpl")
	private CampeonatoService campeonatoService;
	
	@Inject
	private ApostadorPontuacaoService apostadorPontuacaoService;
	
	@Override
	protected String getViewName() {
		return "ranking";
	}
	
	@RequestMapping(value = "/ranking/{permalink}", method = RequestMethod.GET)
	public ModelAndView ranking(@PathVariable(value="permalink") String permalink) {
		final Campeonato campeonato = campeonatoService.selecionarCampeonato(permalink); 

		getModelAndView().addObject("campeonato", campeonato);
		getModelAndView().addObject("ranking", apostadorPontuacaoService.listarRanking(campeonato));

		return getModelAndView();
	}

}
