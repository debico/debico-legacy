package br.com.debico.model.campeonato;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

/**
 * Criada para satisfazer as condições do modelo de mapeamento de entidade do
 * JPA. O domínio poderia existir apenas com {@link RodadaMeta} e {@link Rodada}
 * 
 * @author ricardozanini
 * @since 2.0.4
 * @see <a href="http://docs.oracle.com/javaee/6/tutorial/doc/bnbqn.html">Entity Inheritance</a>
 *
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractRodada implements Serializable {

    private static final long serialVersionUID = 294270302408022547L;

    public static final String NOME_RODADA_FORMAT = "Rodada %s";

    protected static final String NOME_DEFAULT = "Rodada \u00danica";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RODADA")
    private int id;

    @Column(name = "NM_RODADA", length = 255)
    private String nome;

    public AbstractRodada() {
        this.nome = "";
    }

    public AbstractRodada(final int id, final String nome) {
        this();
        this.id = id;
        this.nome = nome;
    }

    public AbstractRodada(final int id) {
        super();
        this.id = id;
    }

    public AbstractRodada(final String nome) {
        this();
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id, this.nome);
    }

    @Override
    public String toString() {
        return toStringHelper(this).addValue(this.id).addValue(this.nome)
                .toString();
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

        AbstractRodada that = (AbstractRodada) obj;

        return equal(this.id, that.getId());
    }

}
