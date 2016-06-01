package br.com.debico.resultados.processors;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.debico.bolao.batch.SumarizadorPontuacaoApostadorRodada;
import br.com.debico.core.DebicoException;
import br.com.debico.model.campeonato.AbstractRodada;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.ProcessorBeans;

@Named(ProcessorBeans.SUMARIZA_PONTOS_APOSTADOR_RODADA)
class SumarizaEstatisticaRodadaApostadorProcessor extends ProcessorSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(SumarizaEstatisticaRodadaApostadorProcessor.class);

	@Inject
	private SumarizadorPontuacaoApostadorRodada sumarizadorPontuacaoApostadorRodada;

	public SumarizaEstatisticaRodadaApostadorProcessor() {

	}

	// executamos de forma assíncrona para não transacionar
	@Override
	public void execute(Context context) throws DebicoException {
		checkNotNull(context.getCampeonato(), "Campeonato deve ser especificado");
		LOGGER.debug("[execute] Iniciando a sumarizacao");
		final long start = System.currentTimeMillis();
		final Future<List<AbstractRodada>> pontuacao = sumarizadorPontuacaoApostadorRodada
				.sumarizarAsync(context.getCampeonato(), context.getRodadas());
		// aguardando

		LOGGER.debug("[execute] Aguardando a resposta do JOB");
		while (!pontuacao.isDone()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				LOGGER.error("[execute] Erro na thread de execucao da Sumarizacao.", e);
			}
		}
		LOGGER.debug("[execute] Fim do processamento em: {}", System.currentTimeMillis() - start);

		this.executeNext(context);
	}

}
