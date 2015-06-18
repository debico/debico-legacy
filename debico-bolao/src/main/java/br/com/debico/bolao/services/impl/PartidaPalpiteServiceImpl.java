package br.com.debico.bolao.services.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.dao.PartidaPalpiteViewDAO;
import br.com.debico.bolao.model.view.PartidaRodadaPalpiteView;
import br.com.debico.bolao.services.PartidaPalpiteService;
import br.com.debico.model.Apostador;
import br.com.debico.social.services.ApostadorService;

@Named
@Transactional(readOnly=true)
class PartidaPalpiteServiceImpl implements PartidaPalpiteService {

    @Inject
    private PartidaPalpiteViewDAO partidaPalpiteViewDAO;
    
    @Inject
    private ApostadorService apostadorService;
    
    public PartidaPalpiteServiceImpl() {

    }

    public List<PartidaRodadaPalpiteView> recuperarPalpitesRodada(
            final int idRodada, final String username) {
        checkArgument(idRodada > 0);
        checkNotNull(emptyToNull(username));

        final Apostador apostador = apostadorService.selecionarApostadorPorEmail(username);

        return partidaPalpiteViewDAO.selecionarPartidasComSemPalpite(idRodada,
                apostador.getId());
    }

    public List<PartidaRodadaPalpiteView> recuperarPalpitesRodadaPorOrdinal(
            String permalink, int rodadaOrdinal, String username) {
        checkArgument(rodadaOrdinal > 0);
        checkNotNull(emptyToNull(username));

        final Apostador apostador = apostadorService.selecionarApostadorPorEmail(username);

        return partidaPalpiteViewDAO.selecionarPartidasComSemPalpiteOrdinal(
                permalink, rodadaOrdinal, apostador.getId());
    }

}
