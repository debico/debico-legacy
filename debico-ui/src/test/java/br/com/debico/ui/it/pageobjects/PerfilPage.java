package br.com.debico.ui.it.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PerfilPage extends InternalPage {
    
    public static String PERFIL = "/perfil";

    @CacheLookup
    @FindBy(id="nome")
    private WebElement nomeLocator;
    
    @CacheLookup
    @FindBy(id="apelido")
    private WebElement apelidoLocator;
    
    @CacheLookup
    @FindBy(id="lembretePalpites")
    private WebElement lembretePalpitesLocator;
    
    @CacheLookup
    @FindBy(id="perfilButton")
    private WebElement perfilButtonLocator;
    
    public PerfilPage(WebDriver webDriver) {
        super(webDriver);
    }
    
    @Override
    public String getTitle() {
        return "Perfil";
    }
    
    public PerfilPage typeNome(String nome) {
        return (PerfilPage) this.clearAndType(nomeLocator, nome);
    }
    
    public PerfilPage typeApelido(String apelido) {
        return (PerfilPage) this.clearAndType(apelidoLocator, apelido);
    }
    
    public PerfilPage checkLembrarPalpite() {
        lembretePalpitesLocator.click();
        
        return this;
    }
    
    public AbstractPage submit() {
        perfilButtonLocator.click();
        
        if(isActualPage()) {
            return this;
        }
        
        return PageFactory.initElements(getWebDriver(), SucessoPage.class);    
    }

}
