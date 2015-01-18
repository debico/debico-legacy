package br.com.debico.core.spring.profiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

/**
 * Profile para ser utilizado em ambientes de desenvolvimento, normalmente associados à testes de integração, de unidade, etc.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Profile("dev")
public @interface Dev {

}
