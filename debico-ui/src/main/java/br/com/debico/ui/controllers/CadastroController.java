package br.com.debico.ui.controllers;

import javax.inject.Inject;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import br.com.debico.model.Apostador;
import br.com.debico.social.CadastroApostadorException;
import br.com.debico.social.services.UsuarioService;

@Controller
public class CadastroController extends AbstractViewController {

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private ProviderSignInUtils providerSignInUtils;

    @Override
    protected String getViewName() {
        return "cadastro";
    }

    @RequestMapping(value = "/public/cadastro", method = RequestMethod.GET)
    public ModelAndView cadastroForm(
            @RequestParam(value = "sucesso", required = false) String sucesso) {
        clearError();

        if (sucesso != null) {
            getModelAndView().addObject("sucesso", true);
        } else {
            getModelAndView().addObject("apostador", new Apostador());
        }

        return getModelAndView();
    }

    @RequestMapping(value = "/public/cadastro", method = RequestMethod.POST)
    public ModelAndView cadastrarUsuario(
            @ModelAttribute("apostador") Apostador apostador,
            @RequestParam("confirmar_password") String confirmacaoSenha) {
        try {
            usuarioService.cadastrarApostadorUsuario(apostador,
                    confirmacaoSenha);

            this.redirecionarSucesso("cadastro.sucesso");
        } catch (CadastroApostadorException e) {
            this.setError(e);
        }

        return getModelAndView();
    }

    @RequestMapping(value = "/public/cadastro/social", method = RequestMethod.GET)
    public ModelAndView cadastrarUsuario(WebRequest request) {
        Connection<?> connection = this.providerSignInUtils
                .getConnectionFromSession(request);
        try {
            this.usuarioService
                    .cadastrarOuRelacionarApostadorUserProfide(connection
                            .fetchUserProfile());
            // redirecionar para oauth novamente.
            this.redirecionarSucesso("cadastro.sucesso");
        } catch (CadastroApostadorException e) {
            this.setError(e);
        }

        return getModelAndView();
    }

}
