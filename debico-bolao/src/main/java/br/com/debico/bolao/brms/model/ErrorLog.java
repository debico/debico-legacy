package br.com.debico.bolao.brms.model;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

import static com.google.common.base.Preconditions.checkNotNull;

@Embeddable
public final class ErrorLog implements Serializable {

	private static final long serialVersionUID = -3057276992064140555L;

	@Column(name = "DH_ERR_MENSAGEM", length = 2048, nullable = true)
	private String mensagem;

	@Column(name = "DH_ERR_CLASS", length = 512, nullable = true)
	private String exception;

	@Column(name = "DH_ERR_STACKTRACE", length = 20480, nullable = true)
	private String stackTrace;

	@Transient
	private Throwable error;

	public ErrorLog() {

	}

	public ErrorLog(final Throwable throwable) {
		checkNotNull(throwable);

		this.mensagem = throwable.getLocalizedMessage();
		this.exception = throwable.getClass().getName();
		this.stackTrace = (ErrorLog.getStackTrace(throwable));
		this.error = throwable;
	}

	private static String getStackTrace(Throwable aThrowable) {
		Writer result = new StringWriter();
		PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

	public String getException() {
		return exception;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public Throwable getError() {
		return error;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(
				this.exception, 
				this.mensagem, 
				this.stackTrace,
				this.error);
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

		ErrorLog that = (ErrorLog) obj;

		return equal(this.error, that.getError());
	}

	@Override
	public String toString() {
		return toStringHelper(this)
				.addValue(this.exception)
				.addValue(this.mensagem)
				.omitNullValues()
				.toString();
	}

}
