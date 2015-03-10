package br.com.debico.ui.interceptors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.debico.campeonato.services.CampeonatoService;

/**
 * Adiciona objetos de domínio em telas que precisam de menu.
 * <p/>
 * Adicionar os seguintes paths como sem menu:
 * 
 * <ul>
 * <li>/public/**</li>
 * <li>/api/**</li>
 * <li>/login/**</li>
 * </ul>
 * 
 * @author ricardozanini
 *
 */
public class MenuInterceptor extends HandlerInterceptorAdapter {

	@Inject
	@Named("campeonatoServiceImpl")
	private CampeonatoService campeonatoService;

	public static final String CAMPEONATOS_MODEL_KEY = "campeonatos";

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if (modelAndView != null) {
			modelAndView.addObject(CAMPEONATOS_MODEL_KEY,
					campeonatoService.selecionarCampeonatosAtivos());
		}
	}

}
