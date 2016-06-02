package br.com.debico.model.campeonato;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.debico.model.Time;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TP_CAMPEONATO", discriminatorType = DiscriminatorType.STRING, length = 2)
@Table(name = "tb_campeonato")
@JsonIgnoreProperties({ "times" })
public class CampeonatoImpl implements Campeonato, Serializable {

    private static final long serialVersionUID = 2502288528179431926L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CAMPEONATO")
    private int id;

    @Column(name = "NM_CAMPEONATO", nullable = false, length = 500)
    private String nome;

    @Column(name = "IN_ATIVO", nullable = false)
    private boolean ativo;

    @Column(name = "IN_FINALIZADO", nullable = false)
    private boolean finalizado;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    @JoinTable(name = "tb_campeonato_time", joinColumns = { @JoinColumn(name = "ID_CAMPEONATO", referencedColumnName = "ID_CAMPEONATO", nullable = true, unique = false) }, inverseJoinColumns = { @JoinColumn(name = "ID_TIME", referencedColumnName = "ID_TIME", nullable = true, unique = false) })
    private Set<Time> times;

    @Column(name = "DC_PERMALINK", nullable = false, unique = true, length = 1000)
    private String permalink;

    @Column(name = "DC_IMG_CAPA", nullable = true, length = 1000)
    private String imagemCapa;

    @Column(name = "TP_CAMPEONATO", nullable = false, length = 2, insertable = false, updatable = false)
    private String tipo;

    @Column(name = "DT_INICIO", nullable = true)
    private Date dataInicio;

    @Column(name = "DT_FIM", nullable = true)
    private Date dataFim;

    @OneToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "campeonato")
    @JoinColumn(name = "ID_CAMPEONATO", nullable = true)
    private ParametrizacaoCampeonato parametrizacao;

    public CampeonatoImpl() {
	this.times = new HashSet<Time>();
	this.nome = "";
	this.permalink = "";
	this.ativo = false;
	this.finalizado = false;
    }

    public CampeonatoImpl(final String nome) {
	this();
	this.nome = nome;
	// this.permalink = WebUtils.toPermalink(nome);
    }

    public CampeonatoImpl(final int id) {
	this();
	this.id = id;
    }

    public CampeonatoImpl(final int id, final String nome,
	    final String permalink, final String tipo) {
	this();
	this.id = id;
	this.nome = nome;
	this.permalink = permalink;
	this.tipo = tipo;
    }

    public int getId() {
	return id;
    }

    public String getNome() {
	return nome;
    }

    public String getPermalink() {
	return permalink;
    }

    public boolean isAtivo() {
	return ativo;
    }

    public boolean isFinalizado() {
	return finalizado;
    }

    public Set<Time> getTimes() {
	return Collections.unmodifiableSet(this.times);
    }

    public String getImagemCapa() {
	return imagemCapa;
    }

    public String getTipo() {
	return tipo;
    }

    public ParametrizacaoCampeonato getParametrizacao() {
	return this.parametrizacao;
    }

    @Override
    public Date getDataFim() {
	return this.dataFim;
    }

    @Override
    public Date getDataInicio() {
	return this.dataInicio;
    }

    public void addTime(final Time time) {
	checkNotNull(time);
	this.times.add(time);
    }

    public void addTime(final List<Time> times) {
	checkNotNull(times);
	this.times.addAll(times);
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public void setAtivo(boolean ativo) {
	this.ativo = ativo;
    }

    public void setFinalizado(boolean finalizado) {
	this.finalizado = finalizado;
    }

    public void setId(int id) {
	this.id = id;
    }

    public void setPermalink(String permalink) {
	this.permalink = permalink;
    }

    public void setImagemCapa(String imagemCapa) {
	this.imagemCapa = imagemCapa;
    }

    public void setParametrizacao(ParametrizacaoCampeonato parametrizacao) {
	this.parametrizacao = parametrizacao;
    }

    public void setDataFim(Date dataFim) {
	this.dataFim = dataFim;
    }
    
    public void setDataInicio(Date dataInicio) {
	this.dataInicio = dataInicio;
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

	CampeonatoImpl that = (CampeonatoImpl) obj;

	return equal(this.id, that.getId()) && equal(this.nome, that.getNome());
    }

    @Override
    public int hashCode() {
	return Objects.hashCode(this.ativo, this.finalizado, this.id,
		this.nome, this.permalink);
    }

    @Override
    public String toString() {
	return toStringHelper(this).addValue(this.id).addValue(this.nome)
		.addValue(this.permalink).omitNullValues().toString();
    }

}
