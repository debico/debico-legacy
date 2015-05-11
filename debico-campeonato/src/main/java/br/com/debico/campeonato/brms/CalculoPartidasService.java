package br.com.debico.campeonato.brms;

import java.util.List;

import br.com.debico.model.Partida;
import br.com.debico.model.PartidaChave;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.StatusPartida;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.Chave;
import br.com.debico.model.campeonato.Rodada;


/**
 * Lida com o processamento das regras para definir os resultados das partidas.
 * <p />
 * Deve ser executada dentro de um contexto já transacionado.
 * 
 * @author r_fernandes
 * @since 0.1
 */
public interface CalculoPartidasService {
    
    /**
     * Atualiza o status das {@link Partida}s ainda não definidas ({@link StatusPartida#ND}), mas com placar.
     * 
     * @param rodada desejada.
     * @return partidas processadas.
     */
    List<PartidaRodada> definirStatusPartidas(Rodada rodada);
    
    /**
     * @see #definirStatusPartidas(Rodada)
     * @param chave desejada.
     * @return partidas processadas.
     */
    List<PartidaChave> definirStatusPartidas(Chave chave);
    
    /**
     * Atualiza a pontuação dos times de acordo com as partidas realizadas.
     * 
     * @param partidas partidas com placar e status já definidos.
     * @param agendaGroup nome da <code>agenda</code> utilizada pelo motor para processar as regras de cálculo.
     * @see CalculoPartidasService#definirStatusPartidas()
     */
    void calcularPontuacaoTimes(Campeonato campeonato, List<? extends Partida> partidas, String agendaGroup);

}
