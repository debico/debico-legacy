package br.com.debico.notify.services.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.notify.dao.TemplateDAO;
import br.com.debico.notify.model.Contato;
import br.com.debico.notify.model.ContatoImpl;
import br.com.debico.notify.model.EmailTemplate;
import br.com.debico.notify.model.TipoNotificacao;
import br.com.debico.notify.services.ContatoService;
import br.com.debico.notify.services.EmailNotificacaoService;
import br.com.debico.notify.services.TemplateContextoBuilder;

@Named
@Transactional(readOnly = true)
class ContatoServiceImpl implements ContatoService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContatoServiceImpl.class);

	@Inject
	private EmailNotificacaoService emailNotificacaoService;

	@Inject
	private TemplateDAO templateDAO;

	@Value("${br.com.debico.admin.email}")
	private String emailAdmin;

	private Contato contatoAdmin;

	public ContatoServiceImpl() {

	}

	@PostConstruct
	public void init() {
		checkNotNull(
				emptyToNull(emailAdmin),
				"O email do admin eh obrigatorio. Verifique se a propriedade 'com.omniweb.bolao.admin.email' esta definida no ambiente.");
		contatoAdmin = new ContatoImpl(emailAdmin);
	}

	public void setEmailAdmin(String emailAdmin) {
		this.emailAdmin = emailAdmin;
	}

	public void enviarFeedback(Contato contato, String assunto, String mensagem) {
		final EmailTemplate emailTemplate = templateDAO
				.selecionarEmailTemplate(TipoNotificacao.CONTATO);

		if (emailTemplate != null) {
			LOGGER.debug(
					"[enviarFeedback] Enviando email de contato para o administrador {} de {}",
					contatoAdmin, contato);
			emailNotificacaoService.enviarNotificacao(contatoAdmin,
					emailTemplate,
					TemplateContextoBuilder.contato(assunto, mensagem, contato));
		} else {
			LOGGER.warn(
					"[enviarFeedback] Template '{}' nao econtrado. Email nao enviado.",
					TipoNotificacao.CONTATO);
		}
	}

	public Contato getContatoAdmin() {
		return contatoAdmin;
	}

}
