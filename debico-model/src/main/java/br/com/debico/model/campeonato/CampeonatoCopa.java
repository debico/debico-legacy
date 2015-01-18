package br.com.debico.model.campeonato;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(Campeonato.COPA)
public class CampeonatoCopa extends CampeonatoImpl {

	private static final long serialVersionUID = 618160278489812708L;

}
