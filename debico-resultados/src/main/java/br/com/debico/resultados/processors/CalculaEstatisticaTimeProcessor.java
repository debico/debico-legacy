package br.com.debico.resultados.processors;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.brms.CalculoPartidasService;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.Processor;

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
final class CalculaEstatisticaTimeProcessor implements Processor {

    @Inject
    private CalculoPartidasService calculoPartidasService;

    public CalculaEstatisticaTimeProcessor() {

    }

    @Override
    public boolean execute(Context context) {
        calculoPartidasService.calcularPontuacaoTimes(context.getCampeonato(),
                context.getPartidas());
        return true;
    }

}
