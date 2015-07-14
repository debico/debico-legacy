package br.com.debico.resultados.manager;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.services.CampeonatoPontosCorridosService;
import br.com.debico.campeonato.services.RodadaService;
import br.com.debico.core.helpers.CacheKeys;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.ContextImpl;
import br.com.debico.resultados.DefaultProcessorPipeline;
import br.com.debico.resultados.Processor;
import br.com.debico.resultados.ProcessorBeans;
import br.com.debico.resultados.ProcessorManager;
import br.com.debico.resultados.ProcessorPipeline;

import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Transactional(readOnly = false)
public class PontosCorridosProcessorManager implements ProcessorManager {

    private final ProcessorPipeline processorPipeline;

    @Inject
    private CampeonatoPontosCorridosService campeonatoPontosCorridosService;
    
    @Inject
    private RodadaService rodadaService;

    @Inject
    @Named(ProcessorBeans.CALCULA_ESTATISTICA_TIME)
    private Processor calculaEstatisticaTime;

    @Inject
    @Named(ProcessorBeans.CALCULA_POSICAO_TIMES)
    private Processor calculaPosicaoTimes;

    @Inject
    @Named(ProcessorBeans.DEFINE_STATUS_PARTIDAS)
    private Processor defineStatusPartidas;

    public PontosCorridosProcessorManager() {
        this.processorPipeline = new DefaultProcessorPipeline();
    }

    @PostConstruct
    public void init() {
        checkNotNull(this.calculaEstatisticaTime);
        checkNotNull(this.calculaPosicaoTimes);
        checkNotNull(this.defineStatusPartidas);
        // na ordem em que são processadas.
        this.processorPipeline.addProcessor(defineStatusPartidas);
        this.processorPipeline.addProcessor(calculaEstatisticaTime);
        this.processorPipeline.addProcessor(calculaPosicaoTimes);
    }

    /**
     * Inicia o processamento de todos os campeonatos do tipo Pontos Corridos
     * que estão ativos.
     */
    @CacheEvict(CacheKeys.TABELA_CAMPEONATO)
    public void start() {
        final List<CampeonatoPontosCorridos> campeonatoPontosCorridos =
                campeonatoPontosCorridosService.selecionarCampeonatosPontosCorridosAtivos();
        
        for (CampeonatoPontosCorridos campeonato : campeonatoPontosCorridos) {
            final Context context = new ContextImpl(campeonato);
            context.setRodadas(rodadaService.selecionarRodadasNaoCalculadas(campeonato));
            
            this.processorPipeline.doProcess(context);
        }
    }

}
