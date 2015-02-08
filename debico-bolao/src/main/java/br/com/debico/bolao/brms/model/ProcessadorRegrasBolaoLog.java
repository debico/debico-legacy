package br.com.debico.bolao.brms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.model.campeonato.Chave;
import br.com.debico.model.campeonato.Rodada;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

@Entity
@Table(name="tb_log_processamento")
public class ProcessadorRegrasBolaoLog implements Serializable {

	private static final long serialVersionUID = 2287948179972262482L;

	@Id
	@Column(name = "ID_LOG_PROC")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "DH_PROCESSAMENTO", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraProcessamento;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_CAMPEONATO", nullable = false)
	private CampeonatoImpl campeonato;

	@ManyToOne(optional = true)
	@JoinColumn(name = "ID_CHAVE", nullable = true)
	private Chave chave;

	@ManyToOne(optional = true)
	@JoinColumn(name = "ID_RODADA", nullable = true)
	private Rodada rodada;

	@Column(name = "IN_SUCESSO", nullable = false)
	private boolean sucesso;

	@Embedded
	private ErrorLog errorLog;

	public ProcessadorRegrasBolaoLog() {
		this.sucesso = true;
		this.dataHoraProcessamento = new Date();
	}

	public ProcessadorRegrasBolaoLog(final Throwable throwable) {
		this();
		if(throwable != null) {
			this.sucesso = false;
			this.errorLog = new ErrorLog(throwable);
		}
	}

	public ProcessadorRegrasBolaoLog(final CampeonatoImpl campeonato, final Rodada rodada, final Throwable throwable) {
		this(throwable);
		this.campeonato = campeonato;
		this.rodada = rodada;
	}
	
	public ProcessadorRegrasBolaoLog(final CampeonatoImpl campeonato, final Rodada rodada) {
		this(campeonato, rodada, null);
	}

	public ProcessadorRegrasBolaoLog(final CampeonatoImpl campeonato, final Chave chave, final Throwable throwable) {
		this(throwable);
		this.campeonato = campeonato;
		this.chave = chave;
	}
	
	public ProcessadorRegrasBolaoLog(final CampeonatoImpl campeonato, final Chave chave) {
		this(campeonato, chave, null);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataHoraProcessamento() {
		return dataHoraProcessamento;
	}

	public void setDataHoraProcessamento(Date dataHoraProcessamento) {
		this.dataHoraProcessamento = dataHoraProcessamento;
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(CampeonatoImpl campeonato) {
		this.campeonato = campeonato;
	}

	public Chave getChave() {
		return chave;
	}

	public void setChave(Chave chave) {
		this.chave = chave;
	}

	public Rodada getRodada() {
		return rodada;
	}

	public void setRodada(Rodada rodada) {
		this.rodada = rodada;
	}

	public boolean isSucesso() {
		return sucesso;
	}

	public void setSucesso(boolean sucesso) {
		this.sucesso = sucesso;
	}

	public ErrorLog getErrorLog() {
		return errorLog;
	}

	public void setErrorLog(ErrorLog errorLog) {
		this.errorLog = errorLog;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(
				this.campeonato,
				this.dataHoraProcessamento,
				this.errorLog,
				this.id,
				this.sucesso);
	}
	
	@Override
	public String toString() {
		return toStringHelper(this)
				.addValue(this.sucesso)
				.addValue(this.dataHoraProcessamento)
				.addValue(this.campeonato)
				.addValue(this.rodada)
				.addValue(this.chave)
				.addValue(this.errorLog)
				.omitNullValues()
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

		ProcessadorRegrasBolaoLog that = (ProcessadorRegrasBolaoLog) obj;		
		
		return equal(this.sucesso, that.isSucesso())
				&& equal(this.id, that.getId())
				&& equal(this.dataHoraProcessamento, that.getDataHoraProcessamento())
				&& equal(this.errorLog, that.getErrorLog())
				&& equal(this.campeonato, that.getCampeonato());
	}

}
