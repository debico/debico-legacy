package br.com.debico.resultados;

import javax.inject.Inject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.debico.bolao.spring.BolaoConfig;
import br.com.debico.campeonato.spring.CampeonatoConfig;

@EnableTransactionManagement
@Configuration
@Import({BolaoConfig.class, CampeonatoConfig.class})
@ComponentScan({"br.com.debico.resultados.processors", "br.com.debico.resultados.managers"})
public class ResultadosConfig {

    @Inject
    protected BolaoConfig bolaoConfig;
    
    @Inject
    protected CampeonatoConfig campeonatoConfig;
    
    public ResultadosConfig() {
	
    }

}
