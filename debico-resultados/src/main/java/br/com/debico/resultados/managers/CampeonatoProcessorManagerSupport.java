package br.com.debico.resultados.managers;

import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.resultados.processors.ProcessorSupport;

abstract class CampeonatoProcessorManagerSupport<T extends Campeonato> extends
	ProcessorSupport implements CampeonatoProcessorManager<T> {

    public CampeonatoProcessorManagerSupport() {

    }

    protected abstract Class<T> getType();

    @Override
    public final boolean supports(Campeonato campeonato) {
	return this.getType().isInstance(campeonato);
    }

}
