package br.com.debico.ui.controllers.internal;

import java.security.Principal;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.debico.social.CadastroApostadorException;
import br.com.debico.social.TokenSenhaInvalidoException;
import br.com.debico.social.UsuarioInexistenteException;
import br.com.debico.social.model.PasswordContext;
import br.com.debico.social.services.UsuarioService;
import br.com.debico.ui.controllers.AbstractViewController;

@Controller
public class SenhaController extends AbstractViewController {

    @Inject
    private UsuarioService usuarioService;

    public SenhaController() {

    }

    @Override
    protected String getViewName() {
	return "senha";
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

    /**
     * Tentativa de recuperação de senha.
     * 
     * @param emailUsuario
     * @return
     */
    @RequestMapping(value = "/public/senha", method = RequestMethod.POST)
    public ModelAndView esqueciSenha(
	    @RequestParam("email_usuario") String emailUsuario) {
	resetViewName();

	try {
	    usuarioService.enviarTokenEsqueciMinhaSenha(emailUsuario);
	    this.redirecionarSucesso("senha.envio_token.sucesso");
	} catch (UsuarioInexistenteException e) {
	    this.redirecionarErro(e);
	}

	return this.getModelAndView();
    }

    /**
     * Link vindo do email do usuário.
     * 
     * @param token
     * @return
     */
    @RequestMapping(value = "/public/senha", method = RequestMethod.GET)
    public ModelAndView recuperarTokenSenha(@RequestParam("token") String token) {
	resetViewName();

	try {
	    usuarioService.validarTokenEsqueciMinhaSenha(token);
	    final PasswordContext passwordContext = new PasswordContext();
	    passwordContext.setTokenSenha(token);
	    this.addObject("passwordContext", passwordContext);
	} catch (TokenSenhaInvalidoException e) {
	    this.redirecionarErro(e);
	}

	return this.getModelAndView();
    }

}
