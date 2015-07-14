package br.com.debico.resultados;

import java.util.Collections;
import java.util.List;

public class DefaultProcessorPipeline implements ProcessorPipeline {

    private final List<Processor> chain;

    public DefaultProcessorPipeline() {
        this.chain = Collections.emptyList();
    }

    @Override
    public void addProcessor(Processor processor) {
        this.chain.add(processor);
    }

    @Override
    public void doProcess(Context context) {
        for (Processor processor : chain) {
            if(!processor.execute(context)) {
                break;
            }
        }
    }

}
