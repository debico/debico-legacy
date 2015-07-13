package br.com.debico.resultados;

/**
 * Implementação do Pattern <i>Chain of Responsibility</i> para realizar o
 * processamento de pequenas instâncias de regras da API de resultados.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.4
 */
public interface ProcessorPipeline {

    /**
     * Adiciona um {@link Processor} no pipeline.
     * 
     * @param processor
     */
    void addProcessor(Processor processor);

    /**
     * Realiza a cadeia de processamento do pipeline no esquema FIFO.
     */
    void doProcess(Context context);

}
