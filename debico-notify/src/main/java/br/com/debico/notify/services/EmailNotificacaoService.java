package br.com.debico.notify.services;

import java.util.List;
import java.util.Map;

import br.com.debico.notify.model.Contato;
import br.com.debico.notify.model.EmailTemplate;
import br.com.debico.notify.model.TipoNotificacao;

/**
 * <i>Marked interface</i> para definir o serviço de envio de notificações por
 * email.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
public interface EmailNotificacaoService extends NotificacaoService<EmailTemplate> {

	public void enviarNotificacao(Contato destinatario, EmailTemplate template, Map<String, Object> contexto);

	public void enviarNotificacao(Contato destinatario, TipoNotificacao notificacao, Map<String, Object> contexto);

	/**
	 * 
	 * @param destinatario
	 * @param notificacao
	 * @param contexto
	 * @param contextoLinks
	 * @since 2.0.5
	 */
	public void enviarNotificacao(Contato destinatario, TipoNotificacao notificacao, Map<String, Object> contexto,
			List<String> contextoLinks);

	/**
	 * Para enviar o mesmo email para N destinatários.
	 * 
	 * @param destinatarios
	 * @param notificacao
	 * @param contexto
	 * @since 2.0.5
	 */
	public void enviarNotificacao(List<? extends Contato> destinatarios, TipoNotificacao notificacao,
			Map<String, Object> contexto);

	/**
	 * Enviar o mesmo email para N destinatários com os objetos que vão na
	 * formação do link do email relacionados.
	 * 
	 * @param destinatarios
	 * @param notificacao
	 * @param contexto
	 * @param contextoLinks
	 * @since 2.0.5
	 * 
	 */
	public void enviarNotificacao(List<? extends Contato> destinatarios, TipoNotificacao notificacao,
			Map<String, Object> contexto, List<String> contextoLinks);

}
