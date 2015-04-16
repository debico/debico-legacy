package br.com.debico.social.model;

import java.io.Serializable;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

/**
 * Estrutura básica de {@link LigaApostador} que serve para ir e vir entre a API
 * REST. Também pode ser base para a criação da entidade.
 * 
 * @author ricardozanini
 * @since 2.0.0
 */
public class LigaApostadorLite implements Serializable {

	private static final long serialVersionUID = -5933649497829391975L;

	private long idLiga;

	private int idApostador;

	public LigaApostadorLite() {

	}

	public LigaApostadorLite(final long idLiga, final int idApostador) {
		this.idApostador = idApostador;
		this.idLiga = idLiga;
	}

	public long getIdLiga() {
		return idLiga;
	}

	public void setIdLiga(long idLiga) {
		this.idLiga = idLiga;
	}

	public int getIdApostador() {
		return idApostador;
	}

	public void setIdApostador(int idApostador) {
		this.idApostador = idApostador;
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

		LigaApostadorLite that = (LigaApostadorLite) obj;

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
		return toStringHelper(this).addValue(this.idApostador)
				.addValue(this.idLiga).toString();
	}
}