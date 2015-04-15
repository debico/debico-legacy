package br.com.debico.ui.controllers.internal;

import java.security.Principal;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.debico.social.CadastroLigaException;
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

	@RequestMapping(value = "/liga")
	public ModelAndView minhasLigas(final Principal principal) {
		resetViewName();

		this.addObject("minhas-ligas",
				ligaService.consultarLiga(principal.getName()));

		return this.getModelAndView();
	}

	@RequestMapping(value = "/liga", method = RequestMethod.POST)
	public ModelAndView cadastrar(@RequestBody final String nomeLiga,
			final Principal principal) {
		try {
			ligaService.cadastrarNovaLiga(nomeLiga, principal.getName());

			this.redirecionarSucesso("liga.cadastro.sucesso");
		} catch (CadastroLigaException e) {
			this.setError(e);
		}

		return this.getModelAndView();
	}

	@RequestMapping(value = "/liga/{id}")
	public ModelAndView recuperarLiga(@PathVariable("id") final long id) {
		resetViewName();

		this.addObject("liga", ligaService.recuperarLiga(id));

		return this.getModelAndView();
	}

	// na verdade seria um PATCH, mas faremos assim porque forms no HTML nao aceitam outra coisa se nao POST ou GET
	@RequestMapping(value = "/liga/{id}", method = RequestMethod.POST)
	public ModelAndView atualizar(@PathVariable("id") final long id,
			@RequestBody final String nomeLiga, Principal principal) {

		try {
			ligaService.atualizarLiga(id, nomeLiga, principal.getName());

			this.redirecionarSucesso("liga.atualizacao.sucesso");
		} catch (CadastroLigaException e) {
			this.setError(e);
		}

		return this.getModelAndView();
	}

}
