package br.com.debico.model.campeonato;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

@Entity
@Table(name = "tb_rodada")
public class Rodada implements Serializable, Comparable<Rodada> {

	private static final long serialVersionUID = 4821429131125814443L;
	
	public static final String NOME_RODADA_FORMAT = "Rodada %s";
	
	private static final String NOME_DEFAULT = "Rodada \u00danica";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RODADA")
	private int id;

	@Column(name = "NM_RODADA", length = 255)
	private String nome;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_RANKING", nullable = false, unique = false)
	private Ranking ranking;
	
	@Column(name = "DT_INI_RODADA", nullable = true)
	@Temporal(TemporalType.DATE)
	@Basic(fetch=FetchType.LAZY)
	private Date dataInicioRodada;
	
	@Column(name = "DT_FIM_RODADA", nullable = true)
	@Temporal(TemporalType.DATE)
	@Basic(fetch=FetchType.LAZY)
	private Date dataFimRodada;
	
	@Column(name = "NU_ORDEM", nullable = true)
	private Integer ordem;
	
	public Rodada() {
		this.nome = "";
		this.ordem = 1;
	}
	
	public Rodada(final int id, final String nome) {
	    this();
        this.id = id;
        this.nome = nome;
    }
	
	public Rodada(final int id, final String nome, final Integer ordem) {
        this(id, nome);
	    this.ordem = ordem;
    }
	
	public Rodada(final int id) {
		this();
		this.id = id;
	}
	
	public Rodada(final String nome) {
	    this();
	    this.nome = nome;
	}
	
	public Rodada(final String nome, Integer ordem) {
        this();
        this.nome = nome;
        this.ordem = ordem;
    }
	
	public static Rodada novaRodadaNumerada(final int ordinal) {
	    return new Rodada(String.format(NOME_RODADA_FORMAT, ordinal), ordinal);
	}
	
	public static Rodada novaRodadaUnica(Ranking ranking) {
		Rodada rodada = new Rodada(NOME_DEFAULT, 1);
		rodada.setRanking(ranking);
		
		return rodada;
	}
	
	public int getId() {
		return id;
	}
	
	public Ranking getRanking() {
        return ranking;
    }

	public String getNome() {
		return nome;
	}
	
	/**
	 * Recupera o nome da rodada de acordo com '{@value #NOME_RODADA_FORMAT}'.
	 * 
	 * @since 2.0.3
	 * @return
	 */
	public String getNomeFormatado() {
        return String.format(NOME_RODADA_FORMAT, this.ordem);
    }
	
	public Date getDataFimRodada() {
		return dataFimRodada;
	}
	
	public Date getDataInicioRodada() {
		return dataInicioRodada;
	}
	
	public Integer getOrdem() {
        return ordem;
    }
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setRanking(Ranking ranking) {
        this.ranking = ranking;
    }
	
	public void setDataFimRodada(Date dataFimRodada) {
		this.dataFimRodada = dataFimRodada;
	}
	
	public void setDataInicioRodada(Date dataInicioRodada) {
		this.dataInicioRodada = dataInicioRodada;
	}
	
	public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

	public int compareTo(Rodada o) {
		return o.getOrdem().compareTo(this.ordem);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.nome);
	}

	@Override
	public String toString() {
		return toStringHelper(this)
				.addValue(this.id)
		        .addValue(this.nome)
				.toString();
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

		Rodada that = (Rodada) obj;

		return equal(this.id, that.getId());
	}

}
