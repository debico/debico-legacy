package br.com.debico.resultados.processors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.PartidaDAO;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.campeonato.AbstractRodada;
import br.com.debico.resultados.Context;

/**
 * Processador que define o status da partida a partir da(s) rodada(s) no
 * contexto.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
@Named
@Transactional(propagation = Propagation.MANDATORY, readOnly = false)
public final class DefineStatusPartidasProcessor extends BRMSProcessor {

    private static final String AGENDA_GROUP_STATUS_PARTIDA = "calcula_resultado";
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(DefineStatusPartidasProcessor.class);

    public DefineStatusPartidasProcessor() {
    }

    @Inject
    private PartidaDAO partidaDAO;

    public void setPartidaDAO(PartidaDAO partidaDAO) {
	this.partidaDAO = partidaDAO;
    }

    @Override
    public String getAgendaRule() {
	return AGENDA_GROUP_STATUS_PARTIDA;
    }

    @Override
    protected Collection<?>[] selecionarFatos(Context context) {
	if (!context.getPartidas().isEmpty()) {
	    return new Collection<?>[] { context.getPartidas() };
	}

	final List<PartidaRodada> partidas = new ArrayList<PartidaRodada>();

	for (AbstractRodada rodada : context.getRodadas()) {
	    LOGGER.debug(
		    "[selecionarFatos] Selecionando todas as partidas ainda nao definidas com placar na rodada '{}'.",
		    rodada);

	    partidas.addAll(partidaDAO
		    .selecionarPartidasNaoDefinidasComPlacar(rodada));

	    LOGGER.debug("[selecionarFatos] '{}' partidas selecionadas.",
		    partidas.size());
	}
	context.setPartidas(partidas);
	return new Collection<?>[] { partidas };
    }

}
