package br.com.debico.bolao.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jvnet.mock_javamail.Mailbox;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.services.AlertaPalpiteService;
import br.com.debico.bolao.test.support.AbstractBolaoUnitTest;
import br.com.debico.model.Apostador;
import br.com.debico.notify.dao.TemplateDAO;
import br.com.debico.notify.model.EmailTemplate;
import br.com.debico.notify.model.TipoNotificacao;
import br.com.debico.social.services.ApostadorService;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
public class TestAlertaPalpiteServiceImpl extends AbstractBolaoUnitTest {

    @Inject
    private AlertaPalpiteService alertaPalpiteService;
    
    @Inject
    private ApostadorService apostadorService;
    
    @Inject
    private TemplateDAO templateDAO;
    
    @Test
    public void testEnviarAlertasApostadoresSemPalpiteDate() throws MessagingException, UnsupportedEncodingException {
        Date dataPartida = new DateTime(2014, 9, 21, 8, 30).toDate();
        
        List<Apostador> remetentes = alertaPalpiteService.enviarAlertasApostadoresSemPalpite(dataPartida);
        EmailTemplate template = templateDAO.selecionarEmailTemplate(TipoNotificacao.ALERTA_PALPITE);
        
        LOGGER.info("remetentes selecionados: {}", remetentes);
        
        assertNotNull(remetentes);
        assertFalse(remetentes.isEmpty());
        
        // o primeiro colocado já palpitou.
        assertTrue(!remetentes.contains(apostadorService.selecionarApostadorPorEmail(EMAIL_PRIMEIRO_RANKING)));
        
        // o tondin nao quer receber!
        assertTrue(!remetentes.contains(apostadorService.selecionarApostadorPorEmail(EMAIL_SEM_OPCAO_NOTIFICACAO)));
        
        // ele ainda nao palpitou, tem que receber.
        assertTrue(remetentes.contains(apostadorService.selecionarApostadorPorEmail(EMAIL_ULTIMO_RANKING)));
        
        for (Apostador apostador : remetentes) {
			List<Message> inbox = Mailbox.get(apostador.getEmail());
			
			assertFalse(inbox.isEmpty());
			assertEquals(template.getAssunto(), inbox.get(0).getSubject());
			assertEquals(template.getInternetAddress().toString(), inbox.get(0).getFrom()[0].toString());
		}
        
    }
    
    @Test
    public void testEnviarAlertasApostadoresSemPalpiteDiaSemPartida() throws MessagingException, UnsupportedEncodingException {
        // 4 anos no futuro não vai ter partida, então não tem que alertar NINGUÉM
        Date dataPartida = new DateTime().plusYears(4).toDate();
        
        List<Apostador> remetentes = alertaPalpiteService.enviarAlertasApostadoresSemPalpite(dataPartida);
                
        assertNotNull(remetentes);
        assertTrue(remetentes.isEmpty());
    }

}
