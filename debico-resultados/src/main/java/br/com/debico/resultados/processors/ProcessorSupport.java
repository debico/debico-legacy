package br.com.debico.resultados.processors;

import br.com.debico.resultados.Context;
import br.com.debico.resultados.Processor;

/**
 * Implementação base de um {@link Processor} para lidar com Use Cases simples.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.4
 */
public abstract class ProcessorSupport implements Processor {

    private Processor nextProcessor;

    public ProcessorSupport() {

    }

    @Override
    public final void setNextProcessor(Processor processor) {
	this.nextProcessor = processor;
    }

    protected final Processor getNextProcessor() {
	return this.nextProcessor;
    }

    protected final void executeNext(Context context) {
	if (this.nextProcessor != null) {
	    this.nextProcessor.execute(context);
	}
    }

}
