package br.com.debico.ui.controllers;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.web.servlet.ModelAndView;

import br.com.debico.core.DebicoException;
import br.com.debico.ui.utils.MessageSourceUtils;

public abstract class AbstractViewController implements MessageSourceAware {

    private MessageSource messageSource;

    private ModelAndView modelAndView;

    public static final String MENSAGEM_VIEW_NAME = "mensagem";

    @PostConstruct
    public void init() {
        this.modelAndView = new ModelAndView(getViewName());
    }

    protected abstract String getViewName();

    public ModelAndView getModelAndView() {
        return modelAndView;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(final String messageCode) {
        return this.messageSource.getMessage(messageCode, null,
                Locale.getDefault());
    }

    public String getMessage(final String messageCode, Object... args) {
        return this.messageSource.getMessage(messageCode, args,
                Locale.getDefault());
    }

    /**
     * Quando a aplicação mantém o estado da página, se faz necessário limpar os
     * dados de erro.
     */
    public void clearError() {
        modelAndView.addObject("error", false);
        modelAndView.addObject("errorMsg", "");
        modelAndView.setViewName(getViewName());
    }

    public void setError(final String errorMessageCode) {
        modelAndView.addObject("error", true);
        modelAndView.addObject("errorMsg", getMessage(errorMessageCode));
        modelAndView.setViewName(getViewName());
    }

    public void setError(final Throwable e) {
        modelAndView.addObject("error", true);
        modelAndView.addObject("errorMsg", e.getLocalizedMessage());
        modelAndView.setViewName(getViewName());
    }

    /**
     * Adiciona as variáveis de erro ao ModelAndView para serem tratados pelo
     * template. Adicione a div
     * <code>&lt;div th:replace="fragments/alert :: error" /&gt;</code> no seu
     * template.
     * 
     * @param e
     */
    public void setError(final DebicoException e) {
        modelAndView.addObject("error", true);
        modelAndView.addObject("errorMsg",
                MessageSourceUtils.getMessageFromEx(e, messageSource));

        modelAndView.setViewName(getViewName());
    }

    /**
     * Modifica o nome da view para a tela de sucesso e adiciona uma mensagem de
     * acordo com o messages.properties.
     * 
     * @param messageCode
     */
    public void redirecionarSucesso(final String messageCode) {
        modelAndView.setViewName(MENSAGEM_VIEW_NAME);
        modelAndView.addObject("mensagem", getMessage(messageCode));
        modelAndView.addObject("sucesso", true);
    }

    public void resetViewName() {
        modelAndView.setViewName(getViewName());
    }

    protected void addObject(final String key, final Object model) {
        modelAndView.addObject(key, model);
    }

    /**
     * As vezes você quer modiciar o title da página just for fun. Coloque {0}
     * no messages.properties.
     * 
     * @param titleParam
     */
    protected void addTitleParam(Object titleParam) {
        this.addObject("title_param", titleParam);
    }
}
