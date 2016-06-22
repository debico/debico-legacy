package br.com.debico.ui.controllers.admin;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.debico.resultados.ManagerBeans;
import br.com.debico.resultados.ProcessorManager;
import br.com.debico.ui.controllers.AbstractViewController;

@Controller
public class ProcessamentoAtualizacaoPlacarController extends
	AbstractViewController {

    @Named(ManagerBeans.ATUALIZACAO_DADOS_PARTIDA_MANAGER)
    @Inject
    private ProcessorManager processorManager;

    public ProcessamentoAtualizacaoPlacarController() {

    }

    @Override
    protected String getViewName() {
	return "atualizacao_placar";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/resultados/atualizacao/placar/disparar")
    public @ResponseBody boolean dispararProcessador() {
	processorManager.start();
	// provisorio ate adicionarmos a resposta correta
	return true;
    }

}
