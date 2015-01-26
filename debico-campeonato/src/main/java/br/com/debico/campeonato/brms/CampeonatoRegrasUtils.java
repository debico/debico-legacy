package br.com.debico.campeonato.brms;

import br.com.debico.model.Partida;
import br.com.debico.model.StatusPartida;
import br.com.debico.model.campeonato.ParametrizacaoCampeonato;
import br.com.debico.model.campeonato.PontuacaoTime;
import br.com.debico.model.campeonato.StatusClassificacao;
import br.com.debico.model.campeonato.StatusPosicao;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Funções utilizadas durante a execução das regras.
 * 
 * @author r_fernandes
 * @since 0.1
 */
public final class CampeonatoRegrasUtils {

	private CampeonatoRegrasUtils() {

	}

	public static final int PONTOS_VENCEDOR_CAMP_PC = 3;
	public static final int PONTOS_EMPATE_CAMP_PC = 1;

	/**
	 * Efetua o cálculo de pontos para uma partida de pontos corridos.
	 * 
	 * @param partida
	 * @param timeA
	 *            o time vencedor quando a partida não terminou em empate.
	 * @param timeB
	 *            o time perdedor quando a partida não terminou em empate.
	 */
	public static void calculaPontosCorridos(final Partida partida,
			final PontuacaoTime timeA, final PontuacaoTime timeB) {
		checkArgument(partida.getStatus() != StatusPartida.ND,
				"O status da partida deve ser definido antes do calculo de pontos.");

		if (partida.getStatus() == StatusPartida.EM) {
			timeA.setEmpates(timeA.getEmpates() + 1);
			timeB.setEmpates(timeB.getEmpates() + 1);
			timeA.setPontos(timeA.getPontos() + PONTOS_EMPATE_CAMP_PC);
			timeB.setPontos(timeB.getPontos() + PONTOS_EMPATE_CAMP_PC);
		} else {
			timeA.setVitorias(timeA.getVitorias() + 1);
			timeB.setDerrotas(timeB.getDerrotas() + 1);
			timeA.setPontos(timeA.getPontos() + PONTOS_VENCEDOR_CAMP_PC);
		}

		calculaEstatistica(partida, timeA, timeB);
	}

	/**
	 * Define o status da posição baseado na posição antiga e atual enviada.
	 * <p/>
	 * 
	 * @see PontuacaoTime#getStatusPosicao()
	 * @param posicaoAtual
	 * @param novaPosicao
	 * @return
	 */
	public static int calculaStatusPosicao(final int posicaoAtual,
			final int novaPosicao) {
		if (posicaoAtual == 0 || novaPosicao == 0) {
			return StatusPosicao.MANTEVE;
		}

		if (posicaoAtual < novaPosicao) {
			return StatusPosicao.DESCEU;
		} else if (posicaoAtual > novaPosicao) {
			return StatusPosicao.SUBIU;
		} else {
			return StatusPosicao.MANTEVE;
		}
	}

	/**
	 * Baseado na posição do time na tabela e na parametrização do seu
	 * campeonato, define o {@link StatusClassificacao}.
	 * 
	 * @param param
	 * @param novaPosicao
	 * @return
	 */
	public static StatusClassificacao calculaStatusClassificacao(
			final ParametrizacaoCampeonato param, final int novaPosicao) {
		// campeões / classificados
		if (novaPosicao <= param.getPosicaoLimiteElite()) {
			return new StatusClassificacao(true, false);
		}

		// Vasco, Palmeiras..
		if (novaPosicao >= param.getPosicaoLimiteInferior()) {
			return new StatusClassificacao(false, true);
		}

		// meio da tabela
		return new StatusClassificacao(false, false);
	}

	/**
	 * Efetua o cálculo da estatística.
	 * 
	 * @param partida
	 * @param timeA
	 * @param timeB
	 */
	private static void calculaEstatistica(final Partida partida,
			final PontuacaoTime timeA, final PontuacaoTime timeB) {
		int golsTimeA = 0;
		int golsTimeB = 0;

		if (partida.getStatus() == StatusPartida.EM
				|| partida.getStatus() == StatusPartida.VM) {
			golsTimeA = partida.getPlacar().getGolsMandante();
			golsTimeB = partida.getPlacar().getGolsVisitante();
		} else if (partida.getStatus() == StatusPartida.VV) {
			golsTimeA = partida.getPlacar().getGolsVisitante();
			golsTimeB = partida.getPlacar().getGolsMandante();
		}

		timeA.setJogos(timeA.getJogos() + 1);
		timeB.setJogos(timeB.getJogos() + 1);

		timeA.setGolsPro(timeA.getGolsPro() + golsTimeA);
		timeB.setGolsPro(timeB.getGolsPro() + golsTimeB);

		timeA.setGolsContra(timeA.getGolsContra() + golsTimeB);
		timeB.setGolsContra(timeB.getGolsContra() + golsTimeA);

		timeA.setSaldoGols(timeA.getSaldoGols() + (golsTimeA - golsTimeB));
		timeB.setSaldoGols(timeB.getSaldoGols() + (golsTimeB - golsTimeA));

		timeA.setAproveitamento(calcularAproveitamento(timeA,
				PONTOS_VENCEDOR_CAMP_PC));
		timeB.setAproveitamento(calcularAproveitamento(timeB,
				PONTOS_VENCEDOR_CAMP_PC));
	}

	/**
	 * Calcula o aproveitamento do time.
	 * 
	 * @param time
	 *            item do ranking de pontuação do time.
	 * @param pontosJogo
	 *            pontos que o campeonato premia o time vencedor.
	 * @return double com o percentual de aproveitamento.
	 * @see <a
	 *      href="https://br.answers.yahoo.com/question/index?qid=20081001121317AAlkQRY">Como
	 *      faço para calcular o aproveitamento de um time no compeonato
	 *      brasileiro de 2008?</a> :D
	 * 
	 */
	private static double calcularAproveitamento(final PontuacaoTime time,
			final int pontosJogo) {
		return (time.getPontos() / ((double) (time.getJogos() * pontosJogo))) * 100;
	}
}
