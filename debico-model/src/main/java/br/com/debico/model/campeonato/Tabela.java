package br.com.debico.model.campeonato;

import static com.google.common.base.Preconditions.checkArgument;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(Ranking.RANKING_TABELA)
public class Tabela extends Ranking {

    private static final long serialVersionUID = -2156562051284377933L;

    public Tabela() {
        
    }
    
    public Tabela(final FaseUnica fase) {
        super(fase);
    }
    
    @Override
    public void setFase(FaseImpl fase) {
        checkArgument(fase instanceof FaseUnica, "Fase nao suportada para um ranking do tipo tabela");
        
        super.setFase(fase);
    }
    
}
