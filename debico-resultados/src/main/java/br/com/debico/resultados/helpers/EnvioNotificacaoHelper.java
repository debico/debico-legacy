package br.com.debico.resultados.helpers;

import java.util.Collection;

import br.com.debico.model.PartidaBase;
import br.com.debico.model.campeonato.Campeonato;

/**
 * Helper para o envio de notificações sobre resultados.
 * 
 * @author rzanini
 * @since 2.0.5
 */
public interface EnvioNotificacaoHelper {

    void enviarAlertaAtualizacaoPartidas(Campeonato campeonato,
	    Collection<? extends PartidaBase> partidas);
    
    void enviarAlertaErroAtualizacaoPartidas(Campeonato campeonato, Throwable erro);

}
