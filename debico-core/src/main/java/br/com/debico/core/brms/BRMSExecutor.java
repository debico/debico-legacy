package br.com.debico.core.brms;

import java.util.Collection;


/**
 * Realiza as operações na API de BRMS.
 * 
 * @author r_fernandes
 * @since 0.1
 */
public interface BRMSExecutor {

    int processar(String agenda, Collection<?>... fatos);
    
}
