package br.com.debico.bolao.spring;

import javax.inject.Inject;

import org.kie.api.KieServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import br.com.debico.campeonato.spring.CampeonatoConfig;
import br.com.debico.core.brms.BRMSExecutor;
import br.com.debico.core.brms.impl.DroolsBRMSExecutor;
import br.com.debico.core.spring.config.ServicesConfig;
import br.com.debico.notify.spring.NotifyConfig;
import br.com.debico.social.spring.SocialConfig;

/**
 * Deve ser utilizado por clientes que desejam utilizar os beans disponíveis no
 * <code>debico-bolao</code>.
 * <p/>
 * Tais camadas deverão importar {@link ServicesConfig}.
 * 
 * @author ricardozanini
 *
 */
@Configuration
@Import({ CampeonatoConfig.class, SocialConfig.class, NotifyConfig.class })
@ComponentScan({ "br.com.debico.bolao.services", "br.com.debico.bolao.dao",
        "br.com.debico.bolao.brms" })
public class BolaoConfig extends ServicesConfig {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(BolaoConfig.class);

    @Inject
    protected CampeonatoConfig campeonatoConfig;

    @Inject
    protected SocialConfig socialConfig;

    @Inject
    protected NotifyConfig notifyConfig;

    public BolaoConfig() {

    }

    @Bean(name = "bolaoBrmsExecutor")
    public BRMSExecutor bolaoBrmsExecutor() {
        LOGGER.debug("********* INICIO DA CONFIGURACAO DO KIE *********");
        
        KieServices kieServices = KieServices.Factory.get();

        DroolsBRMSExecutor brmsExecutor = new DroolsBRMSExecutor();
        brmsExecutor.setKieBase(kieServices.getKieClasspathContainer()
                .getKieBase("bolaoKBase"));
        
        LOGGER.debug("********* FIM DA CONFIGURACAO DO KIE *********");

        return brmsExecutor;
    }

}
