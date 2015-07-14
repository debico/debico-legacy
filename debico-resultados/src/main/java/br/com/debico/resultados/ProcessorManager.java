package br.com.debico.resultados;

import java.util.List;

/**
 * Responsável por criar um {@link ProcessorPipeline} para a realização de um
 * processamento de resultados específico.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.4
 */
public interface ProcessorManager<T> {
    
    /**
     * Inicia o processamento.
     * 
     * @return
     */
    List<Context> start();
    
    /**
     * Inicia o processamento com base nos parâmetros de &lt;T&gt;
     * @param parameter
     * @return
     */
    List<Context> start(T parameter);
    
    void init();
    
}
