package br.com.debico.ui.it;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegrationTestSupport {

    protected final Logger LOGGER = LoggerFactory.getLogger(IntegrationTestSupport.class);

    private final Properties jettyProperties;

    protected IntegrationTestSupport() {
        this.jettyProperties = new Properties();
        if (!this.loadPropertiesFromJettyEnv()) {
            this.loadPropertiesFromJUnitEnv();
        }
    }

    private boolean loadPropertiesFromJettyEnv() {
        try {
            // etc/jetty está no classpath.
            jettyProperties.load(this.getClass().getResourceAsStream("/env.properties"));
            LOGGER.info("Propriedades carregadas no ambiente com sucesso!");
        } catch (IOException e) {
            LOGGER.warn("Nao foi possivel carregar o arquivo de properties", e);
            return false;
        }
        return true;
    }

    private boolean loadPropertiesFromJUnitEnv() {
        try {
            // etc/jetty está no classpath.
            jettyProperties.load(this.getClass().getResourceAsStream("/jetty/env.properties"));
            LOGGER.info("Propriedades carregadas no ambiente com sucesso!");
        } catch (IOException e) {
            LOGGER.warn("Nao foi possivel carregar o arquivo de properties", e);
            return false;
        }
        return true;
    }

    protected String buildURL(final String path) {
        final String url = String.format("http://localhost:%s%s", jettyProperties.getProperty("jetty.port"), path);
        LOGGER.info("Path construido: {}", url);
        return url;
    }

    protected WebDriver buildDriver(final String path) {
        final HtmlUnitDriver driver = this.getDriverWithProxy();
        driver.get(this.buildURL(path));
        LOGGER.info("Driver para o teste disponibilizado");
        return driver;
    }

    protected <T> T buildPage(final String path, final Class<T> pageClassToProxy) {
        LOGGER.info("Construindo a pagina em {} com proxy {}", path, pageClassToProxy);
        return PageFactory.initElements(this.buildDriver(path), pageClassToProxy);
    }

    protected <T> T buildPage(final WebDriver driver, final Class<T> pageClassToProxy) {
        LOGGER.info("Construindo a pagina com driver {} e proxy {}", driver, pageClassToProxy);
        return PageFactory.initElements(driver, pageClassToProxy);
    }

    private HtmlUnitDriver getDriverWithProxy() {

        final HtmlUnitDriver driver = new HtmlUnitDriver(true) {
            // @Override
            // protected WebClient modifyWebClient(WebClient client) {
            // DefaultCredentialsProvider credentialsProvider = new
            // DefaultCredentialsProvider();
            // credentialsProvider.addCredentials("r_fernandes", "Vedder2468",
            // "squid.pamcary-interno.com.br", 3128,
            // null);
            // client.setCredentialsProvider(credentialsProvider);
            // LOGGER.info("Definido as credenciais");
            // return client;
            // }
        };

        return driver;
    }
}
