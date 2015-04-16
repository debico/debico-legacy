package br.com.debico.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

import static com.google.common.base.Preconditions.checkNotNull;

import static com.google.common.base.Strings.emptyToNull;
import static com.google.common.base.Strings.isNullOrEmpty;

@Entity
@Table(name = "tb_apostador")
@JsonInclude(Include.NON_EMPTY)
public class Apostador implements Serializable, Comparable<Apostador> {

	private static final long serialVersionUID = -4119500865102301069L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_APOSTADOR")
	private int id;

	@Column(name = "NM_APOSTADOR", nullable = false, length = 500)
	private String nome;

	@Column(name = "NM_APELIDO", nullable = true, length = 128)
	private String apelido;

	@ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	@JoinColumn(name = "ID_USUARIO", nullable = false, updatable = false)
	private Usuario usuario;

	@Embedded
	private ApostadorOpcoes opcoes;

	public Apostador(final String nome) {
		checkNotNull(emptyToNull(nome));

		this.nome = nome;
		this.opcoes = new ApostadorOpcoes();
	}

	public Apostador(final String nome, final Usuario usuario) {
		this(nome);

		this.usuario = usuario;
	}

	public Apostador(final int id, final String nome, final String email) {
		this(nome, new Usuario(email));
		this.id = id;
	}

	public Apostador(final int id, final String nome) {
		this(nome);
		this.id = id;
	}

	// nao declarar as opcoes aqui. esperar o JPA popular o bean.
	public Apostador() {
		this.nome = "";
		this.usuario = new Usuario();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getEmail() {
		if (usuario != null && usuario.getEmail() != null) {
			return usuario.getEmail();
		} else {
			return "";
		}
	}

	public String getNome() {
		return nome;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public ApostadorOpcoes getOpcoes() {
		return opcoes;
	}

	public void setOpcoes(ApostadorOpcoes opcoes) {
		this.opcoes = opcoes;
	}

	@Transient
	@JsonIgnore
	public boolean possuiTimeCoracao() {
		return (this.getOpcoes().getTimeCoracao() != null && this.getOpcoes()
				.getTimeCoracao().getId() > 0);
	}

	/**
	 * Nome para aparecer em interfaces e saudações, também conhecido como
	 * <code>Display Name</code> ou <code>Screen Name</code>.
	 * 
	 * @return Apelido, se não for vazio. Do contrário, o nome
	 * @since 1.2.0
	 */
	@Transient
	@JsonIgnore
	public String getNomeTela() {
		return (isNullOrEmpty(apelido) ? (this.nome) : this.apelido);
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

		Apostador that = (Apostador) obj;

		return equal(this.id, that.getId()) && equal(this.nome, that.getNome());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.nome);
	}

	@Override
	public String toString() {
		return toStringHelper(this).addValue(id).addValue(nome)
				.omitNullValues().toString();
	}

	public int compareTo(Apostador o) {
		return o.getNome().compareTo(this.nome);
	}

}
