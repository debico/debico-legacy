package br.com.debico.campeonato.services;

import java.util.Date;

import br.com.debico.model.Partida;
import br.com.debico.model.Placar;

/**
 * Realiza os casos de uso relacionado com as partidas dos campeonatos.
 * 
 * @author r_fernandes
 * @since 0.1
 */
public interface PartidaService {

	/**
	 * Salva o placar da partida identificada.
	 * 
	 * @param idPartida
	 *            identificador da partida
	 * @param placar
	 *            instância de {@link Placar} corretamente definida.
	 * @return
	 */
	Partida salvarPlacar(int idPartida, Placar placar);

	/**
	 * Atualiza a data e horário de uma partida pré-determinada.
	 * 
	 * @param idPartida
	 *            identificador da partida
	 * @param dataHorarioPartida
	 *            data e horário da partida.
	 * @return
	 */
	Partida atualizarDataHorario(int idPartida, Date dataHorarioPartida);

	/**
	 * Atualiza os base da partida: horário e local.
	 * <p/>
	 * <strong>Atenção:</strong> este método não limpa os dados de cache das
	 * partidas.
	 * 
	 * @param idPartida
	 *            identificador da partida
	 * @param dataHorarioPartida
	 *            data e horário da partida.
	 * @param local
	 *            da partida
	 * @return
	 */
	Partida atualizarDataHorarioLocal(int idPartida, Date dataHorarioPartida, String local);

}
