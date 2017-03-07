package br.com.debico.api.bolao.domain;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

@MappedSuperclass
public abstract class PalpiteBase implements Serializable {

    private static final long serialVersionUID = 5049834497211030222L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_APOSTADOR", nullable = false, unique = false)
    private Apostador apostador;

    // http://stackoverflow.com/questions/13027214/jpa-manytoone-with-cascadetype-all
    @ManyToOne(optional = false, cascade = { (CascadeType.ALL) })
    @JoinColumn(name = "ID_PLACAR", nullable = false, unique = false)
    private Placar placar;

    @Column(name = "IN_COMPUTADO", nullable = false)
    private boolean computado;

    public PalpiteBase(final Apostador apostador, final Placar placar) {
        this();
        checkNotNull(apostador);

        this.placar = placar;
        this.apostador = apostador;
        this.computado = false;
    }

    public PalpiteBase() {

    }

    public Placar getPlacar() {
        return this.placar;
    }

    public void setPlacar(Placar placar) {
        this.placar = placar;
    }

    public Apostador getApostador() {
        return apostador;
    }

    public void setApostador(Apostador apostador) {
        this.apostador = apostador;
    }

    public boolean isComputado() {
        return computado;
    }

    public void setComputado(boolean computado) {
        this.computado = computado;
    }

    protected ToStringHelper toStringHelper() {
        return Objects.toStringHelper(this).add("computado", this.computado).add("placar", this.placar);
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.apostador, this.computado, this.placar);
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

        Palpite that = (Palpite) obj;

        return equal(this.apostador, that.getApostador()) && equal(this.computado, that.isComputado())
                && equal(this.placar, that.getPlacar());
    }

}
