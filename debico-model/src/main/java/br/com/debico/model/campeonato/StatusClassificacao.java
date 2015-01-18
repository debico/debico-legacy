package br.com.debico.model.campeonato;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

/**
 * Determina o status da classificação de um time dentro de {@link PontuacaoTime}. 
 * 
 * @author ricardozanini
 *
 */
@Embeddable
public class StatusClassificacao implements Serializable {

	private static final long serialVersionUID = -8183171732403350049L;

	@Column(name="IN_ELITE", nullable = true)
	private boolean elite;
	
	@Column(name="IN_INFERIOR", nullable = true)
	private boolean inferior;
	
	public StatusClassificacao() {
		
	}
	
	public StatusClassificacao(boolean elite, boolean inferior) {
		this.setElite(elite);
		this.setInferior(inferior);
	}
	
	public final boolean isElite() {
		return elite;
	}
	
	public final boolean isInferior() {
		return inferior;
	}
	
	public final void setElite(boolean elite) {
		this.elite = elite;
	}
	
	public final void setInferior(boolean inferior) {
		this.inferior = inferior;
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

		StatusClassificacao that = (StatusClassificacao) obj;

		return equal(this.elite, that.isElite())
				&& equal(this.inferior, that.isInferior());
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(this.elite, this.inferior);
	}
	
	@Override
	public String toString() {
		return toStringHelper(this)
				.add("Elite", this.isElite())
				.add("Inferior", this.isInferior())
				.toString();
	}
	
}
