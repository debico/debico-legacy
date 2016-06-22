package br.com.debico.ui.controllers;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Classe base para tratar das requisições de redirecionamento de acesso negado.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.3
 */
@Controller
public class SupportController extends AbstractViewController {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(SupportController.class);

    public SupportController() {

    }

    @Override
    protected String getViewName() {
	return "support";
    }

    @RequestMapping(value = "/support/error")
    public String redirectWithErrorCode(
	    @RequestParam(value = "msgCode", required = true) String msgCode, Model model) {
	LOGGER.debug("[] Erro com codigo: {}", msgCode);
	this.clearError();
	model.addAttribute("error", true);
	model.addAttribute("errorMsg", this.getMessage(msgCode));
	return this.getViewName();
    }

    // redirecionados do web.xml onde o HTTPErrorHandler nao conseguiu capturar.
    @RequestMapping(value = "/support/server_error")
    public String redirectFromServerError(HttpServletRequest req, Model model) {
	LOGGER.debug("[redirectFromServerError] Erro no servidor");
	this.clearError();
	final Throwable throwable = (Throwable) req
		.getAttribute("javax.servlet.error.exception");
	String errorMsg = null;

	if (throwable != null) {
	    errorMsg = throwable.getMessage();
	}

	model.addAttribute("errorCode",
		req.getAttribute("javax.servlet.error.status_code"));
	model.addAttribute("errorMsg", errorMsg);
	model.addAttribute("error", true);

	return this.getViewName();
    }

    @RequestMapping(value = "/support/not_found")
    public String redirectFromNotFound(HttpServletRequest request, Model model) {
	LOGGER.debug("[redirectFromNotFound] Pagina nao encontrada");
	this.clearError();
	model.addAttribute("errorCode", HttpStatus.NOT_FOUND.value());
	model.addAttribute("error", false);

	return this.getViewName();
    }

    // 401 & 403
    @RequestMapping(value = "/support/access_denied")
    public String redirectFromAccessDenied(HttpServletRequest request,
	    Model model) {
	final Object errorCode = request
		.getAttribute("javax.servlet.error.status_code");
	LOGGER.debug(
		"[redirectFromAccessDenied] Usuario tentando acessar um recurso negado. Resposta com: {}",
		errorCode);
	this.clearError();
	model.addAttribute("errorCode", errorCode);
	model.addAttribute("errorMsg",
		this.getMessage("support.accessDenied.html.msg"));
	model.addAttribute("error", false);
	LOGGER.debug("[redirectFromAccessDenied] Redirecionamento realizado com sucesso!");
	return this.getViewName();
    }

    @RequestMapping(value = "/support/session_exp")
    public String redirectFromSessionExpired(HttpServletRequest request,
	    Model model) {
	LOGGER.debug(
		"[redirectFromSessionExpired] Usuario com sessao expirada. Resposta com: {}",
		HttpStatus.UNAUTHORIZED);
	this.clearError();
	model.addAttribute("errorCode", HttpStatus.UNAUTHORIZED.value());
	model.addAttribute("errorMsg",
		this.getMessage("support.sessionExpired.html.msg"));
	model.addAttribute("error", false);
	model.addAttribute("sessionExpired", true);
	LOGGER.debug("[redirectFromSessionExpired] Redirecionamento realizado com sucesso!");
	return this.getViewName();
    }

}
