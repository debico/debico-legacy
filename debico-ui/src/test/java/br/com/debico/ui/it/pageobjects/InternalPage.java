package br.com.debico.ui.it.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import br.com.debico.ui.it.pageobjects.fragments.MenuFragment;

/**
 * Compõe os fragmentos e casos de uso de uma página interna.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
public abstract class InternalPage extends AbstractPage {
    
    private final MenuFragment menuFragment;

    public InternalPage(WebDriver webDriver) {
        super(webDriver);
        
        this.menuFragment = PageFactory.initElements(webDriver, MenuFragment.class);
    }
    
    public MenuFragment getMenuFragment() {
        return menuFragment;
    }
    
    public LoginPage logout() {
        return menuFragment.logout();
    }
    
    public PerfilPage acessarPerfil() {
        return menuFragment.goToPerfil();
    }

}
