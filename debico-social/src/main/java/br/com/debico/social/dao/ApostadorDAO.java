package br.com.debico.social.dao;

import java.util.Date;
import java.util.List;

import br.com.debico.model.Apostador;
import br.com.debico.model.campeonato.Campeonato;


public interface ApostadorDAO {
        
    Apostador selecionarPorEmail(String email);
    
    /**
     * Recupera todas as informações do {@link Apostador} por meio de FETCH JOINS.
     * 
     * @param email
     * @return
     * @since 1.2.0
     */
    Apostador selecionarPerfilPorEmail(String email);
    
    void inserir(Apostador apostador);
    
    /**
     * Recupera uma lista de apostadores que estão inscritos em determinado {@link Campeonato} e que não palpitaram nas partidas da data especificada. 
     *  
     * @param campeonato que o {@link Apostador} está inscrito
     * @param dataPartida data (sem informações sobre hora) de realização da partida.
     * @return {@link List} com a relação dos apostadores
     * @since 1.1.0-RC1
     */
    List<Apostador> selecionarApostadoresSemPalpite(Campeonato campeonato, Date dataInicioPartida, Date dataFimPartida);
    
    Apostador selecionarPorId(Integer id);
}