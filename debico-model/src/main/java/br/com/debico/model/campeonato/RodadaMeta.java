package br.com.debico.model.campeonato;

import javax.persistence.Entity;
import javax.persistence.Table;

import static com.google.common.base.Objects.equal;

/**
 * Domínio da Rodada onde apenas o Identificador e o Nome são interessantes para
 * o caso de uso.
 * 
 * @author ricardozanini
 * @since 2.0.4
 */
@Entity
@Table(name = "tb_rodada")
public class RodadaMeta extends AbstractRodada {

    private static final long serialVersionUID = 1334149455925864076L;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
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

        RodadaMeta that = (RodadaMeta) obj;

        return equal(this.getId(), that.getId());
    }

}
