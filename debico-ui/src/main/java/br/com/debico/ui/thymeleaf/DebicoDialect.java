package br.com.debico.ui.thymeleaf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;
import org.thymeleaf.processor.IProcessor;

import br.com.debico.ui.thymeleaf.processors.UsuarioAttrProcessor;

/**
 * Dialeto base da aplicação.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.2
 * @see <a
 *      href="http://www.thymeleaf.org/doc/tutorials/2.1/extendingthymeleaf.html">Tutorial:
 *      Extending Thymeleaf</a>
 */
public class DebicoDialect extends AbstractDialect implements
	IExpressionEnhancingDialect {

    private static final String DEFAULT_PREFIX = "debico";
    private static final String USER_EXPRESSION_OBJECT_NAME = "usuario";
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(DebicoDialect.class);

    public DebicoDialect() {
	super();
    }

    @Override
    public String getPrefix() {
	return DEFAULT_PREFIX;
    }

    @Override
    public Map<String, Object> getAdditionalExpressionObjects(
	    IProcessingContext processingContext) {
	LOGGER.trace("[getAdditionalExpressionObjects] Adicionando a expressao de autenticacao do usuario.");
	final IContext context = processingContext.getContext();
	final IWebContext webContext = (context instanceof IWebContext ? (IWebContext) context
		: null);

	final Map<String, Object> objects = new HashMap<String, Object>(1, 1.0f);

	if (webContext != null) {
	    LOGGER.trace("[getAdditionalExpressionObjects] Recuperando o objeto de autenticacao");
	    objects.put(USER_EXPRESSION_OBJECT_NAME, UsuarioAuthUtils.apostadorAutenticado());
	}

	return objects;
    }
    
    @Override
    public Set<IProcessor> getProcessors() {
	final Set<IProcessor> processors = new HashSet<IProcessor>();
	processors.add(new UsuarioAttrProcessor());
	
        return processors;
    }

}
