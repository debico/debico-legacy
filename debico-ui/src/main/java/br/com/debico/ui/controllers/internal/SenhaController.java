package br.com.debico.ui.controllers.internal;

import java.security.Principal;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.debico.social.CadastroApostadorException;
import br.com.debico.social.model.PasswordContext;
import br.com.debico.social.services.UsuarioService;
import br.com.debico.ui.controllers.AbstractViewController;

@Controller
public class SenhaController extends AbstractViewController {

    @Inject
    private UsuarioService usuarioService;

    public SenhaController() {

    }

    @RequestMapping(value = "/senha", method = RequestMethod.GET)
    public ModelAndView senha(Principal principal) {
        resetViewName();
        final PasswordContext passwordContext = new PasswordContext();
        passwordContext.setEmailUsuario(principal.getName());
        this.addObject("passwordContext", passwordContext);

        return this.getModelAndView();
    }

    @RequestMapping(value = "/senha", method = RequestMethod.POST)
    public ModelAndView senha(
            @ModelAttribute("passwordContext") PasswordContext passwordContext) {
        resetViewName();
        try {
            usuarioService.alterarSenhaApostadorUsuario(passwordContext);
            this.redirecionarSucesso("senha.sucesso");
        } catch (CadastroApostadorException e) {
            this.setError(e);
        }
        return this.getModelAndView();
    }

    @Override
    protected String getViewName() {
        return "senha";
    }

}
