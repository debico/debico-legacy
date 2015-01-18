package br.com.debico.bolao;

import java.util.Date;
import java.util.List;

import br.com.debico.model.Apostador;

/**
 * Serviço para a criação de alertas relacionados ao Palpite.
 * 
 * @author ricardozanini
 * @since 1.1.0
 */
public interface AlertaPalpiteService {

	/**
	 * Envia um alerta (normalmente via email) para os apostadores que ainda não palpitaram nas partidas da data desejada.
	 * <p/>
	 * Não envia o alerta para apostadores ainda não inscritos no campeonato, ou seja, para apostadores que ainda não deram um mísero palpite.
	 *  
	 * @param dataPartida
	 * @return lista de apostadores para o qual foram enviados os emails.
	 */
	List<Apostador> enviarAlertasApostadoresSemPalpite(Date dataPartida);
	
	List<Apostador> enviarAlertasApostadoresSemPalpite();
	
}
