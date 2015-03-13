package br.com.debico.ui.spring;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

/**
 * Arquivo de configuração do Spring para configurar a documentação da API interna. 
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
@EnableSwagger
@Configuration
public class SwaggerConfig {

    @Inject
    protected SpringSwaggerConfig springSwaggerConfig;

    public SwaggerConfig() {

    }

    /**
     * Configuração da documentação da API.
     * 
     * @see <a href="https://github.com/martypitt/swagger-springmvc">Swagger
     *      Spring MVC</a>
     * @return
     */
    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(springSwaggerConfig)
                .includePatterns("/api/*")
                .includePatterns("/widget/*")
                .apiInfo(this.apiInfo());
    }

    protected ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Debico API",
                "API interna da aplicação Debico para interagir nativamente com a interface web.",
                null, "ricardozanini@gmail.com", "Private License", null);

        return apiInfo;

    }
}