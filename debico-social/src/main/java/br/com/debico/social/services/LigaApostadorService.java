package br.com.debico.social.services;

import java.util.List;

import br.com.debico.model.Apostador;
import br.com.debico.social.model.Liga;
import br.com.debico.social.model.LigaApostadorLite;

/**
 * Serviços relacionados à relação liga e apostador.
 * 
 * @author ricardozanini
 *
 */
public interface LigaApostadorService {

	/**
	 * Relaciona todos apostadores de determinada liga.
	 * 
	 * @param idLiga
	 * @return lista com apostadores.
	 */
	List<Apostador> consultarApostadores(final long idLiga);

	/**
	 * Inscreve o apostador na liga.
	 * 
	 * @param ligaApostador instância da estrutura base
	 * @return true se realizado com sucesso.
	 */
	boolean inscreverApostador(final LigaApostadorLite ligaApostador);

	/**
	 * Atalho caso o código cliente tenha esses objetos "em mãos".
	 * 
	 * @param liga
	 * @param apostador
	 * @return true se realizado com sucesso.
	 * @see #inscreverApostador(long, int)
	 */
	boolean inscreverApostador(final Liga liga, final Apostador apostador);

	/**
	 * Remove o apostador da liga.
	 */
	boolean removerApostador(final Liga liga, final Apostador apostador);
	
	/**
	 * 
	 * @see #removerApostador(Liga, Apostador)
	 */
	boolean removerApostador(final LigaApostadorLite ligaApostador);
	
	


}
