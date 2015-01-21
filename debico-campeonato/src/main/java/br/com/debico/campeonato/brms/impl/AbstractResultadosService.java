package br.com.debico.campeonato.brms.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.debico.campeonato.RodadaService;
import br.com.debico.campeonato.brms.CalculoPartidasService;
import br.com.debico.campeonato.brms.CalculoRankingTimesService;
import br.com.debico.campeonato.brms.ResultadosService;
import br.com.debico.model.PartidaChave;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.Chave;
import br.com.debico.model.campeonato.Rodada;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Apoio para as implementações de {@link ResultadosService}.
 * <p/>
 * Deixa os <code>hooks</code> prontos para utilizarem do motor de regras para processar o 
 * resultado das partidas.
 * <p/>
 * Todas as partidas já são previamente definidas com o status correto de acordo com o placar,
 * assim como os pontos adquiridos pelos apostadores no bolão.
 * <p/>
 * É sugerido que todas as classes de processamento utilizem dessa classe para realizar as implementações.
 * 
 * @author r_fernandes
 * @since 0.1
 */
abstract class AbstractResultadosService<C extends Campeonato> implements ResultadosService<C> {

	private final Logger LOGGER = LoggerFactory.getLogger(AbstractResultadosService.class);
    
    @Inject
    private CalculoPartidasService calculoPartidasService;
    
    @Inject
    private CalculoRankingTimesService calculoRankingTimesService;
    
    @Inject
    private RodadaService rodadaService;
    
    public AbstractResultadosService() {
        
    }
    
    protected final CalculoPartidasService getCalculoPartidasService() {
        return calculoPartidasService;
    }
    
    protected CalculoRankingTimesService getCalculoRankingTimesService() {
        return calculoRankingTimesService;
    }

    /**
     * Realiza o processamento da {@link Rodada} especificada.
     * São as partidas que já foram determinadas o placar e que ainda não tem status.
     * Todos os palpites também são computados.
     */
    public final List<PartidaRodada> processar(final C campeonato, final Rodada rodada) {
        //sanity check
        checkNotNull(rodada);
        //define o status das partidas na rodada.
        List<PartidaRodada> partidas = this.calculoPartidasService.definirStatusPartidas(rodada);
        
        if(!partidas.isEmpty()) {
        	//resultado para o campeonato
        	this.doEfetuarCalculoPontosTimeRodada(rodada, partidas);
        	return partidas;
        }
        
        LOGGER.debug("[processar] Nao ha partidas para serem processadas pela rodada {} no campeonato {}", rodada, campeonato);
        
        return Collections.emptyList();
    }

    public final List<PartidaChave> processar(final C campeonato, final Chave chave) {
        //sanity check
        checkNotNull(chave);
        //define o status das partidas na rodada.
        List<PartidaChave> partidas = this.calculoPartidasService.definirStatusPartidas(chave);
        
        if(!partidas.isEmpty()) {
        	//resultado para o campeonato
        	this.doEfetuarCalculoResultadoTimeChave(chave, partidas);
        	return partidas;
        } else {
        	return Collections.emptyList();
        }
    }
    
    @Override
    public final List<PartidaRodada> processar(C campeonato) {
    	List<PartidaRodada> partidas = new ArrayList<PartidaRodada>();
    	List<Rodada> rodadas = rodadaService.selecionarRodadasNaoCalculadas(campeonato);
    	
    	for (Rodada rodada : rodadas) {
			partidas.addAll(this.processar(campeonato, rodada));
		}
    	
    	return partidas;
    }
    
    //hooks
    /**
     * Realiza o processamento do cálculo de pontos específico desse campeonato.
     * <p/>
     * As partidas já foram definidas com o status correto.
     * 
     * @param partidas já processadas e com placar.
     */
    protected abstract void doEfetuarCalculoPontosTimeRodada(final Rodada rodada, final List<PartidaRodada> partidas);
    
    /**
     * Realiza o resultado das partidas na chave em questão.
     * 
     * @param partidas já processadas e com placar.
     */
    protected abstract void doEfetuarCalculoResultadoTimeChave(final Chave chave, final List<PartidaChave> partidas);
    

}
