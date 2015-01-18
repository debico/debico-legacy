package br.com.debico.notify;

import java.util.List;
import java.util.Map;

import br.com.debico.model.Apostador;
import br.com.debico.notify.model.Template;

/**
 * API para envio de notificações para {@link Apostador}es.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.1.0
 * @param <T> tipo de template utilizado para construir a mensagem de notificação.
 */
public interface NotificacaoService<T extends Template> {

    /**
     * Envia uma notificação para uma lista de apostadores com um contexto em comum. <p/>
     * Antes do envio, entretanto, cada apostador é colocado no contexto do template antes deste ser processado.
     * 
     * @param apostadores lista de destinatários
     * @param template utilizado para montar a notificação
     * @param contexto dados para a criação da mensagem personalizada
     */
    void enviarNotificacao(List<Apostador> apostadores, T template, Map<String, Object> contexto);
    
    /**
     * @see #enviarNotificacao(List, Template, Map)
     */
    void enviarNotificacao(Apostador apostador, T template, Map<String, Object> contexto);
    
}
