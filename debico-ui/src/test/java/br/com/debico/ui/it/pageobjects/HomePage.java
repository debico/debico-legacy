package br.com.debico.ui.it.pageobjects;

import org.openqa.selenium.WebDriver;

public class HomePage extends InternalPage {
    
    public static String PATH = "/";

	public HomePage(final WebDriver webDriver) {
	    super(webDriver);
	}
	
	@Override
	public String getTitle() {
	    return "Bem Vindo";
	}
}
