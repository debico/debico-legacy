package br.com.debico.model.campeonato;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue(FaseImpl.TIPO_FASE_UNICA)
public class FaseUnica extends FaseImpl {
    
    private static final String NOME_DEFAULT = "Fase \u00danica";
    
	private static final long serialVersionUID = -3415868662471472829L;
		
	public FaseUnica() {
	    
	}
	
	public FaseUnica(final String nome) {
	    super(nome);
	}
	
	public static FaseUnica novaFaseUnica() {
	    return new FaseUnica(NOME_DEFAULT);
	}
	
	public static FaseUnica novaFaseUnica(CampeonatoImpl campeonato) {
	    FaseUnica fase = novaFaseUnica();
	    fase.setCampeonato(campeonato);
	    
	    return fase;
	}
	

}
