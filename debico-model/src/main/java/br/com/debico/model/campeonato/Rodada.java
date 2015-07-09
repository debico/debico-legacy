package br.com.debico.model.campeonato;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static com.google.common.base.Objects.equal;

@Entity
@Table(name = "tb_rodada")
public class Rodada extends AbstractRodada implements Serializable, Comparable<Rodada> {

	private static final long serialVersionUID = 4821429131125814443L;
	
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
		super();
		this.ordem = 1;
	}
	
	public Rodada(final int id, final String nome) {
	   super(id, nome);
    }
	
	public Rodada(final int id, final String nome, final Integer ordem) {
        this(id, nome);
	    this.ordem = ordem;
    }
	
	public Rodada(final int id) {
		super(id);
	}
	
	public Rodada(final String nome) {
	    super(nome);
	}
	
	public Rodada(final String nome, Integer ordem) {
        this(nome);
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
	
	public Ranking getRanking() {
        return ranking;
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
	    return super.hashCode();
	}

	@Override
	public String toString() {
	    return super.toString();
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

		return equal(this.getId(), that.getId());
	}

}
