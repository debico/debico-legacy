package br.com.debico.ui.thymeleaf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;

import br.com.debico.social.model.ApostadorUserDetails;

/**
 * Utilidade para tratar dos cenários de autenticação
 * 
 * @author ricardozanini
 *
 */
public final class UsuarioAuthUtils {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(UsuarioAuthUtils.class);

    private UsuarioAuthUtils() {

    }

    /**
     * Recupera os dados do Apostador autenticado.
     * @return
     */
    public static ApostadorUserDetails apostadorAutenticado() {
	final Authentication auth = AuthUtils.getAuthenticationObject();

	if(auth != null && auth.getPrincipal() instanceof ApostadorUserDetails) {
	    return (ApostadorUserDetails) auth.getPrincipal();
	} else {
	    return ApostadorUserDetails
		    .apostadorAnonimo((AnonymousAuthenticationToken) auth);
	}
    }

    /**
     * Recupera a propriedade no formato String do apostador autenticado em questão. Nunca nulo.
     * 
     * @param apostador
     * @param property
     * @return
     */
    public static String getUserDetailsProperty(
	    ApostadorUserDetails apostador, String property) {
	try {
	    final BeanWrapper wrapper = new BeanWrapperImpl(apostador);
	    final Object value = wrapper.getPropertyValue(property);

	    if (value == null) {
		return "";
	    } else {
		return value.toString();
	    }
	} catch (BeansException ex) {
	    LOGGER.warn("Nao foi possivel recuperar a propriedade {} de {}",
		    property, apostador);
	}

	return "";
    }

    /**
     * Retorna o usuário logado. Nunca nulo. Mesmo se não estiver no contexto,
     * uma string em branco é retornada.
     * 
     * @return
     */
    public static String loginUsuarioAutenticado() {
	final Authentication auth = AuthUtils.getAuthenticationObject();

	if (auth == null) {
	    return "";
	}

	return auth.getName();
    }

    public static int userId() {
	final Authentication auth = AuthUtils.getAuthenticationObject();

	if (auth == null || !(auth.getPrincipal() instanceof ApostadorUserDetails)) {
	    return 0;
	}

	return ((ApostadorUserDetails) auth.getPrincipal()).getId();
    }

}
