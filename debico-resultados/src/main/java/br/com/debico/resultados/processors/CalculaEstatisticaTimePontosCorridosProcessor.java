package br.com.debico.resultados.processors;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.dao.PontuacaoTimeDAO;
import br.com.debico.model.PartidaBase;
import br.com.debico.model.Time;
import br.com.debico.model.campeonato.PontuacaoTime;
import br.com.debico.resultados.Context;

/**
 * Processador para calcular as estatísticas gerais (pontos, números de gols,
 * jogos, etc) de um Time em um campeonato que contem Ranking (pontos corridos,
 * fase de grupos etc.).
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
@Named
@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
public final class CalculaEstatisticaTimePontosCorridosProcessor extends
	BRMSProcessor {

    private static final String AGENDA_GROUP_ESTATISTICA_TIME = "calcula_pontos_campeonato_pc";
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(CalculaEstatisticaTimePontosCorridosProcessor.class);

    @Inject
    private PontuacaoTimeDAO pontuacaoTimeDAO;

    public CalculaEstatisticaTimePontosCorridosProcessor() {

    }

    @Override
    public String getAgendaRule() {
	return AGENDA_GROUP_ESTATISTICA_TIME;
    }

    @Override
    protected Collection<?>[] selecionarFatos(Context context) {
	if (context.getPartidas().isEmpty()) {
	    return new Collection<?>[] {};
	}

	LOGGER.debug("[selecionarFatos] Selecionando o ranking para os times das partidas selecionadas.");

	List<PontuacaoTime> ranking = pontuacaoTimeDAO
		.selecionarPontuacaoTimes(
			this.recuperarTimes(context.getPartidas()),
			context.getCampeonato());

	LOGGER.debug("[selecionarFatos] '{}' itens de pontuacao selecionados.",
		ranking.size());

	return new Collection<?>[] { context.getPartidas(), ranking };
    }

    /**
     * Recupera uma lista de times a partir das partidas informadas.
     * 
     * @param partidas
     * @return
     */
    private Set<Time> recuperarTimes(final List<? extends PartidaBase> partidas) {
	Set<Time> times = new HashSet<Time>();

	for (PartidaBase p : partidas) {
	    times.add(p.getMandante());
	    times.add(p.getVisitante());
	}

	return times;
    }

}
