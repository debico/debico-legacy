package br.com.debico.api.bolao.domain;


/**
 * Fase do {@link Campeonato}.
 * 
 * @author r_fernandes
 *
 */
public interface Fase {

    int getId();
    
    Integer getOrdem();
        
    String getNome();
        
    Campeonato getCampeonato();
       
}
