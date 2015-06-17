package br.com.debico.ui.controllers.internal;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.debico.campeonato.services.CampeonatoService;
import br.com.debico.social.model.Liga;
import br.com.debico.social.services.LigaApostadorService;
import br.com.debico.social.services.LigaService;
import br.com.debico.ui.controllers.AbstractViewController;

@Controller
public class LigaController extends AbstractViewController {

	@Inject
	private LigaService ligaService;

	@Inject
	private LigaApostadorService ligaApostadorService;

	@Named("campeonatoServiceImpl")
	@Inject
	private CampeonatoService campeonatoService;

	public LigaController() {

	}

	@Override
	protected String getViewName() {
		return "liga";
	}

	// na verdade nao precisamos do permalink, ele é necessário apenas para
	// deixar o link bonitinho. :D
	@RequestMapping(value = "/liga/{id}/{permalink}", method = RequestMethod.GET)
	public ModelAndView liga(@PathVariable("id") long id,
			@PathVariable("permalink") String permalink) {
		resetViewName();
		final Liga liga = ligaService.recuperarLiga(id).get();
		this.addObject("liga", liga);
		this.addObject("campeonatos",
				campeonatoService.selecionarCampeonatosAtivos());
		this.addObject("camaradas",
				ligaApostadorService.consultarApostadores(liga.getId()));
		this.addTitleParam(liga.getNome());

		// adicionar outros objetos pré-renderizados aqui.
		// este link será responsável pelo painel da liga com o mural,
		// resultados, etc.

		// restringir o acesso a usuários que pertencem à liga.

		return this.getModelAndView();
	}

}
