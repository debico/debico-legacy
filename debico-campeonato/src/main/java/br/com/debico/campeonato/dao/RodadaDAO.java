package br.com.debico.campeonato.dao;

import java.util.Date;
import java.util.List;

import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.Rodada;
import br.com.debico.model.campeonato.RodadaMeta;
import br.com.tecnobiz.spring.config.dao.Dao;

public interface RodadaDAO extends Dao<Rodada, Integer> {

    /**
     * Recupera a rodada com base na data de hoje.
     * 
     * @param campeonato
     * @return
     */
    Rodada selecionarRodadaAtual(Campeonato campeonato);

    /**
     * Recupera a rodada com base na data enviada.
     * 
     * @param campeonato
     * @param data
     * @return
     */
    Rodada selecionarRodadaPorData(Campeonato campeonato, Date data);

    /**
     * Seleciona a rodada que está dentro do período desejado.
     * 
     * @param campeonato
     * @param data
     * @return
     */
    Rodada selecionarUltimaRodada(Campeonato campeonato);

    /**
     * Selecionar todas as rodadas de determinado campeonato.
     * <p/>
     * Não busca por relacionamentos.
     * 
     * @param campeonato
     * @return
     */
    List<Rodada> selecionarRodadas(Campeonato campeonato);

    /**
     * Seleciona todas as rodadas de determinado campeonato com partidas com
     * placar definido mas ainda não computadas.
     * 
     * @param campeonato
     * @return
     */
    List<Rodada> selecionarRodadasNaoCalculadas(Campeonato campeonato);

    /**
     * Seleciona todas as rodadas com partidas com e sem placar definidos.
     * 
     * @param campeonato
     * @return
     */
    List<Rodada> selecionarRodadasNaoCalculadasIncluindoSemPlacar(
	    Campeonato campeonato);

    /**
     * Seleciona todas as rodadas de determiando campeonato com partidas já
     * processadas.
     * 
     * @param campeonato
     * @since 2.0.4
     * @return lista de rodadas
     */
    List<RodadaMeta> selecionarRodadasComPartidasProcessadas(
	    Campeonato campeonato);

}
