package br.com.debico.model.to;

import java.io.Serializable;

import br.com.debico.model.Palpite;
import br.com.debico.model.Placar;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

/**
 * Estrutura simples baseada em {@link Palpite} sem relações com as entidades do
 * JPA.
 * 
 * @author ricardozanini
 *
 */
public class PalpiteTO implements Serializable {

    private static final long serialVersionUID = 1450856284231390125L;

    private int id;
    private String apostadorEmail;
    private Placar placar;
    private int idPartida;
    private int idUsuario;

    public PalpiteTO(final String apostadorEmail, final Placar placar,
            final int idPartida) {
        this.apostadorEmail = apostadorEmail;
        this.idPartida = idPartida;
        this.placar = placar;
    }

    public PalpiteTO(final int idUsuario, final Placar placar,
            final int idPartida) {
        this.idUsuario = idUsuario;
        this.idPartida = idPartida;
        this.placar = placar;
    }

    // JSON
    public PalpiteTO() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Deprecated
    public String getApostadorEmail() {
        return apostadorEmail;
    }

    @Deprecated
    public void setApostadorEmail(String apostadorEmail) {
        this.apostadorEmail = apostadorEmail;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Placar getPlacar() {
        return placar;
    }

    public void setPlacar(Placar placar) {
        this.placar = placar;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.apostadorEmail, this.id, this.idPartida,
                this.placar);
    }

    @Override
    public String toString() {
        return toStringHelper(this).omitNullValues().addValue(this.placar)
                .addValue(this.idPartida).addValue(this.apostadorEmail)
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

        PalpiteTO that = (PalpiteTO) obj;

        return super.equals(obj) && equal(this.id, that.getId())
                && equal(this.placar, that.getPlacar())
                && equal(this.idUsuario, that.getIdUsuario());
    }

}
