package br.com.debico.campeonato.dao;

import java.util.Collection;
import java.util.List;

import br.com.debico.model.Time;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.PontuacaoTime;
import br.com.tecnobiz.spring.config.dao.Dao;

public interface PontuacaoTimeDAO extends Dao<PontuacaoTime, Integer> {
    
    /**
     * Seleciona uma lista de pontuação de acordo com os times desejados.
     * 
     * @param times
     * @return
     */
    List<PontuacaoTime> selecionarPontuacaoTimes(Collection<Time> times);
    
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
