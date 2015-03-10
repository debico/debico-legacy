package br.com.debico.ui.it.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SucessoPage extends AbstractPage {
    
    @CacheLookup
    @FindBy(id="redirecionarHome")
    private WebElement linkRedirecionarLocator;

    public SucessoPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public String getTitle() {
        return "Sucesso";
    }
    
    public HomePage continuarSeLogado() {
        this.linkRedirecionarLocator.click();
        
        return PageFactory.initElements(getWebDriver(), HomePage.class);
    }
    
    public LoginPage continuarSeAnonimo() {
        this.linkRedirecionarLocator.click();
        
        return PageFactory.initElements(getWebDriver(), LoginPage.class);
    }

}
