package br.com.debico.core.dao.spring;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.context.annotation.Import;

/**
 * Determina qual JPADAO utilizar a depender do contexto.
 * 
 * @author r_fernandes
 *
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Import(DAOConfigSelector.class)
public @interface EnableDAO {
    
    /**
     * Tipo da configuração.
     * 
     * @return
     */
    TipoDAOConfig type() default TipoDAOConfig.JPA_EMBEDDED_DATABASE ;
}
