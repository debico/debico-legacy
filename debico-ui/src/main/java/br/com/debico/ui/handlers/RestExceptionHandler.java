package br.com.debico.ui.handlers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.debico.core.DebicoException;

import com.wordnik.swagger.annotations.Api;

@ControllerAdvice(annotations = { Api.class })
public class RestExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    public RestExceptionHandler() {

    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DebicoException.class)
    public @ResponseBody ErrorInfo handleServicesException(HttpServletRequest req, Exception ex) {
        LOGGER.error(
                "[handleServicesException] Erro ao processar o request na camada de servicos: '{}'",
                ex.getMessage(), ex);

        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }
    
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public @ResponseBody ErrorInfo handleRuntimeException(HttpServletRequest req, Exception ex) {
        LOGGER.error(
                "[handleRuntimeException] Erro Fatal ao processar o request: '{}'",
                ex.getMessage(), ex);

        return new ErrorInfo(req.getRequestURL().toString(), ex);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({SQLException.class,DataAccessException.class})
    public @ResponseBody ErrorInfo hadleDatabaseException(HttpServletRequest req, Exception ex) {
        LOGGER.error(
                "[handleServicesException] Erro com o banco de dados: '{}'",
                ex.getMessage(), ex);

        return new ErrorInfo(req.getRequestURL().toString(),
                "Erro ao acessar o nosso banco de dados. Tente novamente mais tarde.");
    }

}
