package br.com.debico.ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.google.common.base.Strings.isNullOrEmpty;

@Controller
public class LoginController extends AbstractViewController {
	
	@Override
	protected String getViewName() {
		return "login";
	}

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout) {
        
        clearError();
        
        if(!isNullOrEmpty(error)) {
        	this.setError("login.error");
        }
        
        this.getModelAndView().addObject("logout", (logout != null));
        
        return getModelAndView();
    }
	
}
