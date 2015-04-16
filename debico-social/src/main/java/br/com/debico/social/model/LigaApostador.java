package br.com.debico.social.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tb_liga_apostador")
public final class LigaApostador implements Serializable {

	private static final long serialVersionUID = -7413492928518433770L;

	@Id
	@ManyToOne
	@JoinColumn(name="ID_APOSTADOR", updatable = false)
	private Apostador apostador;

	@Id
	@ManyToOne
	@JoinColumn(name="ID_LIGA", updatable = false)
	private Liga liga;

	public LigaApostador() {

	}

	public LigaApostador(final Liga liga, final Apostador apostador) {
		this.apostador = apostador;
		this.liga = liga;
	}
	
	public LigaApostador(final long liga, final int apostador) {
		this.apostador = new Apostador(apostador);
		this.liga = new Liga(liga);
	}
	
	
	public LigaApostador(final LigaApostadorLite ligaApostadorLite) {
		this(ligaApostadorLite.getIdLiga(), ligaApostadorLite.getIdApostador());
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
		return equal(this.apostador, that.getApostador())
				&& equal(this.liga, that.getLiga());
		// @formatter:on
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.apostador, this.liga);
	}

	@Override
	public String toString() {
		return toStringHelper(this).addValue(this.apostador)
				.addValue(this.liga).toString();
	}
}
