package br.com.debico.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

@Entity
@Table(name="tb_placar")
public final class Placar implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -8691322184599718989L;
    
    @Column(name="NU_GOLS_MANDANTE", nullable=false)
    private int golsMandante;
    
    @Column(name="NU_GOLS_VISITANTE", nullable=false)
    private int golsVisitante;
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID_PLACAR")
    private int id;

    public Placar() {
        this.golsMandante = 0;
        this.golsVisitante = 0;
    }
    
    public Placar(final int golsMandante, final int golsVisitante) {
        this.golsMandante = golsMandante;
        this.golsVisitante = golsVisitante;
    }
    
    public Placar(final int id, final int golsMandante, final int golsVisitante) {
    	this(golsMandante, golsVisitante);
    	this.id = id;
    }

    public int getGolsMandante() {
        return golsMandante;
    }

    public int getGolsVisitante() {
        return golsVisitante;
    }

    public void setGolsMandante(int golsMandante) {
        this.golsMandante = golsMandante;
    }

    public void setGolsVisitante(int golsVisitante) {
        this.golsVisitante = golsVisitante;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }


    @Override
    public String toString() {
        return toStringHelper(this).addValue(this.golsMandante).addValue("x").addValue(this.golsVisitante).toString();
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

        Placar that = (Placar) obj;

        return  equal(this.id, that.getId())
                && equal(this.golsMandante, that.getGolsMandante()) 
                && equal(this.golsVisitante, that.getGolsVisitante());
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.golsMandante, this.golsVisitante);
    }

}
