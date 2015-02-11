package br.com.debico.bolao.brms.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.brms.CalculoPalpitesService;
import br.com.debico.bolao.dao.ApostadorPontuacaoDAO;
import br.com.debico.bolao.dao.PalpiteDAO;
import br.com.debico.core.brms.BRMSExecutor;
import br.com.debico.model.ApostadorPontuacao;
import br.com.debico.model.Palpite;
import br.com.debico.model.Partida;
import br.com.debico.model.campeonato.Campeonato;

@Named
@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
class CalculoPalpitesServiceImpl implements CalculoPalpitesService {

    protected static final Logger LOGGER = LoggerFactory
            .getLogger(CalculoPalpitesServiceImpl.class);

    protected static final String AGENDA_GROUP = "resultado_palpite";

    @Inject
    protected PalpiteDAO palpiteDAO;

    @Inject
    protected ApostadorPontuacaoDAO apostadorPontuacaoDAO;

    @Inject
    @Named("bolaoBrmsExecutor")
    protected BRMSExecutor brmsExecutor;

    public void computarPalpites(final Campeonato campeonato,
            final List<? extends Partida> partidas) {
        LOGGER.debug("[computarPalpites] Selecionando os palpites.");
        List<Palpite> palpites = palpiteDAO.selecionarTodos(partidas);
        LOGGER.debug("[computarPalpites] '{}' palpites selecionados.",
                palpites.size());

        LOGGER.debug("[computarPalpites] Selecionando os apostadores.");
        List<ApostadorPontuacao> apostadores = apostadorPontuacaoDAO
                .selecionarApostadores(campeonato);
        LOGGER.debug("[computarPalpites] '{}' Apostadores selecionados.",
                apostadores.size());

        if (!palpites.isEmpty()) {
            brmsExecutor.processar(AGENDA_GROUP, palpites, partidas,
                    apostadores);
        }
    }

}
