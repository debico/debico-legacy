package br.com.debico.ui.it.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Representa a nossa página de login.
 * 
 * @since 1.2.0
 * @author ricardozanini
 * @see <a href="https://code.google.com/p/selenium/wiki/PageObjects">Page Objects</a>
 * @see <a href="https://code.google.com/p/selenium/wiki/PageFactory">PageFactory</a>
 */
public class LoginPage extends AbstractPage  {
	
	public LoginPage(final WebDriver webDriver) {
		super(webDriver);
	}
	
	public static String PATH = "/login";
	
	@Override
	public String getTitle() {
	    return "Login";
	}
	
	@CacheLookup
	@FindBy(id="username")
	private WebElement usernameLocator;
	
	@CacheLookup
	@FindBy(id="password")
	private WebElement passwordLocator;
	
	@CacheLookup
	@FindBy(id="login")
	private WebElement loginButtonLocator;
	
    public LoginPage typeUsername(String username) {
    	usernameLocator.sendKeys(username);

        return this;
    }

    public LoginPage typePassword(String password) {
    	passwordLocator.sendKeys(password);

        return this;    
    }

    public AbstractPage submitLogin() {
        loginButtonLocator.submit();
        
        // Não rolou.
        if(isActualPage()) {
            return this;
        }
        
        return PageFactory.initElements(getWebDriver(), HomePage.class);    
    }

    public AbstractPage loginAs(String username, String password) {
        typeUsername(username);
        typePassword(password);
        
        return submitLogin();
    }
	

}
