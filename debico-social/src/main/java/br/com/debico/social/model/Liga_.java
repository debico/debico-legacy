package br.com.debico.social.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.debico.model.Apostador;

@StaticMetamodel(Liga.class)
public class Liga_ {

	public static volatile SingularAttribute<Liga, Long> id;
	public static volatile SingularAttribute<Liga, String> nome;
	public static volatile SingularAttribute<Liga, Boolean> ativa;
	public static volatile SingularAttribute<Liga, String> permalink;
	public static volatile SingularAttribute<Liga, Apostador> administrador;

}
