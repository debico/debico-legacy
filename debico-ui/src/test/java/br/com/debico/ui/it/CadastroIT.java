package br.com.debico.ui.it;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import br.com.debico.ui.it.pageobjects.AbstractPage;
import br.com.debico.ui.it.pageobjects.CadastroPage;
import br.com.debico.ui.it.pageobjects.HomePage;
import br.com.debico.ui.it.pageobjects.LoginPage;
import br.com.debico.ui.it.pageobjects.SucessoPage;

public class CadastroIT extends IntegrationTestSupport {

    /**
     * Tenta efetuar um cadastro e se autenticar logo depois.
     */
    @Test
    public void testCadastrarELogar() {
        LOGGER.info("Iniciando os testes para Cadastrar e Logar na App");
        final String username = "brucebanner@shield.com";
        final String pass = "ihat3green";
        LOGGER.info("Tentando criar a pagina de Cadastro");
        final CadastroPage cadastroPage = this.buildPage(CadastroPage.PATH, CadastroPage.class);
        LOGGER.info("Pagina criada, entrando com os dados do Sr. Banner");
        cadastroPage.typeNome("Bruce Banner");
        cadastroPage.typeEmail(username);
        cadastroPage.typeSenha(pass);
        cadastroPage.typeConfirmaSenha(pass);
        LOGGER.info("Efetuando o post para cadastrar");
        final AbstractPage sucessPage = cadastroPage.submit();

        // cadastro realizado
        assertThat(sucessPage, IsInstanceOf.instanceOf(SucessoPage.class));

        // redirecionando para o login
        final LoginPage loginPage = ((SucessoPage) sucessPage).continuarSeAnonimo();

        // login com sucesso?
        assertThat(loginPage.loginAs(username, pass), IsInstanceOf.instanceOf(HomePage.class));
    }

    /**
     * Colocaremos dados incorretos no formulario
     */
    @Test
    public void testCadastroIncorreto() {
        LOGGER.info("Iniciando os testes de Cadastro Incorreto");
        final String username = "nataliaromanova@shield.com";
        final String pass = "ki55mylips";

        final WebDriver driver = this.buildDriver(CadastroPage.PATH);
        final CadastroPage cadastroPage = this.buildPage(driver, CadastroPage.class);

        cadastroPage.typeNome("Natalia Romanova");
        cadastroPage.typeEmail(username);
        cadastroPage.typeSenha(pass);
        cadastroPage.typeConfirmaSenha(pass + pass); // ops :)

        final AbstractPage sucessPage = cadastroPage.submit();

        // cadastro não realizado
        assertThat(sucessPage, IsInstanceOf.instanceOf(CadastroPage.class));
    }

}
