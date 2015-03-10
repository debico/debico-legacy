package br.com.debico.ui.it;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.PageFactory;

import br.com.debico.ui.it.pageobjects.AbstractPage;
import br.com.debico.ui.it.pageobjects.HomePage;
import br.com.debico.ui.it.pageobjects.InternalPage;
import br.com.debico.ui.it.pageobjects.LoginPage;
import static org.junit.Assert.assertThat;

public class LoginIT extends IntegrationTestSupport {

	@Test
	public void testLoginAs() {
		WebDriver webDriver = new HtmlUnitDriver();
		
		webDriver.get(super.buildURL(LoginPage.PATH));
		
		LoginPage page = PageFactory.initElements(webDriver, LoginPage.class);
		// efetua o login
		AbstractPage resultPage = page.loginAs(Constants.USER, Constants.PASS);
		// estamos na Home?
		assertThat(resultPage, IsInstanceOf.instanceOf(HomePage.class));
		// logout.
		LoginPage logoutPage = ((InternalPage)resultPage).logout();
		assertThat(logoutPage, IsInstanceOf.instanceOf(LoginPage.class));
		
		webDriver.findElement(By.id("logoutAlert"));
	}
	
	@Test
    public void testLoginIncorrect() {
        WebDriver webDriver = new HtmlUnitDriver();
        
        webDriver.get(this.buildURL(LoginPage.PATH));
        
        LoginPage page = PageFactory.initElements(webDriver, LoginPage.class);
        
        assertThat(page.loginAs("whatever", "SECRET"), IsInstanceOf.instanceOf(LoginPage.class));
        
        webDriver.findElement(By.id("errorAlert"));
    }

}
