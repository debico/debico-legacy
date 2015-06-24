package br.com.debico.social.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.debico.core.helpers.WebUtils;
import br.com.debico.model.Apostador;
import br.com.debico.model.BaseModel;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

/**
 * Liga é a referência para um grupo de apostadores que decidiram se juntar para
 * brincar de bolão, criar campeonatos customizados ou qualquer outra atividade
 * no site.
 * 
 * @author ricardozanini
 * @since 2.0.0
 */
@Entity
@Table(name = "tb_liga")
public class Liga implements BaseModel<Long>, Serializable, Comparable<Liga> {

	private static final long serialVersionUID = 1877249715720845337L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_LIGA")
	private long id;

	@Column(name = "NM_LIGA", nullable = false, length = 500)
	private String nome;

	@Column(name = "IN_ATIVA", nullable = false)
	private boolean ativa;

	@Column(name = "DC_PERMALINK", nullable = false, unique = true, length = 1000)
	private String permalink;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_APOSTADOR_ADMIN", nullable = false)
	private Apostador administrador;

	public Liga() {
		// importante para nao aceitar nulos.
		this.id = 0;
	}
	
	public Liga(final long id) {
		this();
		this.id = id;
	}

	public Liga(final String nome) {
		this();
		this.nome = nome;
		this.permalink = WebUtils.toPermalink(nome);
		this.ativa = true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public Apostador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Apostador administrador) {
		this.administrador = administrador;
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

		Liga that = (Liga) obj;

		// @formatter:off
		return equal(this.id, that.getId()) 
				&& equal(this.nome, that.getNome())
				&& equal(this.permalink, that.getPermalink());
		// @formatter:on
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.nome, this.permalink);
	}

	@Override
	public String toString() {
		// @formatter:off
		return toStringHelper(this)
				.addValue(this.id)
				.addValue(this.nome)
				.addValue(this.permalink)
				.toString();
		// @formatter:on
	}

	@Override
	public int compareTo(Liga o) {
		return this.nome.compareToIgnoreCase(o.getNome());
	}

}
