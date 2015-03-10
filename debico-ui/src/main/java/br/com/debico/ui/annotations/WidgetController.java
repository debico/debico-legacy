package br.com.debico.ui.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <code>Marked Interface</code> para determinar se um controller é um Widget. 
 * <p/>
 * Um controller Widget não entrega templates HTML, mas sim provê interfaces JSON/XML para a comunicação HTTP via Ajax.
 * </p>
 * <code>Controllers</code> marcados por essa anotação são tratados por handlers de <i>exceptions</i>, entregando os detalhes de erro
 * no formato JSON, utilizando o modelo {@link br.com.debico.ui.handlers.ErrorInfo} 
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 * @see APIController
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WidgetController {

}
