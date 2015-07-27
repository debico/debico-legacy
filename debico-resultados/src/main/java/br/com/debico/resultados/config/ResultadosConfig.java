package br.com.debico.resultados.config;

import javax.inject.Inject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import br.com.debico.bolao.spring.BolaoConfig;
import br.com.debico.campeonato.spring.CampeonatoConfig;

@EnableScheduling
@EnableAsync
@Order(Integer.MAX_VALUE)
@Configuration
@Import({ BolaoConfig.class, CampeonatoConfig.class })
@ComponentScan({ "br.com.debico.resultados.processors",
        "br.com.debico.resultados.managers", "br.com.debico.resultados.schedulers" })
public class ResultadosConfig {

    @Inject
    protected BolaoConfig bolaoConfig;

    @Inject
    protected CampeonatoConfig campeonatoConfig;

    public ResultadosConfig() {

    }

}
