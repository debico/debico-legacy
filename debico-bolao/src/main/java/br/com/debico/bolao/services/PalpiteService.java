package br.com.debico.bolao.services;

import br.com.debico.core.DebicoException;
import br.com.debico.model.Palpite;
import br.com.debico.model.campeonato.CampeonatoImpl;

public interface PalpiteService {
    
    /**
     * Limite em milisegundos do prazo do palpite.
     */
    public static final int PRAZO_PALPITE_MILLIS = 3600000;
    
    public static final int PRAZO_PALPITE_HORAS = 1;
    
    /**
     * Realiza o palpite do apastador.
     * <p/>
     * Todos os elementos do Palpite devem estar definidos, a engine não faz a busca dos elemenentos faltantes.
     * <p/>
     * Caso queira utilizar um objeto simplificado, utilizar {@link #palpitar(PalpiteIO)}.
     * 
     * @see #palpitar(PalpiteIO)
     */
    void palpitar(final Palpite palpite, final CampeonatoImpl campeonato) throws DebicoException;

    

}
