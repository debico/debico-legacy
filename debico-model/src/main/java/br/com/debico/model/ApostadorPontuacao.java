package br.com.debico.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.debico.model.campeonato.CampeonatoImpl;

@Entity
@Table(name = "tb_apostador_campeonato")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ApostadorPontuacao extends AbstractApostadorPontuacao {
    private static final long serialVersionUID = 5940431998506054906L;
    
    public ApostadorPontuacao(Apostador apostador, CampeonatoImpl campeonato) {
        super(apostador, campeonato);
    }

    public ApostadorPontuacao(Apostador apostador) {
        super(apostador);
    }

    public ApostadorPontuacao() {
        super();
    }
    
}
