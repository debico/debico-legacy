package br.com.debico.ui.it;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;

import br.com.debico.ui.it.pageobjects.AbstractPage;
import br.com.debico.ui.it.pageobjects.HomePage;
import br.com.debico.ui.it.pageobjects.LoginPage;
import br.com.debico.ui.it.pageobjects.PerfilPage;
import br.com.debico.ui.it.pageobjects.SucessoPage;

public class PerfilIT extends IntegrationTestSupport {

    public PerfilIT() {

    }

    @Test
    public void testAlterarApelido() {
        LoginPage loginPage = this.buildPage(LoginPage.PATH, LoginPage.class);
        HomePage homePage = (HomePage) loginPage.loginAs(Constants.USER, Constants.PASS);
        PerfilPage perfilPage = homePage.acessarPerfil();

        perfilPage.typeApelido("Darth Vadder");
        AbstractPage sucessPage = perfilPage.submit();

        assertThat(sucessPage, IsInstanceOf.instanceOf(SucessoPage.class));
    }

}
