package br.com.debico.ui.thymeleaf.processors;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractTextChildModifierAttrProcessor;

import br.com.debico.core.spring.security.ApostadorUserDetails;
import br.com.debico.ui.thymeleaf.UsuarioAuthUtils;

import com.google.common.base.Strings;

public class UsuarioAttrProcessor extends
	AbstractTextChildModifierAttrProcessor {
    public static final int ATTR_PRECEDENCE = 1000;
    public static final String ATTR_NAME = "usuario";

    public UsuarioAttrProcessor() {
	super(ATTR_NAME);
    }

    @Override
    public int getPrecedence() {
	return ATTR_PRECEDENCE;
    }

    @Override
    protected String getText(Arguments arguments, Element element,
	    String attributeName) {
	// exemplo: debico:apostador="id"
	final String attributeValue = element.getAttributeValue(attributeName);

	if (Strings.isNullOrEmpty(attributeValue)) {
	    return null;
	}

	final ApostadorUserDetails userDetails = UsuarioAuthUtils
		.apostadorAutenticado();
	return UsuarioAuthUtils.getUserDetailsProperty(userDetails,
		attributeValue);
    }
}
