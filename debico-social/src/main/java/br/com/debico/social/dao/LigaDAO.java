package br.com.debico.social.dao;

import java.util.List;

import br.com.debico.model.Apostador;
import br.com.debico.social.model.Liga;
import br.com.tecnobiz.spring.config.dao.Dao;

public interface LigaDAO extends Dao<Liga, Long> {
	

	/**
	 * Seleciona todas as ligas nas quais este apostador está envolvido.
	 * 
	 * @return lista de ligas nos quais o apostador está relacionado
	 */
	List<Liga> selecionarPorUsuario(final int idUsuario);
	
	/**
	 * Seleciona todos os apostadores de determinada liga.
	 * 
	 * @param idLiga
	 * @return todos os apostadores de determinada liga.
	 */
	List<Apostador> selecionarApostadores(final long idLiga);
	
 	
}
