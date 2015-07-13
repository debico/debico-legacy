package br.com.debico.resultados;

/**
 * Pequena parte do processamento. Realiza apenas uma tarefa pré-determinada que
 * fará sentido no todo durante o processamento do pipeline. Similar à
 * implementação de Command.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.4
 */
public interface Processor {

    void execute(Context context);

}
