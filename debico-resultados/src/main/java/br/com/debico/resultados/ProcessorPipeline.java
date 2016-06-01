package br.com.debico.resultados;

/**
 * <i>Composite</i> de {@link Processor} para realizar o processamento em forma
 * sequencial.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @see <a href=
 *      "http://www.enterpriseintegrationpatterns.com/PipesAndFilters.html">Pipes
 *      And Filters</a>
 * @since 2.0.4
 */
public interface ProcessorPipeline extends Processor {

	/**
	 * Adiciona um {@link Processor} no pipeline.
	 * 
	 * @param processor
	 */
	void addProcessor(Processor processor);

}
