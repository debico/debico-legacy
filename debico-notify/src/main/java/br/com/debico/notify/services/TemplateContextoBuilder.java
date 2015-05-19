package br.com.debico.notify.services;

import java.util.HashMap;
import java.util.Map;

import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.notify.model.Contato;
import br.com.debico.notify.model.EmailTemplate;
import br.com.debico.notify.model.Template;

import com.google.common.base.Predicates;
import com.google.common.collect.Maps;

/**
 * Helper para a criação dos templates.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
public final class TemplateContextoBuilder {

    private static final String LINK_BUILDER_PARAM = "LINK";

    private TemplateContextoBuilder() {

    }

    /**
     * Cria o contexto para a criação da mensagem de notificação a partir de um
     * template.
     * 
     * @param campeonato
     * @return
     */
    public static Map<String, Object> alertaPalpite(final Template template,
	    final Campeonato campeonato) {
	final Map<String, Object> context = new HashMap<String, Object>();
	context.put("campeonato", campeonato);
	context.put("link_acesso", String.format(
		template.getLinkAcessoFormat(), campeonato.getTipo()
			.toLowerCase(), campeonato.getPermalink()));

	return context;
    }

    public static Map<String, Object> contato(final String assunto,
	    final String mensagem, final Contato contato) {
	final Map<String, Object> context = new HashMap<String, Object>();
	context.put("mensagem", mensagem);
	context.put("contato", contato);
	context.put("assunto", assunto);

	return context;
    }

    /**
     * Cria um link para enviar no corpo do email.
     * 
     * @param urlFormat
     *            formatada como {@link EmailTemplate#getLinkAcessoFormat()}.
     * @param contexto
     *            da url (key/value), key deve começar com a literal "LINK".
     *            Exemplo: LINK0, LINK1. etc.
     * @see #contextLinkBuilder(Object...)
     * @return
     */
    public static String linkBuilder(final String urlFormat,
	    final Map<String, Object> contexto) {

	// @formatter:off
	final Map<String, Object> linkContext = 
		Maps.filterKeys(
			contexto, 
			Predicates.containsPattern(String.format("(%s[0-9])", LINK_BUILDER_PARAM)));

	return String.format(urlFormat, linkContext.values().toArray());
	// @formatter:on
    }

    /**
     * Monta o contexto para criar o link no email.
     * 
     * @param params
     * @return
     */
    public static Map<String, Object> contextLinkBuilder(Object... args) {
	final Map<String, Object> params = new HashMap<>();

	for (int i = 0; i < args.length; i++) {
	    params.put(String.format("%s%s", LINK_BUILDER_PARAM, i), args[i]);
	}

	return params;
    }

}
