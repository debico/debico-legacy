package br.com.debico.social.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.com.debico.model.Apostador;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

/**
 * Entidade de associação entre {@link Liga} e {@link Apostador}.
 * 
 * @author ricardozanini
 * @since 2.0.0
 */
@Entity
@Table(name = "TB_LIGA_APOSTADOR")
public final class LigaApostador implements Serializable {

	private static final long serialVersionUID = -7413492928518433770L;

	@Id
	private int idApostador;

	@Id
	private long idLiga;

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "ID_APOSTADOR", referencedColumnName = "ID_APOSTADOR")
	private Apostador apostador;

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "ID_LIGA", referencedColumnName = "ID_LIGA")
	private Liga liga;

	public LigaApostador() {

	}
	
	public LigaApostador(final int idApostador, final long idLiga) {
		this.idApostador = idApostador;
		this.idLiga = idLiga;
	}

	public int getIdApostador() {
		return idApostador;
	}

	public void setIdApostador(int idApostador) {
		this.idApostador = idApostador;
	}

	public long getIdLiga() {
		return idLiga;
	}

	public void setIdLiga(long idLiga) {
		this.idLiga = idLiga;
	}

	public Apostador getApostador() {
		return apostador;
	}

	public void setApostador(Apostador apostador) {
		this.apostador = apostador;
	}

	public Liga getLiga() {
		return liga;
	}

	public void setLiga(Liga liga) {
		this.liga = liga;
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

		LigaApostador that = (LigaApostador) obj;

		// @formatter:off
		return equal(this.idApostador, that.getIdApostador())
				&& equal(this.idLiga, that.getIdLiga());
		// @formatter:on
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.idApostador, this.idLiga);
	}

	@Override
	public String toString() {
		return toStringHelper(this).add("Apostador", this.idApostador)
				.add("Liga", this.idLiga).toString();
	}
}
