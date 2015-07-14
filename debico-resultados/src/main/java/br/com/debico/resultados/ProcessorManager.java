package br.com.debico.resultados;

/**
 * Responsável por criar um {@link ProcessorPipeline} para a realização de um
 * processamento de resultados específico.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.4
 */
public interface ProcessorManager<T> {
    
    void start();
    
    void start(T parameter);
    
    void init();
    
}
