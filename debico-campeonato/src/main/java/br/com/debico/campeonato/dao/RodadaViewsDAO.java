package br.com.debico.campeonato.dao;

import java.util.List;

import br.com.debico.model.StatusPartida;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.Rodada;

/**
 * Camada de acesso à dados com visões da rodada.
 * 
 * @author ricardozanini
 * @since 2.0.3
 */
public interface RodadaViewsDAO {

    /**
     * Seleciona todas as rodadas que contém ao menos uma partida com status
     * definido (diferente de {@link StatusPartida#ND}).
     * 
     * @param campeonato
     * @return
     */
    List<Rodada> selecionarRodadasProcessadas(Campeonato campeonato);

}
