package br.com.debico.notify.services.impl;

import static com.google.common.base.Strings.emptyToNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Predicates;
import com.google.common.collect.Maps;

import br.com.debico.notify.model.EmailTemplate;

/**
 * Helper para facilitar a interação com templates
 * 
 * @author rzanini
 * @since 2.0.5
 */
public final class TemplateUtils {

	public static final String LINK_BUILDER_PARAM = "LINK";
	public static final String KEY_CONTEXTO_LINKS = "link_acesso";

	private TemplateUtils() {

	}

	/**
	 * Cria um link para enviar no corpo do email.
	 * 
	 * @param urlFormat
	 *            formatada como {@link EmailTemplate#getLinkAcessoFormat()}.
	 * @param contexto
	 *            da url (key/value), key deve começar com a literal "LINK".
	 *            Exemplo: LINK0, LINK1. etc.
	 * @see TemplateUtils#contextLinkBuilder(Object...)
	 * @return
	 */
	public static String linkBuilder(final String urlFormat, final Map<String, Object> contexto) {

		// @formatter:off
		final Map<String, Object> linkContext = Maps.filterKeys(contexto,
				Predicates.containsPattern(String.format("(%s[0-9])", TemplateUtils.LINK_BUILDER_PARAM)));
		if (linkContext.values().isEmpty()) {
			return urlFormat;
		} else {
			return String.format(urlFormat, linkContext.values().toArray());
		}
		// @formatter:on
	}

	/**
	 * Monta o contexto para criar o link no email. Adiciona, em cada par o
	 * valor: LINK0 > arg0, LINK1 > arg1 .. N.
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

	/**
	 * Formata a URL <code>urlStringFormat</code> de acordo com as variáveis de
	 * <code>linksVars</code> e coloca no contexto.
	 * 
	 * @since 2.0.5
	 * @param contexto
	 * @param urlStringFormat
	 * @param linksVars
	 */
	public static void adicionaLinkFormatadoContexto(Map<String, Object> contexto, String urlStringFormat,
			List<String> linksVars) {

		if (!linksVars.isEmpty() && emptyToNull(urlStringFormat) != null) {
			contexto.put(KEY_CONTEXTO_LINKS,
					TemplateUtils.linkBuilder(urlStringFormat, TemplateUtils.contextLinkBuilder(linksVars.toArray())));
		}

	}

}
