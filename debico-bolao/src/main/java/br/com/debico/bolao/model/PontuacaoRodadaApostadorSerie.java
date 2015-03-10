package br.com.debico.bolao.model;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.com.debico.core.support.ChartSeriesDataSerializer;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;

/**
 * Representa uma série de dados do desempenho de um apostador, rodada a rodada.
 * <p/>
 * Exemplo de plot de dados:
 * 
 * <pre>
 * ------------------
 * | Darth Vadder   |
 * ------------------------------
 * | Rodada         | Pontuação |
 * ------------------------------
 * | 22             | 14        |
 * ------------------------------
 * | 23             | 12        |
 * ------------------------------
 * | 24             | 13        |
 * ------------------------------
 * | 25             | 20        |
 * ------------------------------
 * | 25             | 15        |
 * ------------------------------
 * | 27             | 22        |
 * ------------------------------
 * | 28             | 5         |
 * ------------------------------
 * | 29             | 16        |
 * ------------------------------
 * | 30             | 16        |
 * ------------------------------
 * | 31             | 16        |
 * ------------------------------
 * </pre>
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
public class PontuacaoRodadaApostadorSerie implements Serializable,
        Comparable<PontuacaoRodadaApostadorSerie> {

    private static final long serialVersionUID = 4315464846758153622L;

    private String nomeApostador;
    
    private int idApostador;
    
    /**
     * Key: rodada, Value: pontuação
     */
    private final Map<Integer, Integer> data;

    private PontuacaoRodadaApostadorSerie() {
        this.data = new TreeMap<Integer, Integer>();
    }
    
    public PontuacaoRodadaApostadorSerie(final String nomeApostador, final int idApostador) {
        this();
        this.nomeApostador = nomeApostador;
        this.idApostador = idApostador;
    }
    
    /**
     * Assume que os pontos são do mesmo apostador
     * 
     * @param nomeApostador
     * @param idApostador
     * @param pontos
     */
    public PontuacaoRodadaApostadorSerie(List<PontuacaoRodadaApostador> pontos) {
        this();
        checkNotNull(pontos, "Pontos nao podem ser nulos");
        checkArgument(pontos.size() > 0, "Nao ha pontos na lista!");
        
        this.idApostador = pontos.get(0).getIdApostador();
        this.nomeApostador = pontos.get(0).getNomeApostador();
        this.addAllData(pontos);
    }

    public void addData(int idRodada, int pontos) {
        this.data.put(idRodada, pontos);
    }
    
    public void addData(final PontuacaoRodadaApostador pontuacaoRodadaApostador) {
        this.data.put(pontuacaoRodadaApostador.getOrdemRodada(), pontuacaoRodadaApostador.getPontos());
    }
    
    private void addAllData(final List<PontuacaoRodadaApostador> pontos) {
        for (PontuacaoRodadaApostador p : pontos) {
            this.data.put(p.getOrdemRodada(), p.getPontos());
        }
    }
    
    @JsonSerialize(using=ChartSeriesDataSerializer.class)
    public Map<Integer, Integer> getData() {
        return Collections.unmodifiableMap(this.data);
    }
    
    public String getNomeApostador() {
        return nomeApostador;
    }
    
    public int getIdApostador() {
        return idApostador;
    }
    
    @Override
    public String toString() {
        
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        String template = "\n %4s %-40s %1s (Rodadas Palpitadas)";
        formatter.format(template,
                            this.idApostador,
                            this.nomeApostador,
                            this.data.size());
        
        String line = stringBuilder.toString();
        formatter.close();
        
        return line;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.idApostador);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj.getClass() != getClass()) {
            return false;
        }

        PontuacaoRodadaApostadorSerie that = (PontuacaoRodadaApostadorSerie) obj;
        
        return equal(this.idApostador, that.getIdApostador());
    }
    
    @Override
    public int compareTo(PontuacaoRodadaApostadorSerie o) {
        if (o == null) return -1;
        
        return this.nomeApostador.compareToIgnoreCase(o.getNomeApostador());
    }

}
