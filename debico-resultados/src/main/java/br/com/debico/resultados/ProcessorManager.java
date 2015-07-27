package br.com.debico.resultados;

import java.util.List;

/**
 * Responsável por criar um {@link ProcessorPipeline} para a realização de um
 * processamento de resultados específico.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.4
 */
public interface ProcessorManager {
    
    /**
     * Inicia o processamento com base na lógica interna. É responsável por
     * decidir como começar e criar o contexto de processamento.
     * 
     * @return a lista de contextos processados.
     */
    List<Context> start();
}
