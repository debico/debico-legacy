package br.com.debico.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

@Entity
@Table(name = "tb_time")
public class Time implements Serializable, Comparable<Time> {

    private static final long serialVersionUID = 370535614639386653L;

    public static final Time TIME_A = new Time("Time A");
    public static final Time TIME_B = new Time("Time B");

    public Time(final String nome) {
        this.nome = nome;
    }
    
    public Time(final String nome, final String alias) {
        this(nome);
        this.alias = alias;
    }
    
    
    public Time(final int id, final String nome) {
        this.nome = nome;
        this.id = id;
    }

    public Time() {
        this.nome = "";
        this.alias = "";
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIME")
    private int id;

    @Column(name = "NM_TIME", nullable = false, length = 255)
    private String nome;

    @Column(name = "DC_BRASAO_IMG", length = 1000)
    private String brasaoFigura;
    
    @Column(name = "NM_ALIAS", length = 3)
    private String alias; 

    public void setBrasaoFigura(String brasaoFigura) {
        this.brasaoFigura = brasaoFigura;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBrasaoFigura() {
        return brasaoFigura;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getAlias() {
		return alias;
	}
    
    public void setAlias(String alias) {
		this.alias = alias;
	}

    @Override
    public String toString() {
        return toStringHelper(this).addValue(this.nome).toString();
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

        Time that = (Time) obj;

        return equal(this.nome, that.getNome()) && equal(this.id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.brasaoFigura, this.id, this.nome, this.alias);
    }

    public int compareTo(Time o) {
        return o.getNome().compareToIgnoreCase(this.nome);
    }

}
