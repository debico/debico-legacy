package br.com.debico.model.campeonato;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.debico.model.Time;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

@Entity
@Table(name = "tb_chave")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TP_CHAVE", discriminatorType = DiscriminatorType.STRING, length = 2)
public abstract class Chave implements Serializable, Comparable<Chave> {

    private static final long serialVersionUID = -3161489302029426918L;
    
    public static final String TIPO_CHAVE_UNICA = "UN";
    public static final String TIPO_CHAVE_IDA_VOLTA = "IV";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CHAVE")
    private int id;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_FASE", nullable = false, unique = false)
    private FaseEliminatoria fase;

    @ManyToOne(optional = true, cascade = { (CascadeType.ALL) })
    @JoinColumn(name = "ID_TIME_VENCEDOR", referencedColumnName = "ID_TIME", nullable = true, unique = false)
    private Time vencedor;

    @Column(name = "NU_ORDEM", nullable = false)
    private Integer ordem;

    public Chave() {
        this.ordem = 1;
    }

    public Chave(final FaseEliminatoria fase) {
        this();
        this.fase = fase;
    }
    
    public int getId() {
		return id;
	}

    public Time getVencedor() {
        return vencedor;
    }

    public FaseEliminatoria getFase() {
        return fase;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setFase(FaseEliminatoria fase) {
        this.fase = fase;
    }

    public void setVencedor(Time vencedor) {
        this.vencedor = vencedor;
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

        Chave that = (Chave) obj;

        return equal(this.id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.fase, this.id, this.vencedor, this.ordem);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
        		.omitNullValues()
        		.addValue(this.ordem)
        		.addValue(this.fase)
        		.addValue(this.vencedor)
        		.toString();
    }

    public int compareTo(Chave o) {
        return o.getOrdem().compareTo(this.ordem);
    }

}
