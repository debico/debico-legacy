package br.com.debico.resultados.helpers.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.debico.model.PartidaBase;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.notify.model.EmailTemplate;
import br.com.debico.notify.services.EmailNotificacaoService;
import br.com.debico.resultados.helpers.EnvioNotificacaoHelper;

@Named
class EnvioNotificacaoHelperImpl implements EnvioNotificacaoHelper {

    private static final String CTX_KEY_CAMPEONATO = "campeonato";
    private static final String CTX_KEY_PARTIDAS = "partidas";
    private static final String CTX_LINK0 = "LINK0";
    private static final String CTX_LINK1 = "LINK1";
    
    @Inject
    private EmailNotificacaoService emailNotificacaoService;
    
    public EnvioNotificacaoHelperImpl() {
	
    }

    @Override
    public void enviarAlertaAtualizacaoPartidas(Campeonato campeonato,
	    Collection<? extends PartidaBase> partidas) {
	final Map<String, Object> contexto = new HashMap<>(); 
	contexto.put(CTX_KEY_CAMPEONATO, campeonato);
	contexto.put(CTX_KEY_PARTIDAS, partidas);	
	contexto.put(CTX_LINK0, campeonato.getTipo().toLowerCase());
	contexto.put(CTX_LINK1, campeonato.getPermalink());
	
	
    }

}
