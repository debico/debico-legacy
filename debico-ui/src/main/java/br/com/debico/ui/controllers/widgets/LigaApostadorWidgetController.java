package br.com.debico.ui.controllers.widgets;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.debico.social.InscricaoLigaException;
import br.com.debico.social.model.LigaApostadorLite;
import br.com.debico.social.services.LigaApostadorService;

@Controller
public class LigaApostadorWidgetController {

	@Inject
	private LigaApostadorService ligaApostadorService;

	public LigaApostadorWidgetController() {

	}

	@RequestMapping(value = "/widgets/liga_apostador", method = RequestMethod.POST)
	public @ResponseBody boolean inscreverApostador(
			@RequestBody LigaApostadorLite ligaApostador) throws InscricaoLigaException {
		return ligaApostadorService.inscreverApostador(ligaApostador);
	}

}
