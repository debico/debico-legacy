package br.com.debico.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ApostadorOpcoes.class)
public class ApostadorOpcoes_ {

    public static volatile SingularAttribute<ApostadorOpcoes, Boolean> lembretePalpites;
    public static volatile SingularAttribute<ApostadorOpcoes, Time> timeCoracao;

}
