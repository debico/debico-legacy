package br.com.debico.resultados.processors;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.brms.CalculoPartidasService;
import br.com.debico.core.DebicoException;
import br.com.debico.model.campeonato.AbstractRodada;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.ProcessorBeans;

/**
 * Processador que define o status da partida a partir da(s) rodada(s) no
 * contexto.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
@Named(ProcessorBeans.DEFINE_STATUS_PARTIDAS)
@Transactional(propagation = Propagation.MANDATORY, readOnly = false)
final class DefineStatusPartidasProcessor extends ProcessorSupport {

	@Inject
	private CalculoPartidasService calculoPartidasService;

	public DefineStatusPartidasProcessor() {
	}

	@Override
	public void execute(Context context) throws DebicoException {
		for (AbstractRodada rodada : context.getRodadas()) {
			context.addPartidas(calculoPartidasService.definirStatusPartidas(rodada));
		}

		this.executeNext(context);
	}
}
