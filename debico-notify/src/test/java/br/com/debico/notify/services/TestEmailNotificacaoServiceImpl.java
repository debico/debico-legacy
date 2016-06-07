package br.com.debico.notify.services;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.mock_javamail.Mailbox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import br.com.debico.model.PartidaBase;
import br.com.debico.model.Time;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.notify.model.ContatoImpl;
import br.com.debico.notify.model.TipoNotificacao;
import br.com.debico.notify.spring.NotifyConfig;
import br.com.debico.test.spring.AbstractUnitTest;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { NotifyConfig.class })
public class TestEmailNotificacaoServiceImpl extends AbstractUnitTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestEmailNotificacaoServiceImpl.class);

	@Inject
	private EmailNotificacaoService service;

	@Test
	public void testEnviarNotificacaoContatoTipoNotificacaoMapOfStringObject() throws MessagingException, IOException {
		final Map<String, Object> contexto = new HashMap<>();
		final String email = "brucebanner@marvel.com";
		final PartidaBase partida = new PartidaBase(new Time("Santos"), new Time("Palmeiras"));
		partida.setLocal("Morumbi");
		final CampeonatoPontosCorridos campeonato = new CampeonatoPontosCorridos("Campeonato do Teste");
		campeonato.setPermalink("campeonato-teste");
		contexto.put("campeonato", campeonato);
		contexto.put("partidas", Lists.newArrayList(partida));

		service.enviarNotificacao(new ContatoImpl(email), TipoNotificacao.ALERTA_ATUALIZACAO_PARTIDA, contexto,
				Lists.newArrayList(campeonato.getTipo().toString(), campeonato.getPermalink()));

		List<Message> inbox = Mailbox.get(email);

		assertFalse(inbox.isEmpty());
		final Message message = inbox.get(0);
		LOGGER.debug("Mensagem recebida: {}", message.getContent());
		assertThat(message.getSubject(), containsString("Administrador:"));
		assertEquals(email, message.getAllRecipients()[0].toString());
		Mailbox.get(email).clear();
	}

	@Test
	public void testEnviarNotificacaoListOfContatoTipoNotificacaoMapOfStringObject() throws MessagingException, IOException {
		final Map<String, Object> contexto = new HashMap<>();
		final String email = "peter.parker@marvel.com";
		final CampeonatoPontosCorridos campeonato = new CampeonatoPontosCorridos("Campeonato do Teste");
		campeonato.setPermalink("campeonato-teste");
		contexto.put("campeonato", campeonato);
		contexto.put("stackTrace", new Exception("teste"));

		service.enviarNotificacao(Lists.newArrayList(new ContatoImpl(email)), TipoNotificacao.ERRO_ATUALIZACAO_PARTIDA,
				contexto);

		List<Message> inbox = Mailbox.get(email);

		assertFalse(inbox.isEmpty());
		final Message message = inbox.get(0);
		LOGGER.debug("Mensagem recebida: {}", message.getContent());
		assertThat(message.getSubject(), containsString("Administrador:"));
		assertEquals(email, message.getAllRecipients()[0].toString());
		Mailbox.get(email).clear();

	}

}
