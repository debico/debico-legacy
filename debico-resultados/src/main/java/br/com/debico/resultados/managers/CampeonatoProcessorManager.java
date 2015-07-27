package br.com.debico.resultados.managers;

import java.util.List;

import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.Processor;
import br.com.debico.resultados.ParameterizeProcessorManager;

/**
 * Marked Interface para definir processadores responsáveis por processar tipos
 * de campeonato.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.4
 * @param <T>
 *            extenção de {@link Campeonato} que será processado.
 */
interface CampeonatoProcessorManager<T extends Campeonato> extends
	ParameterizeProcessorManager<T>, Processor {

    @Override
    List<Context> start(T parameter);

    /**
     * Verifica se esta instância é capaz de processar a instância de campeonato
     * em questão.
     * 
     * @param campeonato
     * @return
     */
    boolean supports(Campeonato campeonato);

}
