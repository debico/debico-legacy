package br.com.debico.bolao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.ApostadorPontuacaoService;
import br.com.debico.bolao.PalpiteExistenteException;
import br.com.debico.bolao.PalpiteForaPrazoException;
import br.com.debico.bolao.PalpiteService;
import br.com.debico.bolao.dao.PalpiteDAO;
import br.com.debico.core.DebicoException;
import br.com.debico.model.Palpite;
import br.com.debico.model.campeonato.CampeonatoImpl;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Transactional(readOnly = false)
class PalpiteServiceImpl implements PalpiteService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PalpiteServiceImpl.class);

	@Inject
	private PalpiteDAO palpiteDAO;

	@Inject
	private ApostadorPontuacaoService apostadorService;

	@Inject
	@Named("resourceBundleMessageSource")
	private MessageSource messageSource;

	public void palpitar(final Palpite palpite, final CampeonatoImpl campeonato) throws DebicoException {
		LOGGER.debug("[palpitar] Tentando realizar o palpite: {}", palpite);
		
		checkNotNull(palpite);
		verificarPrazoPalpite(palpite);
		verificarPalpiteExistente(palpite);
		apostadorService.inscreverApostadorCampeonatoSileciosamente(palpite.getApostador(), campeonato);
		
		if(palpite.getId() == 0) {
			palpiteDAO.inserir(palpite);
		} else {
			palpiteDAO.atualizar(palpite);
		}
		
		LOGGER.debug("[palpitar] Palpite realizado: {}", palpite);
	}

	/**
	 * Verifica se o palpite para esta partida já não foi realizado.
	 * <p/>
	 * Essa validação é realizada apenas se o ID do palpite for igual a 0. 
	 * 
	 * @param palpite
	 * @throws PalpiteExistenteException caso o palpite seja existente.
	 */
	protected void verificarPalpiteExistente(final Palpite palpite) throws PalpiteExistenteException {
		LOGGER.debug("[verificarPalpiteExistente] Verificando se eh um palpite existente {}.", palpite);
		
		if(palpite.getId() == 0) {
			if(palpiteDAO.pesquisarExistenciaPalpite(palpite.getPartida(), palpite.getApostador())) {
				throw new PalpiteExistenteException(messageSource);
			}
		}
	}

	/**
	 * Verifica se o prazo para realizar o palpite ainda é válido.
	 * <p/>
	 * Uma partida só pode ser palpitada até uma hora antes do seu início.
	 * 
	 * @param palpite
	 * @throws PalpiteForaPrazoException
	 */
	protected void verificarPrazoPalpite(final Palpite palpite)
			throws PalpiteForaPrazoException {
		LOGGER.debug("[verificarPrazoPalpite] Verificando o prazo do palpite {}.", palpite);
		
		DateTime dt = new DateTime(palpite.getPartida().getDataHoraPartida());

		if (dt.minus(System.currentTimeMillis()).getMillis() < PRAZO_PALPITE_MILLIS) {
			throw new PalpiteForaPrazoException(this.messageSource,
					PRAZO_PALPITE_HORAS);
		}
	}


}