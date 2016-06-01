package br.com.debico.resultados.processors;

import br.com.debico.core.DebicoException;

/**
 * Ocorre em caso de erro durante o processamento (execute).
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.5
 */
public class ProcessorException extends DebicoException {

    private static final long serialVersionUID = -7953315974668618490L;

    public ProcessorException() {
    }

    public ProcessorException(String message) {
	super(message);
    }

    public ProcessorException(String message, String messageCode) {
	super(message, messageCode);
    }

    public ProcessorException(Throwable inner, String messageCode) {
	super(inner, messageCode);
    }

    @SuppressWarnings("deprecation")
    public ProcessorException(String message, Throwable inner) {
	super(message, inner);
    }

}
