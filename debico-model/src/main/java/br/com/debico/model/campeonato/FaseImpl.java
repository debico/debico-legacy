package br.com.debico.model.campeonato;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TP_FASE", discriminatorType = DiscriminatorType.STRING, length = 2)
@Table(name = "tb_fase")
@JsonIgnoreProperties({"campeonato"})
public abstract class FaseImpl implements Fase, Serializable, Comparable<Fase> {

    private static final long serialVersionUID = -2080265782550466598L;
    
    public static final String TIPO_FASE_GRUPOS = "GR";
    public static final String TIPO_FASE_ELIMINATORIAS = "EL";
    public static final String TIPO_FASE_UNICA = "UN";

    @Column(name = "NU_ORDEM", nullable = false)
    private Integer ordem;

    @Column(name = "NM_FASE", length = 500)
    private String nome;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FASE")
    private int id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CAMPEONATO", nullable = false, unique = false)
    private CampeonatoImpl campeonato;

    public FaseImpl() {
        this.ordem = 1;
    }
    
    public FaseImpl(final String nome) {
        this();
        this.nome = nome;
    }

    public Campeonato getCampeonato() {
        return this.campeonato;
    }

    public void setCampeonato(final CampeonatoImpl campeonato) {
        this.campeonato = campeonato;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
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

        FaseImpl that = (FaseImpl) obj;

        return equal(this.id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id, this.nome, this.ordem);
    }

    @Override
    public String toString() {
        return toStringHelper(this)
                .omitNullValues()
                .addValue(this.id)
                .addValue(this.nome)
                .addValue(this.ordem)
                .toString();
    }

    public int compareTo(Fase o) {
        return o.getOrdem().compareTo(this.ordem);
    }

}
