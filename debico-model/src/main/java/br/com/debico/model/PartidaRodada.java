package br.com.debico.model;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.debico.model.campeonato.Rodada;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;

@Entity
@DiscriminatorValue(Partida.PARTIDA_COM_RODADA)
@JsonIgnoreProperties({"rodada", "fase"})
public class PartidaRodada extends Partida {

    private static final long serialVersionUID = -1898091558138019346L;
    
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "ID_RODADA", referencedColumnName = "ID_RODADA", unique = false)
    private Rodada rodada;
    
    public PartidaRodada() {
        super();
    }
    
    /**
     * @see http://stackoverflow.com/questions/10245397/cannot-create-typedquery-for-query-with-more-than-one-return
     * @param id
     * @param local
     * @param dataHora
     * @param mandante
     * @param visitante
     * @param placar
     */
    public PartidaRodada(final int id, final String local, final Date dataHora, final Time mandante, final Time visitante, final Placar placar) {
    	this(mandante, visitante, placar);
    	this.setId(id);
    	this.setLocal(local);
    	this.setDataHoraPartida(dataHora);
    }
    
    public PartidaRodada(final Time mandante, final Time visitante, final Placar placar) {
        super(mandante, visitante, placar);
    }
    
    public PartidaRodada(final Time mandante, final Time visitante) {
        super(mandante, visitante);
    }
    
    public Rodada getRodada() {
        return rodada;
    }
    
    public void setRodada(final Rodada rodada) {       
        this.rodada = rodada;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hashCode(this.rodada);
    }
    
    @Override
    public String toString() {
        return toStringHelper()
                .addValue(this.rodada)
                .addValue(this.getPlacar())
                .addValue(this.getStatus())
                .toString();
    }
}
