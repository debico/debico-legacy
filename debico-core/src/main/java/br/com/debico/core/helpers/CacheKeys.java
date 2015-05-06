package br.com.debico.core.helpers;

/**
 * Constantes com os nomes das chave de cache. Não utilizar Strings declaradas
 * nas classes, utilizar esse mecanismo.
 * 
 * @author ricardozanini
 * @since 1.2.0
 */
public final class CacheKeys {

	private CacheKeys() {

	}
	
	/**
	 * Configuração para o ACL
	 */
	public static final String ACL = "aclCache";

	/**
	 * Chave do cache dos Campeonatos.
	 */
	public static final String CAMPEONATOS = "campeonatos";

	/**
	 * Chave do cache dos Campeonatos Ativos.
	 */
	public static final String CAMPEONATOS_ATIVOS = "campeonatosAtivos";

	/**
	 * Chave do cache do ranking atual dos campeonatos. Só deve expirar após o
	 * cálculo dos pontos.
	 */
	public static final String TABELA_CAMPEONATO = "tabelaCampeonato";

	/**
	 * Chave do cache do ranking atual dos apostadores. Só deve expirar após o
	 * cálculo dos pontos.
	 */
	public static final String RANKING_APOSTADORES = "rankingApostadores";

	/**
	 * Chave do cache das partidas por rodada. Só deve expirar após salvar uma
	 * partida.
	 */
	public static final String PARTIDAS_RODADA = "partidasRodada";

	/**
	 * Chave do cache do desempenho individual do apostador. Só deve expirar
	 * após o cálculo dos pontos.
	 */
	public static final String DESEMPENHO_IND_APOSTADOR = "desempenhoApostador";
	
	/**
	 * Chave do cache das ligas do apostador. Expira quando uma nova liga desse admin é adicionada.
	 */
	public static final String MINHAS_LIGAS = "minhasLigas";

	public static String[] recuperarTodas() {
		return new String[] { CAMPEONATOS, CAMPEONATOS_ATIVOS, PARTIDAS_RODADA,
				RANKING_APOSTADORES, TABELA_CAMPEONATO,
				DESEMPENHO_IND_APOSTADOR };
	}
}
