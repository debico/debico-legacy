package br.com.debico.resultados;

import java.util.List;

/**
 * {@link ProcessorManager} parametrizado.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.4
 * @param <T>
 *            Tipo de parâmetro enviado para a realização do processamento
 */
public interface ParameterizeProcessorManager<T> extends ProcessorManager {

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
