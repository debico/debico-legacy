package br.com.debico.campeonato.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.PartidaService;
import br.com.debico.campeonato.dao.PartidaDAO;
import br.com.debico.core.helpers.CacheKeys;
import br.com.debico.model.Partida;
import br.com.debico.model.Placar;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Transactional(readOnly = false)
class PartidaServiceImpl implements PartidaService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PartidaServiceImpl.class);
    
	@Inject
    private PartidaDAO partidaDAO;

	@CacheEvict(value = CacheKeys.PARTIDAS_RODADA, allEntries = true)
    public Partida salvarPlacar(final int idPartida, final Placar placar) {
        checkArgument(idPartida > 0, "O id da partida eh invalido.");
        checkNotNull(placar, "O placar nao pode ser nulo.");
        
        LOGGER.debug("[salvarPlacar] Tentando salvar o placar {} da partida {}", idPartida, placar);
        
        Partida partida = partidaDAO.selecionarPorId(idPartida);
        partida.setPlacar(placar);
        
        return partidaDAO.atualizar(partida);
    }

}
