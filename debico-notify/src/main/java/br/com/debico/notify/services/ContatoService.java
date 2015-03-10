package br.com.debico.notify.services;

import br.com.debico.notify.model.Contato;


/**
 * API para envio de feedback. 
 * 
 * @author ricardozanini
 *
 */
public interface ContatoService {

	void enviarFeedback(final Contato contato, final String assunto, final String mensagem);
	
	Contato getContatoAdmin();
	
}
