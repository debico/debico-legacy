package br.com.debico.ui.it;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegrationTestSupport {
	
	private final Logger LOGGER = LoggerFactory.getLogger(IntegrationTestSupport.class);
	
	private final Properties jettyProperties;

	protected IntegrationTestSupport() {
		this.jettyProperties = new Properties();
		// etc/jetty está no classpath.
		try {
			jettyProperties.load(this.getClass().getResourceAsStream("/env.properties"));
		} catch (IOException e) {
			LOGGER.warn("Nao foi possivel carregar o arquivo de properties", e);
		}
	}
    
    protected String buildURL(final String path) {
        final String url = String.format("http://localhost:%s%s", jettyProperties.getProperty("jetty.port"), path);
        LOGGER.info("Path construido: {}", url);
        return url;
    }
    
    protected WebDriver buildDriver(final String path) {
        final WebDriver driver = new HtmlUnitDriver();
        driver.get(this.buildURL(path));
        
        return driver;
    }
    
    protected <T> T buildPage(final String path, final Class<T> pageClassToProxy) {
        return PageFactory.initElements(this.buildDriver(path), pageClassToProxy);
    }
    
    protected <T> T buildPage(final WebDriver driver, final Class<T> pageClassToProxy) {
        return PageFactory.initElements(driver, pageClassToProxy);
    }
}
