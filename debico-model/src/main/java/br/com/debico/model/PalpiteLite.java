package br.com.debico.model;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

import java.io.Serializable;

import com.google.common.base.Objects;

/**
 * Estrutura simples baseada em {@link Palpite} sem relações com as entidades do
 * JPA.
 * 
 * @author ricardozanini
 *
 */
public class PalpiteLite implements Serializable {

	private static final long serialVersionUID = 1450856284231390125L;

	private int id;
	private String apostadorEmail;
	private Placar placar;
	private int idPartida;

	public PalpiteLite(final String apostadorEmail, final Placar placar,
			final int idPartida) {
		this.apostadorEmail = apostadorEmail;
		this.idPartida = idPartida;
		this.placar = placar;
	}

	// JSON
	public PalpiteLite() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApostadorEmail() {
		return apostadorEmail;
	}

	public void setApostadorEmail(String apostadorEmail) {
		this.apostadorEmail = apostadorEmail;
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

		PalpiteLite that = (PalpiteLite) obj;

		return super.equals(obj) && equal(this.id, that.getId())
				&& equal(this.placar, that.getPlacar())
				&& equal(this.apostadorEmail, that.getApostadorEmail());
	}

}
