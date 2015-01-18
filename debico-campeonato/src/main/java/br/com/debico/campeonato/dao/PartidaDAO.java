package br.com.debico.campeonato.dao;

import java.util.List;

import br.com.debico.model.Partida;
import br.com.debico.model.PartidaChave;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.Chave;
import br.com.debico.model.campeonato.Rodada;


public interface PartidaDAO {
    
    void inserir(Partida partida);
    
    Partida atualizar(Partida partida);
    
    Partida selecionarPorId(int id);
        
    /**
     * Recupera todas as partidas ainda não definidas e que estão com placar.
     * <p />
     * Essas partidas são aquelas que já aconteceram e o motor ainda não definiu as suas condições.
     * 
     * @return Lista de {@link Partida}.
     */
    List<PartidaRodada> selecionarPartidasNaoDefinidasComPlacar(Rodada rodada);
    
    /**
     * @see #selecionarPartidasNaoDefinidasComPlacar(Rodada)
     * @param rodada
     * @return
     */
    List<PartidaChave> selecionarPartidasNaoDefinidasComPlacar(Chave rodada);
    
  
    /**
     * 
     * @see #selecionarPartidasPorRodada(Rodada)
     */
    List<PartidaRodada> selecionarPartidasPorRodada(int idRodada);
    
    /**
     * Seleciona as partidas de acordo com a numeração da rodada, por campeonato.
     * 
     * @param ordemRodada ordinal da rodada. Ex: 1, 2, 3... n.
     * @param campeonatoPermalink permalink do campeonato em questão.
     * @return
     */
    List<PartidaRodada> selecionarPartidasPorRodada(int ordemRodada, String campeonatoPermalink);
    
    /**
     * Seleciona as próximas 5 partidas de determinado campeonato.
     * Estas partidas já deveram possuir data e locais definidos.
     * 
     * @param campeonato em questão.
     * @return
     */
    List<Partida> selecionarProximasPartidas(Campeonato campeonato);
    
    /**
     * <code>{@link Rodada#getDataInicioRodada()} >= today AND {@link Rodada#getDataFimRodada()} <= today</code> 
     * @param campeonato
     * @return
     */
    List<PartidaRodada> selecionarPartidasRodadaAtual(Campeonato campeonato);

}
