package br.com.debico.resultados;

/**
 * Determinar que um serviço deve ser executado em uma determinada agenda.
 * Respeita a definição do Spring Framework para um <code>bean</code> agendado.
 * <p/>
 * Implementações normalmente delegam para um {@link ProcessorManager} interno
 * este processamento.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.4
 */
public interface SchedulerProcessorManager {

    void start();

}
