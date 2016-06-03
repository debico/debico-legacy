package br.com.debico.resultados.helpers;

import java.util.Collection;

import br.com.debico.model.PartidaBase;
import br.com.debico.model.campeonato.Campeonato;

public interface EnvioNotificacaoHelper {

    void enviarAlertaAtualizacaoPartidas(Campeonato campeonato,
	    Collection<? extends PartidaBase> partidas);

}
