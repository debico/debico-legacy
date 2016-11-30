package br.com.debico.ui.it;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import br.com.debico.ui.it.pageobjects.AbstractPage;
import br.com.debico.ui.it.pageobjects.HomePage;
import br.com.debico.ui.it.pageobjects.InternalPage;
import br.com.debico.ui.it.pageobjects.LoginPage;

public class LoginIT extends IntegrationTestSupport {

    @Test
    public void testLoginAs() {
        LoginPage page = this.buildPage(LoginPage.PATH, LoginPage.class);
        // efetua o login
        AbstractPage resultPage = page.loginAs(Constants.USER, Constants.PASS);
        // estamos na Home?
        assertThat(resultPage, IsInstanceOf.instanceOf(HomePage.class));
        // logout.
        LoginPage logoutPage = ((InternalPage) resultPage).logout();
        assertThat(logoutPage, IsInstanceOf.instanceOf(LoginPage.class));
        // webDriver.findElement(By.id("logoutAlert"));
    }

    @Test
    public void testLoginIncorrect() {
        LoginPage page = this.buildPage(LoginPage.PATH, LoginPage.class);
        assertThat(page.loginAs("whatever", "SECRET"), IsInstanceOf.instanceOf(LoginPage.class));
        // webDriver.findElement(By.id("errorAlert"));
    }

}
