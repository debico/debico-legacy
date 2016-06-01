package br.com.debico.resultados;

import br.com.debico.core.DebicoException;

/**
 * Pequena parte do processamento. Realiza apenas uma tarefa pré-determinada que
 * fará sentido no todo durante o processamento do pipeline. Similar à
 * implementação de Command.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.4
 */
public interface Processor {

    /**
     * Executa o processo no contexto especificado.
     * 
     * @param context
     */
    void execute(Context context) throws DebicoException;

    /**
     * Define o próximo processor na cadeia.
     * 
     * @param processor
     */
    void setNextProcessor(Processor processor);
    
}
