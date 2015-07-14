package br.com.debico.bolao.dao;

import java.util.List;

import br.com.debico.model.Apostador;
import br.com.debico.model.Palpite;
import br.com.debico.model.Partida;
import br.com.debico.model.PartidaBase;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.tecnobiz.spring.config.dao.Dao;


public interface PalpiteDAO extends Dao<Palpite, Integer> {
    
    /**
     * Recupera todos os palpites de determinadas partidas.
     * 
     * @param partidas desejadas.
     * @return relação dos palpites.
     */
    List<Palpite> selecionarTodos(List<? extends PartidaBase> partidas);
    
    /**
     * Recupera todos os palpites do campeonato em questão. Utilizar com cuidado.
     * 
     * @param campeonato
     * @since 2.0.3
     * @return
     */
    List<Palpite> selecionarTodos(CampeonatoImpl campeonato);
    
    /**
     * Pesquisa a existência de um palpite para determinada partida.
     * 
     * @param partida
     * @param apostador
     * @return
     */
    boolean pesquisarExistenciaPalpite(Partida partida, Apostador apostador);

}
 