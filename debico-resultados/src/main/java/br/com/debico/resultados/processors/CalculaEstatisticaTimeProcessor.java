package br.com.debico.resultados.processors;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.brms.CalculoPartidasService;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.ProcessorBeans;

/**
 * Processador para calcular as estatísticas gerais (pontos, números de gols,
 * jogos, etc) de um Time em um campeonato que contem Ranking (pontos corridos,
 * fase de grupos etc.).
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
@Named(ProcessorBeans.CALCULA_ESTATISTICA_TIME)
@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
final class CalculaEstatisticaTimeProcessor extends ProcessorSupport {

    @Inject
    private CalculoPartidasService calculoPartidasService;

    public CalculaEstatisticaTimeProcessor() {

    }

    @Override
    public void execute(Context context) {
	if(context.getPartidas().isEmpty()) {
	    return;
	}
	
        calculoPartidasService.calcularPontuacaoTimes(context.getCampeonato(),
                context.getPartidas());
        
        this.executeNext(context);
    }

}
