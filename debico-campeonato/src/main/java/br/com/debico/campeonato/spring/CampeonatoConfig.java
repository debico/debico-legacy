package br.com.debico.campeonato.spring;

import org.kie.api.KieBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.debico.core.brms.BRMSExecutor;
import br.com.debico.core.brms.impl.DroolsBRMSExecutor;

/**
 * Configuração da Factory do Spring que deve ser importada por camadas que
 * desejam utilizar os beans do módulo <code>debico-campeonato</code>.
 * <p/>
 * Para que a criação dos Beans seja bem-sucedida, importar em classes que devem
 * extender asconfigurações providas pelo <code>debico-core</code>:
 * {@link br.com.debico.core.spring.config.ServicesConfig}
 * 
 * 
 * @author ricardozanini
 * @since 2.0.0
 * 
 */
@Configuration
@ComponentScan({ "br.com.debico.campeonato.dao",
        "br.com.debico.campeonato.services", "br.com.debico.campeonato.brms" })
public class CampeonatoConfig {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CampeonatoConfig.class);

    public CampeonatoConfig() {

    }

    @SuppressWarnings("resource")
    @Bean(name="campeonatoBrmsExecutor")
    public BRMSExecutor campeonatoBrmsExecutor() {
        LOGGER.debug("********* INICIO DA CONFIGURACAO DO KIE *********");
        ConfigurableApplicationContext kieAppContext = new ClassPathXmlApplicationContext(
                "/br/com/debico/campeonato/brms/spring/applicationContext-brms.xml");
        kieAppContext.registerShutdownHook();

        DroolsBRMSExecutor brmsExecutor = new DroolsBRMSExecutor();
        brmsExecutor.setKieBase(kieAppContext.getBean(KieBase.class));
        LOGGER.debug("********* FIM DA CONFIGURACAO DO KIE *********");
        
        return brmsExecutor;
    }

}
