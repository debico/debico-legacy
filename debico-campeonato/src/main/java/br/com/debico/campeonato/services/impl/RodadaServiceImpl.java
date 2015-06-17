package br.com.debico.campeonato.services.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.CampeonatoDAO;
import br.com.debico.campeonato.dao.RodadaDAO;
import br.com.debico.campeonato.services.RodadaService;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.Rodada;

import com.google.common.base.Optional;

@Named
@Transactional(readOnly = true)
class RodadaServiceImpl implements RodadaService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RodadaServiceImpl.class);

    @Inject
    private RodadaDAO rodadaDAO;

    @Inject
    private CampeonatoDAO campeonatoDAO;

    public RodadaServiceImpl() {

    }

    public List<Rodada> selecionarRodadas(final String campeonatoPermalink) {
        LOGGER.debug(
                "[selecionarRodadas] Tentando selecionar as rodadas do campeonato '{}'",
                campeonatoPermalink);
        return rodadaDAO.selecionarRodadas(campeonatoDAO
                .selecionarPorPermalink(campeonatoPermalink));
    }

    @Override
    public List<Rodada> selecionarRodadasNaoCalculadas(Campeonato campeonato) {
        LOGGER.debug(
                "[selecionarRodadasNaoProcessadas] Tentando selecionar as rodadas do campeonato {}",
                campeonato);
        return rodadaDAO.selecionarRodadasNaoCalculadas(campeonato);
    }

    @Override
    public Optional<Rodada> selecionarRodadaMeta(int idRodada) {
        if (idRodada == 0) {
            return Optional.absent();
        }

        return Optional.of(rodadaDAO.findById(idRodada));
    }

}
