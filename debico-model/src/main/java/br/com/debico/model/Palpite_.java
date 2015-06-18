package br.com.debico.model;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Palpite.class)
public class Palpite_ {

    public static volatile SingularAttribute<Palpite, Apostador> apostador;
    public static volatile SingularAttribute<Palpite, Boolean> computado;
    public static volatile SingularAttribute<Palpite, Placar> placar;
    public static volatile SingularAttribute<Palpite, AcertosPontuacao> acertos;
    public static volatile SingularAttribute<Palpite, Date> dataComputado;
    public static volatile SingularAttribute<Palpite, Date> dataHoraPalpite;
    public static volatile SingularAttribute<Palpite, Integer> id;
    public static volatile SingularAttribute<Palpite, Partida> partida;
    public static volatile SingularAttribute<Palpite, Integer> pontos;

}
