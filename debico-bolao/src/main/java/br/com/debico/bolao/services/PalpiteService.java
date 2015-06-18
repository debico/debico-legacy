package br.com.debico.bolao.services;

import java.util.List;

import br.com.debico.bolao.PalpiteExistenteException;
import br.com.debico.bolao.PalpiteForaPrazoException;
import br.com.debico.core.DebicoException;
import br.com.debico.model.Palpite;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.model.to.PalpiteTO;

public interface PalpiteService {

    /**
     * Limite em milisegundos do prazo do palpite.
     */
    public static final int PRAZO_PALPITE_MILLIS = 900000;

    public static final double PRAZO_PALPITE_HORAS = 0.25;

    public static final double PRAZO_PALPITE_MINUTOS = 15;

    /**
     * Realiza o palpite do apastador.
     * <p/>
     * Todos os elementos do Palpite devem estar definidos, a engine não faz a
     * busca dos elemenentos faltantes.
     * <p/>
     * Caso queira utilizar um objeto simplificado, utilizar
     * {@link #palpitar(PalpiteTO)}.
     * 
     * @see #palpitar(PalpiteTO)
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
    PalpiteTO palpitar(final PalpiteTO palpiteIO,
	    final CampeonatoImpl campeonato) throws DebicoException;

    /**
     * Lista os palpites de determinado campeonato. Cuidado! Pode demorar
     * *muito*.
     * 
     * @param campeonato
     * @return
     */
    List<Palpite> recuperarPalpites(final Campeonato campeonato);

}
