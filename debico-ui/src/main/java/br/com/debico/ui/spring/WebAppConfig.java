package br.com.debico.ui.spring;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTimeConstants;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import br.com.debico.bolao.spring.BolaoConfig;
import br.com.debico.core.helpers.Constants;
import br.com.debico.ui.controllers.ViewOptions;
import br.com.debico.ui.interceptors.FooterInterceptor;
import br.com.debico.ui.interceptors.MenuInterceptor;
import br.com.debico.ui.interceptors.TitleInterceptor;
import br.com.debico.ui.thymeleaf.DebicoDialect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature;

/**
 * Classe de configuração do contexto Web da aplicação.
 * 
 * @author ricardozanini
 * @see <a href="http://www.thymeleaf.org/doc/thymeleafspring.html">Tutorial:
 *      Thymeleaf + Spring</a>
 * @see <a href="http://www.thymeleaf.org/doc/springsecurity.html">Thymeleaf +
 *      Spring Security integration basics</a>
 */
@Configuration
@Import({ SecurityConfig.class, BolaoConfig.class, SwaggerConfig.class })
@EnableWebMvc
@ComponentScan(basePackages = { "br.com.debico.ui.controllers",
        "br.com.debico.ui.handlers" })
@PropertySource(value = "classpath:META-INF/debico-ui.properties", ignoreResourceNotFound = false)
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Inject
    protected Environment environment;

    @Inject
    protected BolaoConfig serviceConfig;

    @Inject
    protected SecurityConfig securityConfig;

    @Inject
    protected SwaggerConfig swaggerConfig;

    /**
     * @see <a
     *      href="http://stackoverflow.com/questions/21708339/avoid-jackson-serialization-on-non-fetched-lazy-objects/21760361#21760361">Avoid
     *      Jackson serialization on non fetched lazy objects</a>
     * @return
     */
    public MappingJackson2HttpMessageConverter jacksonMessageConverter() {
        final MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        final ObjectMapper mapper = new ObjectMapper();
        final Hibernate4Module module = new Hibernate4Module();
        // https://github.com/FasterXML/jackson-databind/issues/573
        module.configure(Feature.USE_TRANSIENT_ANNOTATION, false);

        mapper.registerModule(module);

        messageConverter.setObjectMapper(mapper);
        messageConverter.setPrettyPrint(Boolean.valueOf(environment
                .getProperty("br.com.debico.ui.web.json.prettyPrint")));

        return messageConverter;
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        converters.add(this.jacksonMessageConverter());
        super.configureMessageConverters(converters);
    }

    @Bean
    public ServletContextTemplateResolver templateResolver() {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCacheable(false);

        return templateResolver;
    }

    /**
     * @see <a
     *      href="https://github.com/thymeleaf/thymeleaf-extras-springsecurity3">Thymeleaf
     *      - Spring Security 3 integration module</a>
     * @param templateResolver
     * @return
     */
    @Bean
    public SpringTemplateEngine templateEngine(
            final ServletContextTemplateResolver templateResolver) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(new SpringSecurityDialect());
        templateEngine.addDialect(new DebicoDialect());

        return templateEngine;
    }

    @Bean
    public ViewResolver viewResolver(final SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        viewResolver.setOrder(1);

        return viewResolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // registry.addViewController("/support/");
        super.addViewControllers(registry);
    }

    /**
     * @see <a
     *      href="http://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#headers">Security
     *      HTTP Response Headers</a>
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/robots.txt").addResourceLocations("/")
                .setCachePeriod(DateTimeConstants.SECONDS_PER_WEEK);

        registry.addResourceHandler("/static/css/**")
                .addResourceLocations("/static/css/")
                .setCachePeriod(DateTimeConstants.SECONDS_PER_WEEK);

        registry.addResourceHandler("/static/js/**")
                .addResourceLocations("/static/js/")
                .setCachePeriod(DateTimeConstants.SECONDS_PER_DAY);

        registry.addResourceHandler("/static/images/**")
                .addResourceLocations("/static/images/")
                .setCachePeriod(
                        DateTimeConstants.SECONDS_PER_DAY
                                * Constants.DAYS_PER_MONTH); // um mês

        // swagger
        this.swaggerConfig.addResourceHandlers(registry);
    }

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        this.swaggerConfig.configureDefaultServletHandling(configurer);
    }

    @Bean
    public MenuInterceptor menuInterceptor() {
        return new MenuInterceptor();
    }

    @Bean
    public FooterInterceptor footerInterceptor() {
        return new FooterInterceptor();
    }

    @Bean
    public TitleInterceptor titleInterceptor() {
        return new TitleInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // @formatter:off
        registry.addInterceptor(this.menuInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/public/**")
                .excludePathPatterns("/api/**")
                .excludePathPatterns("/contato/**")
                .excludePathPatterns("/login/**");

        registry.addInterceptor(this.footerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/**");

        registry.addInterceptor(this.titleInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/api/**");
        // @formatter:on
    }

    /**
     * @see <a
     *      href="http://www.mkyong.com/spring-mvc/spring-mvc-internationalization-example/">Spring
     *      MVC Internationalization Example</a>
     * @see <a
     *      href="http://www.mkyong.com/spring/spring-how-to-access-messagesource-in-bean-messagesourceaware/">Spring
     *      – How To Access MessageSource In Bean (MessageSourceAware)</a>
     * @param parentMessageSource
     * @return
     */
    @Bean
    public MessageSource messageSource(MessageSource parentMessageSource) {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();

        resourceBundleMessageSource.setParentMessageSource(parentMessageSource);
        resourceBundleMessageSource.setBasename("messages");
        resourceBundleMessageSource.setAlwaysUseMessageFormat(false);

        return resourceBundleMessageSource;
    }

    @Bean(name = "viewOptions")
    public ViewOptions viewOptions() {
        final ViewOptions viewOptions = new ViewOptions();

        viewOptions.setEnableGa(Boolean.valueOf(environment
                .getProperty("br.com.debico.ui.web.ga")));
        viewOptions.setGaWebPropertyId(environment
                .getProperty("br.com.debico.ui.web.ga.id"));
        viewOptions.setGaLocalhost(Boolean.valueOf(environment
                .getProperty("br.com.debico.ui.web.ga.localhost")));

        return viewOptions;
    }
}
