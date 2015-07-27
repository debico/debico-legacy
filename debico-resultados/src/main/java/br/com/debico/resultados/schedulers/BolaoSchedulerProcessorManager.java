package br.com.debico.resultados.schedulers;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.scheduling.annotation.Scheduled;

import br.com.debico.resultados.ManagerBeans;
import br.com.debico.resultados.ProcessorManager;
import br.com.debico.resultados.SchedulerProcessorManager;

/**
 * Delega o processamento perante um agendamento ao {@link ProcessorManager} do
 * bolão.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.4
 */
@Named
class BolaoSchedulerProcessorManager implements SchedulerProcessorManager {

    @Inject
    @Named(ManagerBeans.BOLAO_MANAGER)
    private ProcessorManager processorManager;

    public BolaoSchedulerProcessorManager() {

    }

    /**
     * Acontece periodicamente. Não chamar diretamente.
     * <p/>
     * A agenda padrão é rodar toda madrugada de:
     * <ol>
     * <li>segundas (para jogos no domingo)</li>
     * <li>quintas (para jogos na quarta)</li>
     * <li>sextas (para jogos na quinta)</li>
     * <li>domings (para jogos no sábado)</li>
     * </ol>
     * 
     * O padrão <code>cron</code> é <code>0 0 2 ? * MON,THU,FRI,SUN *</code>
     * 
     * @see <a href="http://www.cronmaker.com">Cron Maker</a>
     * @see <a href=
     *      "http://docs.spring.io/spring/docs/current/spring-framework-reference/html/scheduling.html#scheduling-annotation-support-scheduled"
     *      >The @Scheduled Annotation</a>
     * @see Scheduled
     */
    @Scheduled(cron = "0 0 2 ? * MON,THU,FRI,SUN")
    public void start() {
    }

}
