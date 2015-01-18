package br.com.debico.bolao.brms;

import java.util.List;

import br.com.debico.campeonato.brms.CalculoPartidasService;
import br.com.debico.model.Partida;
import br.com.debico.model.campeonato.Campeonato;

/**
 * Trata do processamento do cálculo dos palpites em relação às partidas.
 * 
 * @author r_fernandes
 * @since 0.1
 *
 */
public interface CalculoPalpitesService {
    
    /**
     * Realiza a computação dos palpites dentro do motor de regras.
     * 
     * @param campeonato das partidas que serão computadas.
     * @param partidas com placar e status já definidos por {@link CalculoPartidasService}.
     * @see CalculoPartidasService
     */
    void computarPalpites(Campeonato campeonato, List<? extends Partida> partidas);

}
