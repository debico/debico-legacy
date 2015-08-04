package br.com.debico.bolao.services.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.dao.PartidaPalpiteViewDAO;
import br.com.debico.bolao.model.view.PartidaRodadaPalpiteView;
import br.com.debico.bolao.services.PartidaPalpiteService;
import br.com.debico.model.Apostador;
import br.com.debico.social.services.ApostadorService;

import static com.google.common.base.Preconditions.checkArgument;

@Named
@Transactional(readOnly = true)
class PartidaPalpiteServiceImpl implements PartidaPalpiteService {

    @Inject
    private PartidaPalpiteViewDAO partidaPalpiteViewDAO;

    @Inject
    private ApostadorService apostadorService;

    public PartidaPalpiteServiceImpl() {

    }

    public List<PartidaRodadaPalpiteView> recuperarPalpitesRodada(
            final int idRodada, final int idUsuario) {
        checkArgument(idRodada > 0);
        checkArgument(idUsuario > 0, "o Id de usuario nao pode ser nulo");

        final Apostador apostador = apostadorService
                .selecionarApostadorPorIdUsuario(idUsuario);

        return partidaPalpiteViewDAO.selecionarPartidasComSemPalpite(idRodada,
                apostador.getId());
    }

    public List<PartidaRodadaPalpiteView> recuperarPalpitesRodadaPorOrdinal(
            String permalink, int rodadaOrdinal, int idUsuario) {
        checkArgument(rodadaOrdinal > 0);
        checkArgument(idUsuario > 0, "o Id de usuario nao pode ser nulo");

        final Apostador apostador = apostadorService
                .selecionarApostadorPorIdUsuario(idUsuario);

        return partidaPalpiteViewDAO.selecionarPartidasComSemPalpiteOrdinal(
                permalink, rodadaOrdinal, apostador.getId());
    }

}
