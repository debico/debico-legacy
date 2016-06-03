package br.com.debico.notify.model;

/**
 * Tipo de template utilizado para enviar uma notificação.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
public enum TipoNotificacao {

    /**
     * Tipo de notificação utilizada para alertar um apostador para realizar um
     * palpite.
     */
    ALERTA_PALPITE(1),
    /**
     * Tipo de notificação de quando alguém envia o formulário de feedback.
     */
    CONTATO(2),
    /**
     * Tipo de notificação que ocorre quando o usuário esquece a senha e
     * requisita o token.
     */
    ESQUECI_SENHA(3),
    /**
     * Tipo de notificação que ocorre após a atualização com sucesso das
     * partidas a partir de integrações externas.
     */
    ALERTA_ATUALIZACAO_PARTIDA(4),
    /**
     * Tipo de notificação que ocorre em caso de erros durante a atualização da
     * partida.
     */
    ERRO_ATUALIZACAO_PARTIDA(5);

    private final int id;

    private TipoNotificacao(final int id) {
	this.id = id;
    }

    public int getId() {
	return id;
    }

}
