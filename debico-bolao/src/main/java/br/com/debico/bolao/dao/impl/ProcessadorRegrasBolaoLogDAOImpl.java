package br.com.debico.bolao.dao.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.brms.model.ProcessadorRegrasBolaoLog;
import br.com.debico.bolao.dao.ProcessadorRegrasBolaoLogDAO;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class ProcessadorRegrasBolaoLogDAOImpl extends
		AbstractJPADao<ProcessadorRegrasBolaoLog, Long> implements
		ProcessadorRegrasBolaoLogDAO {

	public ProcessadorRegrasBolaoLogDAOImpl() {
		super(ProcessadorRegrasBolaoLog.class);
	}

	public List<ProcessadorRegrasBolaoLog> recuperarLogs(Date dataLog) {
		return getEntityManager()
				.createQuery("SELECT l FROM ProcessadorRegrasBolaoLog l WHERE dataHoraProcessamento >= :data", ProcessadorRegrasBolaoLog.class)
				.setParameter("data", dataLog)
				.getResultList();
	}
}
