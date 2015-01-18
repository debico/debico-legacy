package br.com.debico.bolao.brms.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.brms.ProcessadorRegrasBolao;
import br.com.debico.model.campeonato.Campeonato;

/**
 * Wrapper para a verdadeira implementação de {@link ProcessadorResultados} com as configurações de {@link Scheduled}.
 * 
 * @author ricardozanini
 *
 */
@Named
@Transactional(readOnly = false)
class ProcessadorRegrasBolaoScheduler implements ProcessadorRegrasBolao {
	
	@Inject
	@Named("processadorRegrasBolaoImpl")
	private ProcessadorRegrasBolao innerProcessador;

	public ProcessadorRegrasBolaoScheduler() {

	}
	
	/**
	 * Acontece periodicamente. Não chamar diretamente.
	 * <p/>
	 * A agenda padrão é rodar toda madrugada de:
	 * <ol>
	 * 	<li>segundas (para jogos no domingo)</li>
	 * 	<li>quintas (para jogos na quarta)</li>
	 * 	<li>sextas (para jogos na quinta)</li>
	 * 	<li>domings (para jogos no sábado)</li>
	 * </ol>
	 * 
	 *  O padrão <code>cron</code> é <code>0 0 2 ? * MON,THU,FRI,SUN *</code>
	 *  
	 *  @see <a href="http://www.cronmaker.com">Cron Maker</a>
	 *  @see <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/scheduling.html#scheduling-annotation-support-scheduled">The @Scheduled Annotation</a>
	 *  @see Scheduled
	 */
	@Scheduled(cron = "0 0 2 ? * MON,THU,FRI,SUN")
	public void processarResultados() {
		this.innerProcessador.processarResultados();
	}
	
	@Override
	public void processarResultados(Campeonato campeonato) {
		throw new UnsupportedOperationException("O scheduler processa todos os campeonatos ao inves de um especifico.");
	}

}
