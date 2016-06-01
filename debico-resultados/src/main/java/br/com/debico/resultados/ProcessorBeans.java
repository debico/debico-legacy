package br.com.debico.resultados;

/**
 * Nome dos Beans de {@link Processor}s dados à Factory do Spring.
 * 
 * @author ricardozanini
 *
 */
public final class ProcessorBeans {

    private ProcessorBeans() {
        
    }
    
    public static final String CALCULA_ESTATISTICA_TIME = "calculaEstatisticaTime";
    public static final String CALCULA_POSICAO_TIMES = "calculaPosicaoTimes";
    public static final String DEFINE_STATUS_PARTIDAS = "defineStatusPartidas";
    public static final String COMPUTA_PALPITES = "computaPalpites";
    public static final String SUMARIZA_PONTOS_APOSTADOR_RODADA = "sumarizaPontosApostadorRodada";
    public static final String RECUPERA_PARTIDAS_WEB = "recuperaPartidasWebPontosCorridos";

}
