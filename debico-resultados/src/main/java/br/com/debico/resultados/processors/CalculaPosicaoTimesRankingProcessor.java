package br.com.debico.resultados.processors;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.brms.CalculoRankingTimesService;
import br.com.debico.core.DebicoException;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.ProcessorBeans;

@Named(ProcessorBeans.CALCULA_POSICAO_TIMES)
@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
class CalculaPosicaoTimesRankingProcessor extends ProcessorSupport {

	@Inject
	private CalculoRankingTimesService calculoRankingTimesService;

	public CalculaPosicaoTimesRankingProcessor() {

	}

	@Override
	public void execute(Context context) throws DebicoException {
		calculoRankingTimesService.calcularPosicaoGeral(context.getCampeonato());
		this.executeNext(context);
	}

}
