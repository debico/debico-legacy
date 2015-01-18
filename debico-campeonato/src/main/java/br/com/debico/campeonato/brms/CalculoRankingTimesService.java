package br.com.debico.campeonato.brms;

import br.com.debico.model.campeonato.Campeonato;


/**
 * Métodos para definir o status posição do time perante a tabela/grupo desejada. 
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
public interface CalculoRankingTimesService {

    /**
     * Apenas para campeonatos com uma tabela. Para grupos, ainda vamos desenvolver um específico.
     * <p/>
     * O cálculo de pontuação geral utiliza a seguinte norma:
     * 
     * <ol>
     * <li>Pontos</li>
     * <li>Número de Vitórias</li>
     * <li>Saldo de Gols</li>
     * <li>Gols Marcados</li>
     * <li>Nome do clube</li>
     * </ol>
     * 
     * Outros critérios de desempate devem ser definidos em regras específicas da instância do campeonato em questão.
     * 
     * @param campeonato no qual estão sendo calculadas as partidas
     */
    void calcularPosicaoGeral(Campeonato campeonato);
    
}
