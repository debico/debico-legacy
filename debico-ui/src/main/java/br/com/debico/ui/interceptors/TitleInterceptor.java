package br.com.debico.ui.interceptors;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Adiciona um título à pagina.
 * 
 * @author ricardozanini
 * @since 1.1.0
 */
public class TitleInterceptor extends HandlerInterceptorAdapter implements
		MessageSourceAware {

	public static final String POSFIX_TITLE_CODE = ".html.title";

	private MessageSource messageSource;

	public TitleInterceptor() {

	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if (modelAndView != null) {
			modelAndView.addObject("title", getTitle(modelAndView));
		}
	}

	protected String getTitle(ModelAndView modelAndView) {
		final String title = 
				messageSource.getMessage(
						String.format("%s%s", modelAndView.getViewName(), POSFIX_TITLE_CODE), 
						null, 
						Locale.getDefault());

		return messageSource.getMessage("base.html.title", new Object[] { title }, Locale.getDefault());
	}

}
