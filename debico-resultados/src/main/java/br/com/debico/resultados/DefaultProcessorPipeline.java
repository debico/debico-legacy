package br.com.debico.resultados;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Deque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.debico.resultados.processors.ProcessorSupport;

import com.google.common.collect.Lists;

/**
 * Implementação padrão com uma lista interna de gerenciamento da cadeia. A cada
 * adição, um próximo elemento na cadeia é adicionado. Na hora da execução, o
 * primeiro adicionado é o primeiro a ser executado (FIFO)
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
public class DefaultProcessorPipeline extends ProcessorSupport implements ProcessorPipeline {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(DefaultProcessorPipeline.class);

    private final Deque<Processor> chain;

    public DefaultProcessorPipeline() {
	this.chain = Lists.newLinkedList();
    }

    @Override
    public void addProcessor(Processor processor) {
	LOGGER.debug("[addProcessor] Tentando adicionar o processor {}",
		processor);
	checkNotNull(processor, "O processor nao pode ser nulo!");

	if (!this.chain.isEmpty()) {
	    this.chain.getLast().setNextProcessor(processor);
	}

	this.chain.addLast(processor);

	LOGGER.debug("[addProcessor] Processor {} adicionado com sucesso!",
		processor);
    }

    @Override
    public void execute(Context context) {
	if (!this.chain.isEmpty()) {
	    this.chain.getFirst().execute(context);
	}
	
	this.executeNext(context);
    }

    @Override
    public String toString() {
	return this.chain.toString();
    }
}
