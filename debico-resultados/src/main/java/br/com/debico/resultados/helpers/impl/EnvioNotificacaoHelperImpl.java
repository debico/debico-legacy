package br.com.debico.resultados.helpers.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.common.collect.Lists;

import br.com.debico.model.PartidaBase;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.notify.model.TipoNotificacao;
import br.com.debico.notify.services.EmailNotificacaoService;
import br.com.debico.resultados.helpers.EnvioNotificacaoHelper;
import br.com.debico.social.services.AdministradorService;

@Named
class EnvioNotificacaoHelperImpl implements EnvioNotificacaoHelper {

	private static final String CTX_KEY_CAMPEONATO = "campeonato";
	private static final String CTX_KEY_PARTIDAS = "partidas";
	private static final String CTX_KEY_ERRO = "stackTrace";

	@Inject
	private EmailNotificacaoService emailNotificacaoService;

	@Inject
	private AdministradorService administradorService;

	public EnvioNotificacaoHelperImpl() {

	}

	@Override
	public void enviarAlertaAtualizacaoPartidas(Campeonato campeonato, Collection<? extends PartidaBase> partidas) {
		final Map<String, Object> contexto = new HashMap<>();
		contexto.put(CTX_KEY_CAMPEONATO, campeonato);
		contexto.put(CTX_KEY_PARTIDAS, partidas);
		emailNotificacaoService.enviarNotificacao(administradorService.selecionarComoContato(),
				TipoNotificacao.ALERTA_ATUALIZACAO_PARTIDA, contexto,
				Lists.newArrayList(campeonato.getTipo().toLowerCase(), campeonato.getPermalink()));
	}

	@Override
	public void enviarAlertaErroAtualizacaoPartidas(Campeonato campeonato, Throwable erro) {
		final Map<String, Object> contexto = new HashMap<>();
		contexto.put(CTX_KEY_CAMPEONATO, campeonato);
		contexto.put(CTX_KEY_ERRO, erro.getStackTrace());
		emailNotificacaoService.enviarNotificacao(administradorService.selecionarComoContato(),
				TipoNotificacao.ERRO_ATUALIZACAO_PARTIDA, contexto,
				Lists.newArrayList(campeonato.getTipo().toLowerCase(), campeonato.getPermalink()));

	}

}
