package br.com.debico.ui.controllers.widgets;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.debico.social.model.LigaApostadorLite;
import br.com.debico.social.services.LigaApostadorService;

import com.wordnik.swagger.annotations.Api;

@Controller
@Api(value = "liga_apostador", description = "Widget para tratar das inscrições dos apostadores na liga.")
public class LigaApostadorWidgetController {

	@Inject
	private LigaApostadorService ligaApostadorService;

	public LigaApostadorWidgetController() {

	}

	@RequestMapping(value = "/widgets/liga_apostador", method = RequestMethod.POST)
	public @ResponseBody boolean inscreverApostador(
			@RequestBody LigaApostadorLite ligaApostador) {
		return ligaApostadorService.inscreverApostador(ligaApostador);
	}

}
