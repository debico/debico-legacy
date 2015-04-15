package br.com.debico.social.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.debico.model.Apostador;

@StaticMetamodel(LigaApostador.class)
public class LigaApostador_ {

	public static volatile SingularAttribute<LigaApostador, Apostador> apostador;
	public static volatile SingularAttribute<LigaApostador, Liga> liga;

}
