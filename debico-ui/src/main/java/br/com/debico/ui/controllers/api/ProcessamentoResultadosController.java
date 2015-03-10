package br.com.debico.ui.controllers.api;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.debico.bolao.brms.ProcessadorRegrasBolaoFeedback;
import br.com.debico.bolao.brms.model.ProcessadorRegrasBolaoLog;
import br.com.debico.ui.annotations.APIController;

/**
 * Utilizado para ser um meio para disparar o processamento dos resultados manualmente.
 * 
 * @author ricardozanini
 *
 */
@Controller
@APIController
public class ProcessamentoResultadosController {

	@Inject
	private ProcessadorRegrasBolaoFeedback processadorResultados;
	
	public ProcessamentoResultadosController() {
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/processador/disparar")
	public @ResponseBody List<ProcessadorRegrasBolaoLog> dispararProcessamento() {
		processadorResultados.processarResultados();
		
		return processadorResultados.recuperarLogsProcessamento(new Date());
	} 

}
