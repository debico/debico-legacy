package br.com.debico.bolao.dao;

import java.util.List;

import br.com.debico.model.Apostador;
import br.com.debico.model.Palpite;
import br.com.debico.model.Partida;


public interface PalpiteDAO {
    
    void inserir(Palpite palpite);
    
    Palpite atualizar(Palpite palpite);
    
    List<Palpite> selecionarTodos();
    
    /**
     * Recupera todos os palpites de determinadas partidas.
     * 
     * @param partidas desejadas.
     * @return relação dos palpites.
     */
    List<Palpite> selecionarTodos(List<? extends Partida> partidas);
    
    /**
     * Pesquisa a existência de um palpite para determinada partida.
     * 
     * @param partida
     * @param apostador
     * @return
     */
    boolean pesquisarExistenciaPalpite(Partida partida, Apostador apostador);

}
 