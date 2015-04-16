package br.com.debico.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import br.com.debico.model.campeonato.CampeonatoImpl;

@StaticMetamodel(ApostadorPontuacao.class)
public class ApostadorPontuacao_ {

    public static volatile SingularAttribute<ApostadorPontuacao, Apostador> apostador;
    public static volatile SingularAttribute<ApostadorPontuacao, CampeonatoImpl> campeonato;
    public static volatile SingularAttribute<ApostadorPontuacao, Integer> pontosTotal;
    public static volatile SingularAttribute<ApostadorPontuacao, Integer> placar;
    public static volatile SingularAttribute<ApostadorPontuacao, Integer> vencedor;
    public static volatile SingularAttribute<ApostadorPontuacao, Integer> gols;
    public static volatile SingularAttribute<ApostadorPontuacao, Integer> empates;
    public static volatile SingularAttribute<ApostadorPontuacao, Integer> errados;
    

}
