package br.com.debico.campeonato.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.CampeonatoService;
import br.com.debico.campeonato.dao.CampeonatoDAO;
import br.com.debico.core.helpers.CacheKeys;
import br.com.debico.model.campeonato.CampeonatoImpl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import static com.google.common.base.Strings.emptyToNull;

/**
 * @see <a href="http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/cache.html">Cache Abstraction</a>
 * @author ricardozanini
 *
 */
@Named
@Transactional(readOnly = true)
class CampeonatoServiceImpl implements CampeonatoService {

    protected static final Logger LOGGER = LoggerFactory.getLogger(CampeonatoPontosCorridosServiceImpl.class);
    
	@Inject
	private CampeonatoDAO campeonatoDAO;
	
	@Cacheable(CacheKeys.CAMPEONATOS_ATIVOS)
	public List<CampeonatoImpl> selecionarCampeonatosAtivos() {		
		LOGGER.debug("[selecionarCampeonatosAtivos] Retornando todos os campeonatos ativos");
		return campeonatoDAO.selecionarCampeonatosAtivosNaoFinalizados();
	}
	
	@Cacheable(CacheKeys.CAMPEONATOS)
	public CampeonatoImpl selecionarCampeonato(final String permalink) {
        LOGGER.debug("[selecionarCampeonato] Tentando recuperar o campeonato pelo permalink {}", permalink);
        
        checkNotNull(emptyToNull(permalink));
        return campeonatoDAO.selecionarPorPermalink(permalink);
    }
	
	@Cacheable(CacheKeys.CAMPEONATOS)
	public CampeonatoImpl selecionarCampeonato(final int id) {
	    LOGGER.debug("[selecionarCampeonato] Tentando recuperar o campeonato pelo id {}", id);
	    
	    checkArgument(id > 0);
	    return campeonatoDAO.selecionar(id);
	}

}
