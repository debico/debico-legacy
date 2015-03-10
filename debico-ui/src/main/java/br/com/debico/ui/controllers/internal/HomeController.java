package br.com.debico.ui.controllers.internal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.debico.ui.controllers.AbstractViewController;

@Controller
public class HomeController extends AbstractViewController {

	@Override
	protected String getViewName() {
		return "index";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		return getModelAndView();
	}

}
