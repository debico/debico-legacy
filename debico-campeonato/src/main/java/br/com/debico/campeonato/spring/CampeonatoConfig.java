package br.com.debico.campeonato.spring;

import org.kie.api.KieServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.com.debico.core.brms.BRMSExecutor;
import br.com.debico.core.brms.impl.DroolsBRMSExecutor;
import br.com.debico.core.spring.config.ServicesConfig;

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
public class CampeonatoConfig extends ServicesConfig {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CampeonatoConfig.class);

    public CampeonatoConfig() {

    }

    @Bean(name = "campeonatoBrmsExecutor")
    public BRMSExecutor campeonatoBrmsExecutor() {
        LOGGER.debug("********* INICIO DA CONFIGURACAO DO KIE *********");
        // feito dessa forma em virtude da integração ruim com o Spring
        KieServices kieServices = KieServices.Factory.get();
        DroolsBRMSExecutor brmsExecutor = new DroolsBRMSExecutor();
        brmsExecutor.setKieBase(kieServices.getKieClasspathContainer()
                .getKieBase("campeonatoKBase"));
        LOGGER.debug("********* FIM DA CONFIGURACAO DO KIE *********");

        return brmsExecutor;
    }

}
