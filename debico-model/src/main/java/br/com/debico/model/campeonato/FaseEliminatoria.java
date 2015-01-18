package br.com.debico.model.campeonato;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.google.common.base.Objects;

@Entity
@DiscriminatorValue(FaseImpl.TIPO_FASE_ELIMINATORIAS)
public class FaseEliminatoria extends FaseImpl {

	private static final long serialVersionUID = -1007979106062557062L;

	@OneToOne(optional = true)
	@JoinColumn(name = "ID_PROX_FASE", referencedColumnName = "ID_FASE", nullable = true, unique = false)
	private FaseEliminatoria proximaFase;

	public FaseEliminatoria getProximaFase() {
		return proximaFase;
	}

	public void setProximaFase(final FaseEliminatoria proximaFase) {
		this.proximaFase = proximaFase;
	}
	
	@Transient
	public boolean isFaseFinal() {
		return (this.proximaFase == null);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hashCode(this.proximaFase);
	}

}
