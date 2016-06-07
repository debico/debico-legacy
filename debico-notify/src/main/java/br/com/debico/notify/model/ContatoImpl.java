package br.com.debico.notify.model;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.io.UnsupportedEncodingException;

import javax.mail.internet.InternetAddress;

import com.google.common.base.Objects;

import br.com.debico.model.Apostador;
import br.com.debico.model.Usuario;

/**
 * Representa uma estrutura simples de contato.
 * 
 * @author ricardozanini
 *
 */
public class ContatoImpl implements Contato {

	private static final long serialVersionUID = 8280508219685200269L;

	private String email;

	private String nome;

	public ContatoImpl() {

	}

	public ContatoImpl(final Apostador apostador) {
		this(apostador.getEmail(), apostador.getNome());
	}

	public ContatoImpl(final Usuario usuario) {
		this(usuario.getEmail());
	}

	public ContatoImpl(final String email) {
		checkNotNull(emptyToNull(email), "Email obrigatorio");
		checkArgument(email.contains("@"), "Email invalido");
		this.email = email;
	}

	public ContatoImpl(final String email, final String nome) {
		this(email);
		checkNotNull(emptyToNull(nome), "Nome obrigatorio");

		this.email = email;
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public String getNome() {
		return nome;
	}

	public InternetAddress getInternetAddress() throws UnsupportedEncodingException {
		return new InternetAddress(this.email, this.nome);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.email, this.nome);
	}

	@Override
	public String toString() {
		if (emptyToNull(nome) == null) {
			return this.email;
		}

		return String.format("%s <%s>", this.getNome(), this.getEmail());
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

		ContatoImpl that = (ContatoImpl) obj;

		return equal(this.email, that.getEmail());
	}
}
