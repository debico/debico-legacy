package br.com.debico.bolao.services.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import static com.google.common.base.Strings.emptyToNull;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.dao.PontuacaoRodadaApostadorDAO;
import br.com.debico.bolao.model.graph.PontuacaoRodadaApostador;
import br.com.debico.bolao.model.graph.PontuacaoRodadaApostadorSerie;
import br.com.debico.bolao.services.DesempenhoApostadorService;
import br.com.debico.core.helpers.CacheKeys;
import br.com.debico.model.Apostador;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.social.services.ApostadorService;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

@Named
@Transactional(readOnly = true)
class DesempenhoApostadorServiceImpl implements DesempenhoApostadorService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DesempenhoApostadorServiceImpl.class);

    @Inject
    private PontuacaoRodadaApostadorDAO pontuacaoRodadaApostadorDAO;
    
    @Inject
    private ApostadorService apostadorService;

    public DesempenhoApostadorServiceImpl() {

    }
    
    @Override
    public Set<PontuacaoRodadaApostadorSerie> recuperarDesempenhoTop10PontuacaoPorRodada(
            Campeonato campeonato, String emailApostador) {
        checkNotNull(emptyToNull(emailApostador), "Opa! Apostador NULO!");
        final Apostador apostador = apostadorService.selecionarApostadorPorEmail(emailApostador);
        final Set<PontuacaoRodadaApostadorSerie> pontuacao = new TreeSet<PontuacaoRodadaApostadorSerie>();
        final List<PontuacaoRodadaApostador> data = pontuacaoRodadaApostadorDAO.recuperarTop10Apostadores(campeonato);
        boolean inTop10 = false;
        
        LOGGER.debug("[recuperarDesempenhoPontuacaoPorRodada] Tentando recuperar o desempenho do apostador {}", apostador.getId());
        
        for (PontuacaoRodadaApostador r : data) {
            PontuacaoRodadaApostadorSerie serie = new PontuacaoRodadaApostadorSerie(r.getNomeApostador(), r.getIdApostador());
            serie = Iterables.find(pontuacao, Predicates.equalTo(serie), serie);
            serie.addData(r);
            
            if(!pontuacao.contains(serie)) {
                pontuacao.add(serie);
            }
            
            inTop10 = (r.getIdApostador() == apostador.getId() || inTop10);
        }
        
        LOGGER.debug("[recuperarDesempenhoPontuacaoPorRodada] Recuperado o Top 10 {}", pontuacao);
        
        // devemos buscar os pontos do nosso apostador, que não está no Top 10. :(
        if(!inTop10) {
            LOGGER.debug("[recuperarDesempenhoPontuacaoPorRodada] Opa! O apostador '{}' nao estao no TOP 10, recuperando o seu indice", apostador.getId());
            
            pontuacao.add(this.recuperarDesempenhoPontuacaoPorRodada(campeonato, apostador));
        }

        return pontuacao;
    }

    @Cacheable(CacheKeys.DESEMPENHO_IND_APOSTADOR)
    public PontuacaoRodadaApostadorSerie recuperarDesempenhoPontuacaoPorRodada(Campeonato campeonato, String emailApostador) {
        checkNotNull(emptyToNull(emailApostador), "Opa! Apostador NULO!");
        final Apostador apostador = apostadorService.selecionarApostadorPorEmail(emailApostador);
        return this.recuperarDesempenhoPontuacaoPorRodada(campeonato, apostador);
    }
    
    protected  PontuacaoRodadaApostadorSerie recuperarDesempenhoPontuacaoPorRodada(Campeonato campeonato, Apostador apostador) {
        final List<PontuacaoRodadaApostador> desempenho = pontuacaoRodadaApostadorDAO.recuperarPontuacaoApostador(campeonato, apostador.getId());
        
        if(desempenho.size() > 0) {
            return new PontuacaoRodadaApostadorSerie(desempenho);
        } else {
            return new PontuacaoRodadaApostadorSerie(apostador.getNome(), apostador.getId());
        }
    }


}
