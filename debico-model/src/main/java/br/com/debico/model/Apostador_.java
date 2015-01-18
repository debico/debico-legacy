package br.com.debico.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(Apostador.class)
public class Apostador_ {

    public static volatile SingularAttribute<Apostador, Integer> id;
    public static volatile SingularAttribute<Apostador, String> nome;
    public static volatile SingularAttribute<Apostador, String> apelido;
    public static volatile SingularAttribute<Apostador, ApostadorOpcoes> opcoes;
    public static volatile SingularAttribute<Apostador, Usuario> usuario;

}
