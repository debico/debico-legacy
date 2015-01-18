package br.com.debico.notify.model;

/**
 * Tipo de template utilizado para enviar uma notificação. 
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
public enum TipoNotificacao {
    
    /**
     * Tipo de notificação utilizada para alertar um apostador para realizar um palpite.
     */
    ALERTA_PALPITE(1),
    
    /**
     * Tipo de notificação de quando alguém envia o formulário de feedback.
     */
    CONTATO(2);
    
    private final int id;
    
    private TipoNotificacao(final int id) {
    	this.id = id;
    }
    
    public int getId() {
		return id;
	}

}
