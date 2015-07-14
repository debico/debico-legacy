package br.com.debico.resultados.processors;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.brms.CalculoPalpitesService;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.ProcessorBeans;

@ActiveProfiles({"dev", "embedded-jpa"})
@Named(ProcessorBeans.COMPUTA_PALPITES)
@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
class ComputarPalpitesProcessor extends ProcessorSupport {

    @Inject
    private CalculoPalpitesService calculoPalpitesService;
    
    public ComputarPalpitesProcessor() {
	
    }

    @Override
    public void execute(Context context) {
	calculoPalpitesService.computarPalpites(context.getCampeonato(), context.getPartidas());
	this.executeNext(context);
    }

}
