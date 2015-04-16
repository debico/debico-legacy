package br.com.debico.ui.controllers.internal;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.debico.social.services.LigaService;
import br.com.debico.ui.controllers.AbstractViewController;

@Controller
public class LigaController extends AbstractViewController {

	@Inject
	private LigaService ligaService;

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

		this.addObject("liga", ligaService.recuperarLiga(id));

		// adicionar outros objetos pré-renderizados aqui.
		// este link será responsável pelo painel da liga com o mural,
		// resultados, etc.
		
		// restringir o acesso a usuários que pertencem à liga.

		return this.getModelAndView();
	}

	

}