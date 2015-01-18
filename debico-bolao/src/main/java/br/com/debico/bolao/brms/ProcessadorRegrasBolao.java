package br.com.debico.bolao.brms;

import br.com.debico.model.campeonato.Campeonato;

/**
 * Funciona como uma interface para o processamento de todo o resultado do bolão.
 * <p/>
 * Utilizado por clientes com processamento em lote ou que não possuem informações o suficiente para saber o que processar.
 * 
 * @author ricardozanini
 *
 */
public interface ProcessadorRegrasBolao {
	
	/**
	 * Processa o resultado das partidas, o cálculo dos pontos dos times e a pontuação dos apostadores.
	 * 
	 */
	void processarResultados();
	
	/**
	 * Processa para um campeonato em específico
	 * @param campeonato
	 */
	void processarResultados(Campeonato campeonato);
}
