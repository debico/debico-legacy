package br.com.debico.resultados.processors;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.brms.CalculoRankingTimesService;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.Processor;
import br.com.debico.resultados.ProcessorBeans;

@Named(ProcessorBeans.CALCULA_POSICAO_TIMES)
@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
class CalculaPosicaoTimesRankingProcessor implements Processor {

    @Inject
    private CalculoRankingTimesService calculoRankingTimesService;

    public CalculaPosicaoTimesRankingProcessor() {

    }

    @Override
    public boolean execute(Context context) {
        calculoRankingTimesService
                .calcularPosicaoGeral(context.getCampeonato());
        return true;
    }

}
