package br.com.debico.ui.it;

import java.io.IOException;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;

public class IntegrationTestSupport {

    protected final Logger LOGGER = LoggerFactory.getLogger(IntegrationTestSupport.class);

    private final Properties jettyProperties;

    private HtmlUnitDriver driver;

    protected IntegrationTestSupport() {
        this.jettyProperties = new Properties();
        if (!this.loadPropertiesFromJettyEnv()) {
            this.loadPropertiesFromJUnitEnv();
        }
    }

    @Before
    public void init() {
        LOGGER.info("Iniciando o Driver");
        driver = this.getDriverWithProxy();
        LOGGER.info("Driver iniciado");
    }

    @After
    public void terminate() {
        if (driver != null) {
            LOGGER.info("Driver desconectando");
            driver.quit();
            driver = null;
            LOGGER.info("Driver liberado");
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
        final Proxy proxy = new Proxy();
        proxy.setHttpProxy("squid.pamcary-interno.com.br:3128");
        proxy.setNoProxy("localhost");
        proxy.setSslProxy("squid.pamcary-interno.com.br:3128");
        // proxy.setProxyType(ProxyType.MANUAL);
        // proxy.setSocksProxy("squid.pamcary-interno.com.br:3128");
        // proxy.setSocksUsername("r_fernandes");
        // proxy.setSocksPassword("Vedder2468");
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(CapabilityType.PROXY, proxy);
        cap.setCapability(CapabilityType.VERSION, BrowserVersion.FIREFOX_45);
        final HtmlUnitDriver driver = new HtmlUnitDriver(cap) {
            @Override
            protected WebClient modifyWebClient(WebClient client) {
                //http://hc.apache.org/httpcomponents-client-ga/tutorial/html/authentication.html
                final DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) client
                        .getCredentialsProvider();
                credentialsProvider.addNTLMCredentials("r_fernandes", "Vedder2468", "squid.pamcary-interno.com.br",
                        3128, "nisop02514", "GPS-PAMCARY");
                LOGGER.info("Definido as credenciais");
                return client;
            }
        };

        return driver;
    }
}
