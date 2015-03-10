package br.com.debico.ui.controllers;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.debico.notify.model.ContatoImpl;
import br.com.debico.notify.services.ContatoService;

@Controller
public class ContatoController extends AbstractViewController {

	@Inject
	private ContatoService contatoService;

	public ContatoController() {

	}
	
	@Override
	protected String getViewName() {
		return "contato";
	}

	@RequestMapping(value = "/contato/", method = RequestMethod.POST)
	public ModelAndView enviar(@RequestParam(value = "remetente", required = true) String email,
			@RequestParam(value = "assunto", required = true) String assunto,
			@RequestParam(value = "mensagem", required = true) String mensagem) {

		contatoService.enviarFeedback(new ContatoImpl(email), assunto, mensagem);
		
		this.redirecionarSucesso("contato.sucesso");
		
		return getModelAndView();
	}

}
