package br.com.debico.bolao.batch;

import java.util.List;
import java.util.concurrent.Future;

import br.com.debico.model.campeonato.AbstractRodada;
import br.com.debico.model.campeonato.Campeonato;

/**
 * Realiza a operação de sumarização dos pontos do apostador por rodada.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.4
 */
public interface SumarizadorPontuacaoApostadorRodada {

    /**
     * Performa a operação de sumarização.
     * 
     * @param rodadas
     *            que deverão ser processadas
     * @param campeonato
     *            das rodadas
     */
    void sumarizarSync(Campeonato campeonato,
	    List<? extends AbstractRodada> rodadas);

    /**
     * O mesmo que {@link #sumarizarSync(Campeonato, List)} executando de forma
     * assíncrona.
     * 
     * @param campeonato
     * @param rodadas
     * @return
     */
    Future<List<AbstractRodada>> sumarizarAsync(Campeonato campeonato,
	    List<? extends AbstractRodada> rodadas);

}
