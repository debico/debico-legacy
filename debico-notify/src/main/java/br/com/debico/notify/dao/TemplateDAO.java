package br.com.debico.notify.dao;

import br.com.debico.notify.model.EmailTemplate;
import br.com.debico.notify.model.Template;
import br.com.debico.notify.model.TipoNotificacao;
import br.com.tecnobiz.spring.config.dao.Dao;


/**
 * Interface utilizada para interagir com as estruturas de Notificação.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.1.0
 */
public interface TemplateDAO extends Dao<Template, Integer> {

    EmailTemplate selecionarEmailTemplate(TipoNotificacao tipoNotificacao);
    
}
