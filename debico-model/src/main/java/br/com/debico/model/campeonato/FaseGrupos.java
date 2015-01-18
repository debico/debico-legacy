package br.com.debico.model.campeonato;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(FaseImpl.TIPO_FASE_GRUPOS)
public class FaseGrupos extends FaseImpl {

	private static final long serialVersionUID = -6048576544787682509L;
	
	public FaseGrupos() {

	}
	
	public FaseGrupos(final String nome) {
	    super(nome);
	}

}
