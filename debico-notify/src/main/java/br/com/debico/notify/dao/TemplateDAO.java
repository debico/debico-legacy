package br.com.debico.notify.dao;

import br.com.debico.notify.model.EmailTemplate;
import br.com.debico.notify.model.TipoNotificacao;


/**
 * Interface utilizada para interagir com as estruturas de Notificação.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.1.0
 */
public interface TemplateDAO {

    EmailTemplate selecionarEmailTemplate(TipoNotificacao tipoNotificacao);
    
}
