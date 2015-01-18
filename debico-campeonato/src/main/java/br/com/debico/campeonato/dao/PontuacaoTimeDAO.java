package br.com.debico.campeonato.dao;

import java.util.Collection;
import java.util.List;

import br.com.debico.model.Time;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.PontuacaoTime;

public interface PontuacaoTimeDAO {
    
    /**
     * Seleciona uma lista de pontuação de acordo com os times desejados.
     * 
     * @param times
     * @return
     */
    List<PontuacaoTime> selecionarPontuacaoTimes(Collection<Time> times);
    
    /**
     * Insere um item no Ranking do Campeonato em questão
     * 
     * @param pontuacaoTime
     */
    void inserirPontuacao(PontuacaoTime pontuacaoTime);
    
    /**
     * Busca a pontuação de determinado campeonato, contanto que possua alguma fase que tenha pontuação.
     * <p/>
     * Muito útil em campeonatos que tenham apenas uma Tabela/Grupo. 
     * <p/>
     * Em caso de campeonatos com fases de grupo, utilizar outro método de busca.
     * @param campeonato
     * @return
     */
    List<PontuacaoTime> selecionarPontuacaoCampeonato(Campeonato campeonato);

}
