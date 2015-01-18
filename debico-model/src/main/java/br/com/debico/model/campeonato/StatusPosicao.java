package br.com.debico.model.campeonato;

/**
 * Constantes que indicam a posição do status do time na tabela.
 * 
 * @see PontuacaoTime#getStatusPosicao()
 * @author ricardozanini
 *
 */
public final class StatusPosicao {

	public static final int SUBIU = 1;
	public static final int DESCEU = -1;
	public static final int MANTEVE = 0;

	private StatusPosicao() {

	}

	/**
	 * Verifica se o valor enviado pertence a alguma constante. Uma espécie de
	 * consistência que um ENUM já faz automaticamente. :/
	 * 
	 * @param status
	 * @return
	 */
	public static boolean validar(final int status) {
		return (status <= SUBIU && status >= DESCEU);
	}

}
