package br.com.debico.ui.controllers.admin;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.ManagerBeans;
import br.com.debico.resultados.ProcessorManager;
import br.com.debico.ui.controllers.AbstractViewController;

/**
 * Utilizado para ser um meio para disparar o processamento dos resultados
 * manualmente.
 * 
 * @author ricardozanini
 *
 */
@Controller
public class ProcessamentoResultadosController extends AbstractViewController {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(ProcessamentoResultadosController.class);

    @Inject
    @Named(ManagerBeans.SUMARIZADOR_MANAGER)
    private ProcessorManager<Campeonato> processorManager;

    public ProcessamentoResultadosController() {

    }

    @Override
    protected String getViewName() {
	return "processador";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/processador")
    public ModelAndView processador() {
	return this.getModelAndView();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/admin/processador/disparar")
    public @ResponseBody boolean dispararProcessador() {
	LOGGER.debug("[dispararProcessador] Iniciando processador.");
	List<Context> contexts = this.processorManager.start();
	LOGGER.debug(
		"[dispararProcessador] Processamento realizado. Retorno: {}",
		contexts);
	// provisorio ate adicionarmos a resposta correta
	return true;
    }

}
