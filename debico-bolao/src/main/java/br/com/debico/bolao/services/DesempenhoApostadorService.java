package br.com.debico.bolao.services;

import java.util.Set;

import br.com.debico.bolao.model.graph.PontuacaoRodadaApostadorSerie;
import br.com.debico.model.campeonato.Campeonato;

/**
 * Serviço para extração de índices de desempenho do Apostador.
 * <p/>
 * Pode ser utilizado em widgets, gráficos, tabelas, etc.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
public interface DesempenhoApostadorService {
    
    /**
     * Retorna apenas a série do desempenho do Apostador em questão.
     * 
     * @param campeonato
     * @param idUsuario
     * @return
     */
    PontuacaoRodadaApostadorSerie recuperarDesempenhoPontuacaoPorRodada(Campeonato campeonato, int idUsuario);
    
    /**
     * Retorna um conjunto de apostadores (Top 10) do campeonato mais o usuário solicitado com a 
     * pontuação atingida rodada a rodada de determiando campeonato.
     * 
     * @param campeonato
     * @param idUsuario
     * @return
     */
    Set<PontuacaoRodadaApostadorSerie> recuperarDesempenhoTop10PontuacaoPorRodada(Campeonato campeonato, int idUsuario);

}
