package br.com.debico.ui.handlers;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

import java.io.Serializable;

import com.google.common.base.Objects;

/**
 * Estrutura de dados para armazenar as informações de Erro retornadas pelas API
 * Rest da aplicação.
 * 
 * @author ricardozanini
 * @see <a href="http://spring.io/blog/2013/11/01/exception-handling-in-spring-mvc">Exception Handling in Spring MVC</a>
 */
public final class ErrorInfo implements Serializable {

    private static final long serialVersionUID = 6156402281184534085L;
    
    public final String url;
    public final String ex;
    public String exClass;

    public ErrorInfo(final String url, final Exception ex) {
        this.url = url;
        this.ex = ex.getLocalizedMessage();
        this.exClass = ex.getClass().getName();
    }

    public ErrorInfo(final String url, final String message) {
        this.url = url;
        this.ex = message;
    }

    public String getEx() {
        return ex;
    }

    public String getUrl() {
        return url;
    }
    
    public String getExClass() {
        return exClass;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.ex, this.url);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj.getClass() != getClass()) {
            return false;
        }

        ErrorInfo that = (ErrorInfo) obj;

        return equal(this.ex, that.getEx()) && equal(this.url, that.getUrl());
    }

    @Override
    public String toString() {
        return toStringHelper(this).addValue(this.ex).addValue(this.url)
                .toString();
    }
}
