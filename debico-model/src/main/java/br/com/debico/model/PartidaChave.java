package br.com.debico.model;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.debico.model.campeonato.Chave;

import com.google.common.base.Objects;

// TODO: PartidaChave também vai ser rodada. #fikadica
@Entity
@DiscriminatorValue(Partida.PARTIDA_COM_CHAVE)
public class PartidaChave extends Partida {
    
    private static final long serialVersionUID = -4251482324373454932L;
    
    @ManyToOne(optional = true, cascade = { (CascadeType.ALL) })
    @JoinColumn(nullable = true, name = "ID_CHAVE", referencedColumnName = "ID_CHAVE", unique = false)
    private Chave chave;
    
    public PartidaChave() {
        super();
    }
    
    public PartidaChave(final Time mandante, final Time visitante, final Placar placar) {
        super(mandante, visitante, placar);
    }
    
    public PartidaChave(final Time mandante, final Time visitante) {
        super(mandante, visitante);
    }
    
    public Chave getChave() {
        return chave;
    }
    
    public void setChave(Chave chave) {
        //checkArgument(rodada == null, "Nao eh possivel determinar uma Chave para uma partida que contem Rodada.");
        
        this.chave = chave;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hashCode(this.chave);
    }
    
    @Override
    public String toString() {
        return toStringHelper()
                .addValue(this.chave)
                .toString();
    }
}
