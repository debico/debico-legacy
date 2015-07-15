package br.com.debico.resultados.managers;

import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.Processor;
import br.com.debico.resultados.processors.ProcessorSupport;

abstract class CampeonatoProcessorManagerSupport<T extends Campeonato> extends
	ProcessorSupport implements CampeonatoProcessorManager<T> {

    public CampeonatoProcessorManagerSupport() {

    }

    /**
     * Retorna o tipo de Campeonato que este {@link Processor} é capaz de processar.
     * 
     * @return
     */
    protected abstract Class<T> getType();

    /**
     * Atua como um {@link Processor}, por essa razão não cria o contexto. É
     * esperado o campeonato definido no contexto.
     */
    @Override
    public abstract void execute(Context context);
    
    @Override
    public final boolean supports(Campeonato campeonato) {
	return this.getType().isInstance(campeonato);
    }

}
