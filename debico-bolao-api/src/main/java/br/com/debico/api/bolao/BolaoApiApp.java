package br.com.debico.api.bolao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "br.com.debico.api.bolao" })
public class BolaoApiApp {

    public BolaoApiApp() {

    }

    public static void main(String[] args) {
        SpringApplication.run(BolaoApiApp.class, args);
    }

}
