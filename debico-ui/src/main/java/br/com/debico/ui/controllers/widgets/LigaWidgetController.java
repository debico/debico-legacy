package br.com.debico.ui.controllers.widgets;

import java.security.Principal;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.debico.social.CadastroLigaException;
import br.com.debico.social.model.Liga;
import br.com.debico.social.services.LigaService;
import br.com.debico.ui.thymeleaf.UsuarioAuthUtils;

@Controller
public class LigaWidgetController {

    @Inject
    private LigaService ligaService;

    public LigaWidgetController() {

    }

    @RequestMapping(value = "/widgets/liga", method = RequestMethod.POST)
    public @ResponseBody Liga cadastrar(@RequestBody final String nomeLiga,
            Principal principal) throws CadastroLigaException {
        return ligaService.cadastrarNovaLiga(nomeLiga,
                UsuarioAuthUtils.userId());
    }

    @RequestMapping(value = "/widgets/liga/{id}", method = RequestMethod.GET)
    public @ResponseBody Liga recuperarLiga(@PathVariable("id") final long id) {
        return ligaService.recuperarLiga(id).orNull();
    }

    @RequestMapping(value = "/widgets/liga/{id}", method = RequestMethod.PATCH)
    public @ResponseBody Liga atualizar(@PathVariable("id") final long id,
            @RequestBody final String nomeLiga, Principal principal)
            throws CadastroLigaException {
        return ligaService.atualizarLiga(id, nomeLiga,
                UsuarioAuthUtils.userId());
    }

}
