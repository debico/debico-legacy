package br.com.debico.bolao;

import java.util.Locale;

import org.springframework.context.MessageSource;

import br.com.debico.core.DebicoException;
import br.com.debico.core.MessagesCodes;

/**
 * Lançada quando realizada uma tentativa de palpite fora do prazo estipulado.
 * 
 * @author r_fernandes
 * @since 0.1
 * 
 */
public class PalpiteForaPrazoException extends DebicoException {

    private static final long serialVersionUID = 8632448909026902340L;
    
    private int limite;

    public PalpiteForaPrazoException(final MessageSource messageSource, int limite) {
        super(messageSource
                .getMessage(MessagesCodes.PALPITE_FORA_PRAZO, 
                        new Object[] {limite}, 
                        Locale.getDefault()));
        
        this.limite = limite;
    }
    
    public int getLimite() {
        return limite;
    }
}
