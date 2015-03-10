package br.com.debico.ui.it;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.PageFactory;

import br.com.debico.ui.it.pageobjects.AbstractPage;
import br.com.debico.ui.it.pageobjects.HomePage;
import br.com.debico.ui.it.pageobjects.LoginPage;
import br.com.debico.ui.it.pageobjects.PerfilPage;
import br.com.debico.ui.it.pageobjects.SucessoPage;
import static org.junit.Assert.assertThat;

public class PerfilIT extends IntegrationTestSupport {
    
    private LoginPage loginPage;
    
    private WebDriver webDriver;

    public PerfilIT() {
        
    }
    
    @Before
    public void setUp() {
        webDriver = new HtmlUnitDriver();
        webDriver.get(super.buildURL(LoginPage.PATH));
        
        loginPage = PageFactory.initElements(webDriver, LoginPage.class);
    }
    
    @Test
    public void testAlterarApelido() {
        HomePage homePage = (HomePage) loginPage.loginAs(Constants.USER, Constants.PASS);
        PerfilPage perfilPage = homePage.acessarPerfil();
        
        perfilPage.typeApelido("Darth Vadder");
        AbstractPage sucessPage = perfilPage.submit();
        
        assertThat(sucessPage, IsInstanceOf.instanceOf(SucessoPage.class));
    }

}
