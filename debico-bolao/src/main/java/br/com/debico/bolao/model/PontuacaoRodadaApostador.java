package br.com.debico.bolao.model;

import static com.google.common.base.Objects.equal;

import java.io.Serializable;

import com.google.common.base.Objects;

/**
 * Estrutura que representa uma linha da série de dados de {@link PontuacaoRodadaApostadorSerie}.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
public class PontuacaoRodadaApostador implements Serializable, Comparable<PontuacaoRodadaApostador> {
    
    private static final long serialVersionUID = -7310292255909229330L;

    private String nomeApostador;
    
    private int idApostador;
    
    private int ordemRodada;
    
    private int pontos;

    public PontuacaoRodadaApostador() {

    }
    
    public PontuacaoRodadaApostador(String nomeApostador, int idRodada, int pontos) {
        this.ordemRodada = idRodada;
        this.nomeApostador = nomeApostador;
        this.pontos = pontos;
    }

    public final String getNomeApostador() {
        return nomeApostador;
    }

    public final void setNomeApostador(String nomeApostador) {
        this.nomeApostador = nomeApostador;
    }

    public final int getOrdemRodada() {
        return ordemRodada;
    }

    public final void setOrdemRodada(int ordemRodada) {
        this.ordemRodada = ordemRodada;
    }

    public final int getPontos() {
        return pontos;
    }

    public final void setPontos(int pontos) {
        this.pontos = pontos;
    }
    
    public final int getIdApostador() {
        return idApostador;
    }

    public final void setIdApostador(int idApostador) {
        this.idApostador = idApostador;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.ordemRodada, this.nomeApostador, this.pontos);
    }
    
    @Override
    public int compareTo(PontuacaoRodadaApostador o) {
        return Integer.compare(ordemRodada, o.getOrdemRodada());
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

        PontuacaoRodadaApostador that = (PontuacaoRodadaApostador) obj;
        
        return equal(this.nomeApostador, that.getNomeApostador())
                && equal(this.ordemRodada, that.getOrdemRodada())
                && equal(this.pontos, that.getPontos());
    }
    

}
