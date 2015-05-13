package br.com.debico.bolao.schedulers;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.services.AlertaPalpiteService;

@Named
@Transactional(readOnly = true)
class AlertaPalpiteScheduler {

    @Inject
    @Named("alertaPalpiteServiceImpl")
    private AlertaPalpiteService alertaPalpiteService;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AlertaPalpiteScheduler.class);

    public AlertaPalpiteScheduler() {

    }

    /**
     * Acontece periodicamente. Não chamar diretamente.
     * <p/>
     * A agenda padrão para rodar todo dia de manhã (6am).
     * 
     * O padrão <code>cron</code> é <code>0 0 6 1/1 * ?</code>
     * 
     * @see <a href="http://www.cronmaker.com">Cron Maker</a>
     * @see <a href=
     *      "http://docs.spring.io/spring/docs/current/spring-framework-reference/html/scheduling.html#scheduling-annotation-support-scheduled"
     *      >The @Scheduled Annotation</a>
     * @see Scheduled
     */
    @Scheduled(cron = "0 0 6 1/1 * ?")
    public void enviarAlertasApostadoresSemPalpite() {
        LOGGER.debug("[enviarAlertasApostadoresSemPalpite] Tentando enviar alertas aos apostadores.");

        alertaPalpiteService.enviarAlertasApostadoresSemPalpite();
    }

}
