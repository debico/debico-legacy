package br.com.debico.model.campeonato;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(Chave.TIPO_CHAVE_UNICA)
public class ChaveUnica extends Chave {

	private static final long serialVersionUID = 7300523571929339875L;

	public ChaveUnica(final FaseEliminatoria fase) {
		super(fase);
	}
	
	public ChaveUnica() {
	    
	}

}
