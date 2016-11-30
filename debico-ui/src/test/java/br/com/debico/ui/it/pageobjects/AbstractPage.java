package br.com.debico.ui.it.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Página base para todos os testes utilizando PageObjects.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
public abstract class AbstractPage {
    
    private final WebDriver webDriver;
    private final Logger LOGGER = LoggerFactory.getLogger(AbstractPage.class);

    public AbstractPage(final WebDriver webDriver) {
        this.webDriver = webDriver;
        
        if(!isActualPage()) {
            LOGGER.warn("Nao eh a pagina esperada. Pagina atual: {} ", this.webDriver.getTitle());
            throw new IllegalStateException(String.format("Pagina errada! Nao eh %s.", getTitle()));
        }
        
        
    }
    
    /**
     * O título (ou parte dele) da página.
     * 
     * @return
     */
    public abstract String getTitle();
    
    protected WebDriver getWebDriver() {
        return webDriver;
    }

    /**
     * Verifica se a página atual do WebDriver é esta página.
     * 
     * @return
     */
    protected boolean isActualPage() {
        return this.webDriver.getTitle().contains(getTitle());
    }
    
    protected AbstractPage clearAndType(WebElement element, String value) {
        LOGGER.info("Digitando o valor '{}' no campo '{}'", value, element);
        element.clear();
        element.sendKeys(value);
        
        return this;
    }

}
