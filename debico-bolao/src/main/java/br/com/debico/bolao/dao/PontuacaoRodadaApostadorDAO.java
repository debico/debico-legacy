package br.com.debico.bolao.dao;

import java.util.List;

import br.com.debico.bolao.model.PontuacaoRodadaApostador;
import br.com.debico.model.campeonato.Campeonato;

/**
 * Conjunto de instruções de leitura de dados relacionados a pontuação de apostadores por rodada.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
public interface PontuacaoRodadaApostadorDAO {
    
    /**
     * Agrega a pontuação por rodada por apostador de determinado {@link Campeonato}.
     * 
     * @param campeonato
     * @return 
     */
    List<PontuacaoRodadaApostador> recuperarTop10Apostadores(Campeonato campeonato);
    
    /**
     * O mesmo que {@link #recuperarTop10Apostadores(Campeonato)}, com a diferença de selecionar um apostador específico.
     * 
     * @param campeonato identificador do campeonato
     * @param idApostador identificador do apostador
     * @return
     */
    List<PontuacaoRodadaApostador> recuperarPontuacaoApostador(Campeonato campeonato, int idApostador);

}
