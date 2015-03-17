package br.com.debico.ui.spring;

import static org.ajar.swaggermvcui.SwaggerSpringMvcUi.WEB_JAR_RESOURCE_LOCATION;
import static org.ajar.swaggermvcui.SwaggerSpringMvcUi.WEB_JAR_RESOURCE_PATTERNS;
import static org.ajar.swaggermvcui.SwaggerSpringMvcUi.WEB_JAR_VIEW_RESOLVER_PREFIX;
import static org.ajar.swaggermvcui.SwaggerSpringMvcUi.WEB_JAR_VIEW_RESOLVER_SUFFIX;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

/**
 * Arquivo de configuração do Spring para configurar a documentação da API
 * interna.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @see <a
 *      href="https://github.com/adrianbk/swagger-springmvc-demo/tree/master/swagger-ui">swagger-springmvc-demo</a>
 * @see <a
 *      href="http://raibledesigns.com/rd/entry/documenting_your_spring_api_with">Documenting
 *      your Spring API with Swagger</a>
 * @see <a href="https://github.com/springdox/springdox">Swagger-springmvc</a>
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
                .includePatterns("/api/.*", "/widgets/.*")
                .apiInfo(this.apiInfo());
    }
    
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix(WEB_JAR_VIEW_RESOLVER_PREFIX);
        resolver.setSuffix(WEB_JAR_VIEW_RESOLVER_SUFFIX);
        return resolver;
    }

    protected ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Debico API",
                "API interna da aplicação Debico para interagir nativamente com a interface web.",
                null, "ricardozanini@gmail.com", "Private License", null);

        return apiInfo;

    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(WEB_JAR_RESOURCE_PATTERNS)
                .addResourceLocations(WEB_JAR_RESOURCE_LOCATION)
                .setCachePeriod(0);
    }

    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}