package br.com.debico.ui.controllers.internal;

import java.security.Principal;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.debico.model.Apostador;
import br.com.debico.social.CadastroApostadorException;
import br.com.debico.social.services.ApostadorService;
import br.com.debico.ui.controllers.AbstractViewController;

@Controller
public class PerfilController extends AbstractViewController {
	
	@Inject
	private ApostadorService apostadorService;

	public PerfilController() {

	}
	
	@Override
	protected String getViewName() {
		return "perfil";
	}
	
	@RequestMapping(value="/perfil")
	public ModelAndView perfil(final Principal principal) {
		resetViewName();
		
		getModelAndView().addObject("apostador", this.apostadorService.selecionarPerfilApostadorPorEmail(principal.getName()));
		getModelAndView().setViewName(getViewName());
	
		return getModelAndView();
	}
	
	@RequestMapping(value = "/perfil", method = RequestMethod.POST)
	public ModelAndView atualizarPerfil(@ModelAttribute("apostador") Apostador apostador) {
	    try {
            apostadorService.atualizarApostador(apostador);
            this.redirecionarSucesso("perfil.sucesso");
        } catch (CadastroApostadorException e) {
        	this.setError(e);
        }
	    
	    return getModelAndView();
	}

}
