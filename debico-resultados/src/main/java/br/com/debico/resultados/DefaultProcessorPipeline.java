package br.com.debico.resultados;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class DefaultProcessorPipeline implements ProcessorPipeline {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(DefaultProcessorPipeline.class);

    private final List<Processor> chain;

    public DefaultProcessorPipeline() {
	this.chain = Lists.newArrayList();
    }

    @Override
    public void addProcessor(Processor processor) {
	LOGGER.debug("[addProcessor] Tentando adicionar o processor {}",
		processor);
	checkNotNull(processor, "O processor nao pode ser nulo!");
	this.chain.add(processor);
	LOGGER.debug("[addProcessor] Processor {} adicionado com sucesso!",
		processor);
    }

    @Override
    public void doProcess(Context context) {
	for (Processor processor : chain) {
	    LOGGER.debug("[doProcess] Processando {} no contexto {}",
		    processor, context);
	    if (!processor.execute(context)) {
		LOGGER.debug(
			"[doProcess] Processamento cancelado em {} no contexto {}",
			processor, context);
		break;
	    }
	}
    }

}
