package br.com.debico.bolao.brms;

import java.util.Date;
import java.util.List;

import br.com.debico.bolao.brms.model.ProcessadorRegrasBolaoLog;

/**
 * Além de processar os resultados fornece pontos de extensão para fornecer feedback ao cliente.
 * 
 * @author ricardozanini
 *
 */
public interface ProcessadorRegrasBolaoFeedback extends ProcessadorRegrasBolao {
	
	/**
	 * Recupera os logs de processamento de determinada data.
	 * 
	 * @param dataProcessamento
	 * @return
	 */
	List<ProcessadorRegrasBolaoLog> recuperarLogsProcessamento(Date dataProcessamento);

}
