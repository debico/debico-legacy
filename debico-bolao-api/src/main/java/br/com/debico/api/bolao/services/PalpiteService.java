package br.com.debico.api.bolao.services;

import br.com.debico.api.bolao.domain.Palpite;
import br.com.debico.api.bolao.domain.Placar;

public interface PalpiteService {

    Palpite realizarPalpite(Placar placar, String emailApostador, long idPartida);

}
