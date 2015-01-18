package br.com.debico.bolao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.AlertaPalpiteService;
import br.com.debico.campeonato.CampeonatoService;
import br.com.debico.model.Apostador;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.notify.EmailNotificacaoService;
import br.com.debico.notify.TemplateContextoBuilder;
import br.com.debico.notify.dao.TemplateDAO;
import br.com.debico.notify.model.EmailTemplate;
import br.com.debico.notify.model.TipoNotificacao;
import br.com.debico.social.dao.ApostadorDAO;

@Named
@Transactional(readOnly = true)
class AlertaPalpiteServiceImpl implements AlertaPalpiteService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AlertaPalpiteServiceImpl.class);
	
	@Inject
	private ApostadorDAO apostadorDAO;
	
	@Inject
	private TemplateDAO templateDAO;
	
	@Inject
	@Named("campeonatoServiceImpl") // padrão
	private CampeonatoService campeonatoService;
	
	@Inject
	private EmailNotificacaoService emailNotificacaoService;

	public AlertaPalpiteServiceImpl() {
		
	}

	public List<Apostador> enviarAlertasApostadoresSemPalpite(Date dataPartida) {
		//mantendo apenas o dia, mês e ano.
		final LocalDate dataIni = new DateTime(dataPartida).toLocalDate();
		final DateTime dataFim = dataIni.toDateTimeAtStartOfDay().plusDays(1).minusMinutes(1);
		
		final List<Apostador> remetentes = new ArrayList<Apostador>();
		
		LOGGER.debug("Enviando alertas para apostadores sem palpites para partidas entre {} e {}", dataIni, dataFim);
		
		List<CampeonatoImpl> campeonatos = campeonatoService.selecionarCampeonatosAtivos();
		
		for (CampeonatoImpl campeonato : campeonatos) {
			LOGGER.info("Enviando alertas de palpites entre {} e {} para partidas do campeonato {}", dataIni, dataFim, campeonato);
			List<Apostador> apostadores = apostadorDAO.selecionarApostadoresSemPalpite(campeonato, dataIni.toDate(), dataFim.toDate());
			
			LOGGER.trace("Apostadores selecionados: {}", apostadores);
			
			if(!apostadores.isEmpty()) {
			    remetentes.addAll(apostadores);
			    
			    final EmailTemplate template = templateDAO.selecionarEmailTemplate(TipoNotificacao.ALERTA_PALPITE);
			    
			    if(template != null) {
			    	final Map<String, Object> contexto = TemplateContextoBuilder.alertaPalpite(template, campeonato);
			    	emailNotificacaoService.enviarNotificacao(apostadores, template, contexto);
			    } else {
			    	LOGGER.warn("Nao foi possivel recuperar o template '{}' para o envio de email. Cancelando os envios", TipoNotificacao.ALERTA_PALPITE);
			    	break;
			    }
			}
		}
		
		return remetentes;
	}

	public List<Apostador> enviarAlertasApostadoresSemPalpite() {
		return this.enviarAlertasApostadoresSemPalpite(new Date());
	}

}
