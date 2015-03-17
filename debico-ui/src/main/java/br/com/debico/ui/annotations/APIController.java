package br.com.debico.ui.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <code>Marked Interface</code> para determinar se um controller faz parte da API Web da aplicação. 
 * <p/>
 * Um controller de API não entrega templates HTML, mas sim provê interfaces JSON/XML para a comunicação HTTP via Ajax.
 * </p>
 * <code>Controllers</code> marcados por essa anotação são tratados por handlers de <i>exceptions</i>, entregando os detalhes de erro
 * no formato JSON, utilizando o modelo {@link br.com.debico.ui.handlers.ErrorInfo} 
 * <p/>
 * Normalmente esses controllers só podem ser acessados por meio de um mecanismo de autenticação e autorização.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 * @see WidgetController
 * @deprecated Agora utilizamos a anotação do Swagger para evitar redundância.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface APIController {

}
