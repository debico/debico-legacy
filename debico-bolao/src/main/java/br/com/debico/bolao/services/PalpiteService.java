package br.com.debico.bolao.services;

import br.com.debico.bolao.PalpiteExistenteException;
import br.com.debico.bolao.PalpiteForaPrazoException;
import br.com.debico.core.DebicoException;
import br.com.debico.model.Palpite;
import br.com.debico.model.PalpiteLite;
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
     * Todos os elementos do Palpite devem estar definidos, a engine não faz a
     * busca dos elemenentos faltantes.
     * <p/>
     * Caso queira utilizar um objeto simplificado, utilizar
     * {@link #palpitar(PalpiteLite)}.
     * 
     * @see #palpitar(PalpiteLite)
     */
    void palpitar(final Palpite palpite, final CampeonatoImpl campeonato)
            throws DebicoException;

    /**
     * Realiza o palpite do apostador.
     * 
     * @param palpite
     *            instância do palpite relacionado com o Apostador e a Partida
     *            em questão.
     * @return {@link PalpiteIO} instânciado.
     * @throws PalpiteForaPrazoException
     *             caso o palpite esteja fora do prazo determinado.
     * @throws PalpiteExistenteException
     *             caso o palpite seja existente para o apostador.
     */
    PalpiteLite palpitar(final PalpiteLite palpiteIO,
            final CampeonatoImpl campeonato) throws DebicoException;

}
