package br.com.debico.bolao.dao;

import java.util.List;

import br.com.debico.model.Apostador;
import br.com.debico.model.ApostadorPontuacao;
import br.com.debico.model.campeonato.Campeonato;
import br.com.tecnobiz.spring.config.dao.Dao;

public interface ApostadorPontuacaoDAO extends Dao<ApostadorPontuacao, Apostador> {
    
    List<ApostadorPontuacao> selecionarApostadores(Campeonato campeonato);
    
    /**
     * Retorna os apostadores de determinado campeonato, de determinada liga.
     * 
     * @param idCampeonato identificador do campeonato
     * @param idLiga identificador da liga
     * @return
     */
    List<ApostadorPontuacao> selecionarApostadoresPorLiga(int idCampeonato, long idLiga);
        
    ApostadorPontuacao selecionarApostador(Apostador apostador, Campeonato campeonato);

}
