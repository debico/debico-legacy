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
     * Inicia o processamento com base na lógica interna. É responsável por
     * decidir como começar e criar o contexto de processamento.
     * 
     * @return a lista de contextos processados.
     */
    List<Context> start();

    /**
     * Inicia o processamento com base nos parâmetros de &lt;T&gt;
     * 
     * @param parameter
     *            o parâmetro utilizado para iniciar o processamento
     * @return a lista de contextos processados.
     */
    List<Context> start(T parameter);

    /**
     * Inicializa o manager.
     */
    void init();

}
