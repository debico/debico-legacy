package br.com.debico.model;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.google.common.base.Objects;

/**
 * Define as opções do apostador.
 * 
 * @author ricardozanini
 * @since 1.1.0
 */
@Embeddable
public class ApostadorOpcoes implements Serializable {

    private static final long serialVersionUID = -804840803509682123L;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "IN_LEMBRETE_PALPITE", nullable = true)
    private boolean lembretePalpites;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "ID_TIME", nullable = true)
    private Time timeCoracao;

    public ApostadorOpcoes() {
        // por padrao, vai receber
        this.lembretePalpites = true;
    }

    public boolean isLembretePalpites() {
        return lembretePalpites;
    }

    public void setLembretePalpites(boolean lembretePalpites) {
        this.lembretePalpites = lembretePalpites;
    }

    public Time getTimeCoracao() {
        return timeCoracao;
    }

    public void setTimeCoracao(Time timeCoracao) {
        this.timeCoracao = timeCoracao;
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

        ApostadorOpcoes that = (ApostadorOpcoes) obj;

        return equal(this.lembretePalpites, that.isLembretePalpites());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.lembretePalpites);
    }

    @Override
    public String toString() {
        return toStringHelper(this).add("LembretePalpite", lembretePalpites)
                .toString();
    }

}
