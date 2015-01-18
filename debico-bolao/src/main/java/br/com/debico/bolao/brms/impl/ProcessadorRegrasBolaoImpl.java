package br.com.debico.bolao.brms.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.brms.CalculoPalpitesService;
import br.com.debico.bolao.brms.ProcessadorRegrasBolaoFeedback;
import br.com.debico.bolao.brms.ProcessadorRegrasBolaoLog;
import br.com.debico.bolao.dao.ProcessadorRegrasBolaoLogDAO;
import br.com.debico.campeonato.CampeonatoService;
import br.com.debico.campeonato.brms.ResultadosService;
import br.com.debico.campeonato.dao.RodadaDAO;
import br.com.debico.core.helpers.CacheKeys;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.CampeonatoCopa;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.model.campeonato.Rodada;

@Named
@Transactional(readOnly = false)
class ProcessadorRegrasBolaoImpl implements ProcessadorRegrasBolaoFeedback {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessadorRegrasBolaoImpl.class);

	@Inject
	private ResultadosService<CampeonatoPontosCorridos> campeonatoPCResultados;
	
	@Inject
	private CalculoPalpitesService calculoPalpitesService;

	@Inject
	@Named("campeonatoServiceImpl") // padrão
	private CampeonatoService campeonatoService;

	@Inject
	private RodadaDAO rodadaDAO;
	
	@Inject
	private ProcessadorRegrasBolaoLogDAO logDAO;

	public ProcessadorRegrasBolaoImpl() {

	}
	
	public List<ProcessadorRegrasBolaoLog> recuperarLogsProcessamento(Date dataProcessamento) {
		LocalDate dataMeiaNoite = new DateTime(dataProcessamento).toLocalDate();
		LOGGER.debug("[recuperarLogsProcessamento] Recuperando os logs a partida da data {}.", dataMeiaNoite);
		
		return logDAO.recuperarLogs(dataMeiaNoite.toDate());
	}

	@CacheEvict(value = {
	        CacheKeys.TABELA_CAMPEONATO, 
	        CacheKeys.RANKING_APOSTADORES, 
	        CacheKeys.DESEMPENHO_IND_APOSTADOR
	        }, allEntries = true)
	public void processarResultados() {
		LOGGER.debug("[processarResultados] Recuperando os campeonatos ativos para serem processados.");
		final List<CampeonatoImpl> campeonatos = 
				campeonatoService.selecionarCampeonatosAtivos();

		LOGGER.debug(
				"[processarResultados] Recuperados os campeonatos {}. Iniciando o processamento.",
				campeonatos);

		for (CampeonatoImpl c : campeonatos) {
			this.processarResultados(c);
		}

	}

	@CacheEvict(value = {
	        CacheKeys.TABELA_CAMPEONATO, 
	        CacheKeys.RANKING_APOSTADORES, 
	        CacheKeys.DESEMPENHO_IND_APOSTADOR
	        }, allEntries = true)
	public void processarResultados(Campeonato campeonato) {
		if (campeonato instanceof CampeonatoCopa) {
			this.processarResultadosCopa((CampeonatoCopa) campeonato);
		} else {
			this.processarResultadosPontosCorridos((CampeonatoPontosCorridos) campeonato);
		}
		
	}
	
	protected void processarResultadosPontosCorridos(final CampeonatoPontosCorridos campeonatoPontosCorridos) {
		// TODO: melhorar essa query. recuperar rodadas nao calculadas que contenham partidas com placar.
		final List<Rodada> rodadas = 
				rodadaDAO.selecionarRodadasNaoCalculadas(campeonatoPontosCorridos);
		LOGGER.debug(
				"[processarResultadosPontosCorridos] Recuperadas as rodadas {} do campeonato {}. Pronto para delegar o processamento.",
				rodadas, campeonatoPontosCorridos);

		for (Rodada rodada : rodadas) {
			try {
				List<PartidaRodada> partidas = campeonatoPCResultados.processar(campeonatoPontosCorridos, rodada); 
				
				if(!partidas.isEmpty()) {
					calculoPalpitesService.computarPalpites(campeonatoPontosCorridos, partidas);
					logDAO.inserir(new ProcessadorRegrasBolaoLog(campeonatoPontosCorridos, rodada));
					LOGGER.debug("[processarResultadosPontosCorridos] Rodada {} do campeonato {} processada com sucesso.", 
							rodada, 
							campeonatoPontosCorridos);
				}
			} catch (Exception ex) {
				LOGGER.error("Ops! Ocorreu um erro durante o processamento", ex);
				logDAO.inserir(new ProcessadorRegrasBolaoLog(campeonatoPontosCorridos, rodada, ex));
			}
			
		}
	}

	protected void processarResultadosCopa(final CampeonatoCopa campeonatoCopa) {
		// nothing até implementar campeonatos com esse tipo de partida.
		// 1) verificar se estamos na fase de grupos ou mata-mata.
		// 2) processar de acordo com o status atual do campeonato.
	}

}
