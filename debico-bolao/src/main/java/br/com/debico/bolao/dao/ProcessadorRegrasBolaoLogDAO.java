package br.com.debico.bolao.dao;

import java.util.Date;
import java.util.List;

import br.com.debico.bolao.brms.ProcessadorRegrasBolaoLog;

public interface ProcessadorRegrasBolaoLogDAO {

	void inserir(final ProcessadorRegrasBolaoLog log);
	
	List<ProcessadorRegrasBolaoLog> recuperarLogs(final Date dataLog);
	
}
