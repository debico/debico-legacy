package br.com.debico.notify.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import br.com.debico.model.Apostador;
import br.com.debico.notify.EmailNotificacaoService;
import br.com.debico.notify.model.Contato;
import br.com.debico.notify.model.EmailTemplate;

@Named
class EmailNotificacaoServiceImpl implements EmailNotificacaoService {

	private static final Logger LOGGER = 
			LoggerFactory.getLogger(EmailNotificacaoServiceImpl.class);

	@Inject
	private JavaMailSender mailSender;

	@Inject
	private VelocityEngine velocityEngine;

	public EmailNotificacaoServiceImpl() {

	}

	public void enviarNotificacao(List<Apostador> apostadores, EmailTemplate template, Map<String, Object> contexto) {

		for (Apostador apostador : apostadores) {
			this.enviarNotificacao(apostador, template, contexto);
		}
	}

	public void enviarNotificacao(Apostador apostador, EmailTemplate template, Map<String, Object> contexto) {
		contexto.put("apostador", apostador);

		this.enviarNotificacao((Contato) apostador, template, contexto);
	}

	public void enviarNotificacao(Contato destinatario, EmailTemplate template, Map<String, Object> contexto) {
		try {
			this.mailSender.send(this.prepareMessage(destinatario, template,
					contexto));
		} catch (MailException ex) {
			LOGGER.warn("Nao foi possivel enviar notificacao ao destinatario.",
					ex);
		}
	}

	protected MimeMessagePreparator prepareMessage(final Contato destinatario, final EmailTemplate template, final Map<String, Object> contexto) { 
		return new MimeMessagePreparator() {
			
			public void prepare(MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				final String text = VelocityEngineUtils
						.mergeTemplateIntoString(velocityEngine,
								template.getClasspathTemplate(), "UTF-8",
								contexto);

				message.setTo(destinatario.getInternetAddress());
				message.setFrom(template.getInternetAddress());
				message.setSubject(MimeUtility.encodeText(template.getAssunto(), "utf-8", "B"));
				message.setSentDate(new Date());
				message.setText(text, true);
				
				LOGGER.trace(
						"[prepareMessage] Email preparado de {}, com o assunto: {}.",
						message.getMimeMessage().getFrom(), message
								.getMimeMessage().getSubject());
				
				LOGGER.trace(
						"[prepareMessage] Enviando email para {}, mensagem: {}",
						message.getMimeMessage().getAllRecipients(), text);
			}
		};
	}
}
