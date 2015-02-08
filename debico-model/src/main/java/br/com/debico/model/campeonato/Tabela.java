package br.com.debico.model.campeonato;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static com.google.common.base.Preconditions.checkArgument;

@Entity
@DiscriminatorValue(Ranking.RANKING_TABELA)
public class Tabela extends Ranking {

    private static final long serialVersionUID = -2156562051284377933L;

    public Tabela() {
        
    }
    
    public Tabela(final FaseUnica fase) {
        super(fase);
    }
    
    public static Tabela novaTabelaUnica(final FaseUnica fase) {
    	return new Tabela(fase);
    }
    
    @Override
    public void setFase(FaseImpl fase) {
        checkArgument(fase instanceof FaseUnica, "Fase nao suportada para um ranking do tipo tabela");
        
        super.setFase(fase);
    }
    
}
