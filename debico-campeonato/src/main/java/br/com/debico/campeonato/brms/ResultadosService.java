package br.com.debico.campeonato.brms;

import java.util.List;

import br.com.debico.model.PartidaChave;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.Chave;
import br.com.debico.model.campeonato.Rodada;

/**
 * Ponto central da API, responsável por processar os resultados das partidas.
 * </p> <s>O processamento dos resultados é feito de forma assíncrona, por tanto
 * o cliente deve utilizar de outro meio para olhar para a resposta.</s>
 * 
 * @author r_fernandes
 * @since 0.1
 */
public interface ResultadosService<C extends Campeonato> {

	/**
	 * Processa as partidas com placar, sem definição de status e ainda não
	 * computadas de acordo com a {@link Rodada} específica.
	 * 
	 * @param rodada
	 *            relacionada com essas partidas.
	 * @param campeonato
	 *            em questão
	 * @return partidas processadas
	 */
	List<PartidaRodada> processar(C campeonato, Rodada rodada);

	/**
	 * O mesmo que {@link #processar(Rodada)}, mas levando em conta a
	 * {@link Chave} em questão.
	 * 
	 * @param chave
	 *            relacionada com as partidas.
	 * @param campeonato
	 *            em questão
	 * @return partidas processadas
	 */
	List<PartidaChave> processar(C campeonato, Chave chave);

	/**
	 * Processa todas as rodadas que ainda não foram calculadas do campeonato
	 * desejado.
	 * 
	 * @param campeonato
	 * @return partidas processadas.
	 * @since 2.0.0
	 */
	List<PartidaRodada> processar(C campeonato);

}
