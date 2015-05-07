package br.com.debico.ui.thymeleaf;

import java.util.Map;
import java.util.Set;

import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;
import org.thymeleaf.doctype.resolution.IDocTypeResolutionEntry;
import org.thymeleaf.doctype.translation.IDocTypeTranslation;
import org.thymeleaf.processor.IProcessor;

public class AuthDialect implements IExpressionEnhancingDialect {

	public AuthDialect() {

	}

	@Override
	public String getPrefix() {
		return "auth";
	}

	@Override
	public Set<IProcessor> getProcessors() {
		return null;
	}

	@Override
	public Map<String, Object> getExecutionAttributes() {
		return null;
	}

	@Override
	public Set<IDocTypeTranslation> getDocTypeTranslations() {
		return null;
	}

	@Override
	public Set<IDocTypeResolutionEntry> getDocTypeResolutionEntries() {
		return null;
	}

	@Override
	public Map<String, Object> getAdditionalExpressionObjects(
			IProcessingContext processingContext) {
		return null;
	}

}
