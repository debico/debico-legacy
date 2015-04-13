package br.com.debico.campeonato.dao;

import java.util.List;

import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.Rodada;
import br.com.tecnobiz.spring.config.dao.Dao;

public interface RodadaDAO extends Dao<Rodada, Integer> {

	Rodada selecionarRodadaAtual(Campeonato campeonato);

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

}
