package br.com.debico.resultados.processors;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.brms.CalculoPalpitesService;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.ProcessorBeans;

@Named(ProcessorBeans.COMPUTA_PALPITES)
@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
class ComputarPalpitesProcessor extends ProcessorSupport {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(ComputarPalpitesProcessor.class);

    @Inject
    private CalculoPalpitesService calculoPalpitesService;

    public ComputarPalpitesProcessor() {

    }

    @Override
    public void execute(Context context) {
	if (context.getPartidas().isEmpty()) {
	    LOGGER.debug("[execute] Nao ha partidas para computar palpites. Cancelando.");
	    return;
	}

	calculoPalpitesService.computarPalpites(context.getCampeonato(),
		context.getPartidas());
	this.executeNext(context);
    }

}
