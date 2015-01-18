package br.com.debico.campeonato;

import java.util.List;

import br.com.debico.model.PartidaRodada;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.model.campeonato.PontuacaoTime;
import br.com.debico.model.campeonato.Rodada;

/**
 * Realizações de casos de uso para campeonatos de pontos corridos.
 * <p/>
 * Na teoria funciona para qualquer campeonato de pontos corridos do planeta:
 * Italiano, Inglês, Francês, Brasileiro (série A e B) e etc. Na prática, é preciso validar.
 * 
 * @author r_fernandes
 * @since 0.1
 */
public interface CampeonatoPontosCorridosService extends CampeonatoService {
    
    /**
     * Quantidade de times que podem disputar um campeonato de pontos corridos.
     */
    public static final int QUANTIDADE_TIMES_PADRAO = 20;
    
    /**
     * Quantidade de rodadas em um campeonato de pontos corridos.
     */
    public static final int QUANTIDADE_RODADAS_PADRAO = 38;
    
    /**
     * Define a fase única para um campeonato de pontos corridos já criado e com os times definidos.
     * Realizações:
     * 
     * <ol>
     *  <li>Define uma Fase Única.</li>
     *  <li>Associa uma Tabela com os {@value #QUANTIDADE_TIMES_PADRAO} times do campeonato na fase única</li>
     *  <li>Cria as {@value #QUANTIDADE_RODADAS_PADRAO} rodadas.</li>
     *  <li>Cria todas as partidas possíveis e as associa na primeira rodada.</li>
     * </ol>
     * 
     * O administrador deve complementar os dados da partida (data e local) e a rodada correta de acordo
     * com as regras da entidade de futebol local.
     * 
     * @param campeonatoPontosCorridos campeonato já definido.
     * @see #QUANTIDADE_RODADAS_PADRAO
     * @see #QUANTIDADE_TIMES_PADRAO
     */
    void definirFaseUnica(CampeonatoPontosCorridos campeonatoPontosCorridos);
    
    /**
     * Recupera a pontuação de cada time para montar a TABELA do campeonato. 
     * <p />
     * No caso de Campeonato de pontos corridos, não é necessário recuperar o Ranking/Tabela/Grupo
     * já que é apenas uma única tabela com o ranking. 
     * <p />
     * Os dados já vem ordenados de acordo com as regras gerais de pontuação do campeonato.
     * 
     * @param campeonatoPontosCorridos
     * @see PontuacaoTime#compareTo(PontuacaoTime)
     * @return
     */
    List<PontuacaoTime> selecionarTabela(CampeonatoPontosCorridos campeonatoPontosCorridos);
    
    /**
     * Efetua a listagem de todas as partidas da rodada especificada pelo identificador.
     * 
     */
    List<PartidaRodada> selecionarPartidasRodada(int idRodada);
    
    /**
     * Realiza a listagem de todas as partidas de determinada rodada com o ordinal e campeonato especificado.
     * 
     * @param ordinalRodada sequencial da {@link Rodada}. Ex: 1, 2, 3, 4...n
     * @param campeonato
     * @return
     * @see Rodada#getOrdem()
     */
    List<PartidaRodada> selecionarPartidasRodada(int ordinalRodada, String campeonatoPermalink);
    
    /**
     * Seleciona a rodada atual.
     * 
     * @param campeonatoPontosCorridos o campeonato desejado.
     * @return
     */
    Rodada selecionarRodadaAtual(CampeonatoPontosCorridos campeonatoPontosCorridos);
    
    /**
     * Seleciona a última rodada do campeonato.
     * 
     * @param campeonatoPontosCorridos
     * @return
     * @since 1.1.0
     */
    Rodada selecionarUltimaRodada(CampeonatoPontosCorridos campeonatoPontosCorridos);

}
