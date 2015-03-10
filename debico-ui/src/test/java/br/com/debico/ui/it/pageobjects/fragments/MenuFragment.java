package br.com.debico.ui.it.pageobjects.fragments;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import br.com.debico.ui.it.pageobjects.LoginPage;
import br.com.debico.ui.it.pageobjects.PerfilPage;

/**
 * Representa o menu da aplicação, uma vez o usuário autenticado.
 * <p/>
 * Deve ser associado à paginas que requerem autenticação.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
public class MenuFragment {
    
    private final WebDriver webDriver;

    public MenuFragment(final WebDriver webDriver) {
        this.webDriver = webDriver;
    }
    
    @CacheLookup
    @FindBy(id="logoutLink")
    private WebElement logoutLink;
    
    @CacheLookup
    @FindBy(id="perfilLink")
    private WebElement perfilLink;
    
    public LoginPage logout() {
       logoutLink.click();
        
       return PageFactory.initElements(webDriver, LoginPage.class);
    }
    
    public PerfilPage goToPerfil() {
        perfilLink.click();
        
        return PageFactory.initElements(webDriver, PerfilPage.class);
    }

}
