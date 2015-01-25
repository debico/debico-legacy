package br.com.debico.notify.services;

import java.util.HashMap;
import java.util.Map;

import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.notify.model.Contato;
import br.com.debico.notify.model.Template;

/**
 * Helper para a criação dos templates.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
public final class TemplateContextoBuilder {
	
    private TemplateContextoBuilder() {

    }

    /**
     * Cria o contexto para a criação da mensagem de notificação a partir de um template.
     * 
     * @param campeonato
     * @return
     */
    public static Map<String, Object> alertaPalpite(final Template template, final Campeonato campeonato) {
        final Map<String, Object> context = new HashMap<String, Object>();
        context.put("campeonato", campeonato);
        context.put("link_acesso", 
                String.format(
                        template.getLinkAcessoFormat(), 
                        campeonato.getTipo().toLowerCase(), 
                        campeonato.getPermalink()));
        
        return context;
    }
    
    public static Map<String, Object> contato(final String assunto, final String mensagem, final Contato contato) {
    	final Map<String, Object> context = new HashMap<String, Object>();
    	context.put("mensagem", mensagem);
    	context.put("contato", contato);
    	context.put("assunto", assunto);
    	
    	return context;
    }
    
}
