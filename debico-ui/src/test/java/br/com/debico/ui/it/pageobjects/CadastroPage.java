package br.com.debico.ui.it.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CadastroPage extends AbstractPage {
    
    public static String PATH = "/public/cadastro/";

    @CacheLookup
    @FindBy(id="nome")
    private WebElement nomeLocator;
    
    @CacheLookup
    @FindBy(id="usuario.email")
    private WebElement emailLocator;
    
    @CacheLookup
    @FindBy(id="usuario.senha")
    private WebElement passwordLocator;
    
    @CacheLookup
    @FindBy(id="confirmar_password")
    private WebElement confirmaPassLocator;
    
    @CacheLookup
    @FindBy(id="cadastrar")
    private WebElement cadastrarBtnLocator;
    
    @CacheLookup
    @FindBy(id="voltar")
    private WebElement voltarBtnLocator;
    
    public CadastroPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public String getTitle() {
        return "Cadastro";
    }
    
    public CadastroPage typeNome(String nome) {
        return (CadastroPage) this.clearAndType(nomeLocator, nome);
    }
    
    public CadastroPage typeEmail(String email) {
        return (CadastroPage) this.clearAndType(emailLocator, email);
    }
    
    public CadastroPage typeSenha(String password) {
        return (CadastroPage) this.clearAndType(passwordLocator, password);
    }
    
    public CadastroPage typeConfirmaSenha(String password) {
        return (CadastroPage) this.clearAndType(confirmaPassLocator, password);
    }

    public AbstractPage submit() {
        cadastrarBtnLocator.click();
        
        if(isActualPage()) {
            return this;
        }
        
        return PageFactory.initElements(getWebDriver(), SucessoPage.class);    
    }
    
    public LoginPage voltar() {
        voltarBtnLocator.click();
        
        return PageFactory.initElements(getWebDriver(), LoginPage.class);
    }
    
   

}
