package br.com.debico.bolao.batch;

import java.util.List;

import br.com.debico.model.campeonato.AbstractRodada;

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
     */
    void sumarizar(List<? extends AbstractRodada> rodadas);
    
}
