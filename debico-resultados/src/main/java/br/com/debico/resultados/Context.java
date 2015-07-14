package br.com.debico.resultados;

import java.io.Serializable;
import java.util.List;

import br.com.debico.model.ApostadorPontuacao;
import br.com.debico.model.PalpiteBase;
import br.com.debico.model.PartidaBase;
import br.com.debico.model.campeonato.AbstractRodada;
import br.com.debico.model.campeonato.Campeonato;

/**
 * Context de processamento da API. Estrutura de elementos chave do domínio. A
 * exigência de determinado item do contexto é de responsabilidade do Filtro que
 * está sendo aplicado.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.4
 */
public interface Context extends Serializable {

    /**
     * Campeonato sendo processado. Deve ser informado durante a construção do
     * contexto.
     * 
     * @return
     */
    Campeonato getCampeonato();

    /**
     * Lista de Rodadas do contexto. Deve estar relacionada com
     * {@link #getCampeonato()}
     * 
     * @return
     */
    List<AbstractRodada> getRodadas();

    void setRodadas(List<? extends AbstractRodada> rodadas);

    /**
     * Lista das Partidas sendo processadas pelo contexto. Deve fazer parte das
     * {@link #getRodadas()}
     * 
     * @return
     */
    List<PartidaBase> getPartidas();

    /**
     * Sobrescreve as partidas já adicionadas.
     * 
     * @param partidas
     */
    void setPartidas(List<? extends PartidaBase> partidas);

    /**
     * Adiciona partidas no contexto.
     * 
     * @param partidas
     */
    void addPartidas(List<? extends PartidaBase> partidas);

    /**
     * Lista dos apostadores do campeonato do contexto.
     * 
     * @return
     */
    List<ApostadorPontuacao> getApostadores();

    void setApostadores(List<? extends ApostadorPontuacao> apostadores);

    /**
     * Lista dos palpites dos apostadores do contexto.
     * 
     * @return
     */
    List<PalpiteBase> getPalpites();

    void setPalpites(List<? extends PalpiteBase> palpites);
}
