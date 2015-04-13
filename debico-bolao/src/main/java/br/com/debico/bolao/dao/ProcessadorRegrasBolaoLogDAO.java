package br.com.debico.bolao.dao;

import java.util.Date;
import java.util.List;

import br.com.debico.bolao.brms.model.ProcessadorRegrasBolaoLog;
import br.com.tecnobiz.spring.config.dao.Dao;

public interface ProcessadorRegrasBolaoLogDAO extends
		Dao<ProcessadorRegrasBolaoLog, Long> {

	List<ProcessadorRegrasBolaoLog> recuperarLogs(final Date dataLog);

}
