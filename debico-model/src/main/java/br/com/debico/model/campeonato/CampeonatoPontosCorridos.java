package br.com.debico.model.campeonato;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(Campeonato.PONTOS_CORRIDOS)
public class CampeonatoPontosCorridos extends CampeonatoImpl {

    private static final long serialVersionUID = 1464814442708908927L;

    public CampeonatoPontosCorridos() {

    }

    public CampeonatoPontosCorridos(final String nome) {
        super(nome);
    }

}
