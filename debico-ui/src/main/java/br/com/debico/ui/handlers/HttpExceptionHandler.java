package br.com.debico.ui.handlers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Trata das exceptions ocorridas na camada Http. Qualquer view é tratada por
 * aqui, com qualquer content type.
 * 
 * @author ricardozanini
 * @see <a
 *      href=http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvchttp
 *      ://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc">Exception
 *      Handling in Spring MVC</a>
 * @since 1.0.0
 */
@ControllerAdvice
public class HttpExceptionHandler {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(HttpExceptionHandler.class);

    /*
     * A exception do rememberme é CookieTheftException, podemos utilizar a mais
     * generica: RememberMeAuthenticationException
     */
    /**
     * @see <a
     *      href="http://blog.triptiny.com/2012/09/20/how-to-handle-cookietheftexception-in-spring-remember-me-authentication-elegantly/">How
     *      to Handle CookieTheftException Elegantly</a>
     * @see <a
     *      href="http://stackoverflow.com/questions/20053107/spring-security-invalid-remember-me-token-series-token-mismatch-implies-previ">Spring
     *      Security Invalid remember-me token (Series/token) mismatch. Implies
     *      previous cookie theft attack</a>
     * @return
     */
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(RememberMeAuthenticationException.class)
    public ModelAndView handleCookieExpiredException() {
	LOGGER.debug("[handleCookieExpiredException] Usuario com cookie espirado");
	final ModelAndView modelAndView = new ModelAndView("support");

	modelAndView.addObject("sessionExpired", true);

	return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ RuntimeException.class })
    public ModelAndView hadleRuntimeException(HttpServletRequest req,
	    Exception ex) {
	// TODO: enviar email.

	LOGGER.error("[hadleRuntimeException] erro de RUNTIME: '{}'",
		ex.getMessage(), ex);

	final ModelAndView modelAndView = new ModelAndView("support");
	modelAndView.addObject("errorMsg", ex.getLocalizedMessage());
	modelAndView.addObject("error", true);
	modelAndView.addObject("errorUrl", req.getRequestURL().toString());
	modelAndView.addObject("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);
	
	return modelAndView;
    }
}
