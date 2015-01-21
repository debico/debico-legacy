package br.com.debico.model.campeonato;

import java.io.Serializable;
import java.util.Formatter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import br.com.debico.model.Time;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;

import static com.google.common.base.Preconditions.checkArgument;

@Entity
@Table(name = "tb_pontuacao")
@SqlResultSetMapping(name = "pontuacaoTimeRankingMapping", entities = { @EntityResult(entityClass = PontuacaoTime.class, fields = {
		@FieldResult(name = "aproveitamento", column = "NU_APROVEITAMENTO"),
		@FieldResult(name = "derrotas", column = "NU_DERROTAS"),
		@FieldResult(name = "empates", column = "NU_EMPATES"),
		@FieldResult(name = "golsContra", column = "NU_GOLS_CONTRA"),
		@FieldResult(name = "golsPro", column = "NU_GOLS_PRO"),
		@FieldResult(name = "jogos", column = "NU_JOGOS"),
		@FieldResult(name = "pontos", column = "NU_PONTOS"),
		@FieldResult(name = "posicao", column = "NU_POSICAO"),
		@FieldResult(name = "saldoGols", column = "NU_SALDO_GOLS"),
		@FieldResult(name = "statusPosicao", column = "ST_POSICAO"),
		@FieldResult(name = "vitorias", column = "NU_VITORIAS"),
		@FieldResult(name = "id", column = "ID_PONTUACAO"),
		@FieldResult(name = "ranking", column = "ID_RANKING"),
		@FieldResult(name = "time", column = "ID_TIME"),
		@FieldResult(name = "statusClassificacao.elite", column = "IN_ELITE"),
		@FieldResult(name = "statusClassificacao.inferior", column = "IN_INFERIOR"), }) })
public class PontuacaoTime implements Serializable, Comparable<PontuacaoTime> {

	private static final long serialVersionUID = -7957607429993707184L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PONTUACAO")
	private int id;

	@ManyToOne(optional = false, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TIME", referencedColumnName = "ID_TIME", nullable = false, unique = false, updatable = false)
	private Time time;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_RANKING", referencedColumnName = "ID_RANKING", nullable = false, unique = false, updatable = false)
	private Ranking ranking;

	@Embedded
	private StatusClassificacao statusClassificacao;

	@Column(name = "NU_POSICAO", nullable = false)
	private int posicao;

	@Column(name = "NU_PONTOS", nullable = false)
	private int pontos;

	@Column(name = "NU_JOGOS", nullable = false)
	private int jogos;

	@Column(name = "NU_VITORIAS", nullable = false)
	private int vitorias;

	@Column(name = "NU_EMPATES", nullable = false)
	private int empates;

	@Column(name = "NU_DERROTAS", nullable = false)
	private int derrotas;

	@Column(name = "NU_GOLS_PRO", nullable = false)
	private int golsPro;

	@Column(name = "NU_GOLS_CONTRA", nullable = false)
	private int golsContra;

	@Column(name = "NU_SALDO_GOLS", nullable = false)
	private int saldoGols;

	@Column(name = "NU_APROVEITAMENTO", scale = 5, precision = 2, nullable = false)
	private double aproveitamento;

	@Column(name = "ST_POSICAO", nullable = false)
	private int statusPosicao;

	public PontuacaoTime() {
		this.aproveitamento = 0D;
		this.derrotas = 0;
		this.empates = 0;
		this.golsContra = 0;
		this.golsPro = 0;
		this.jogos = 0;
		this.pontos = 0;
		this.saldoGols = 0;
		this.vitorias = 0;
		this.posicao = 0;
		this.statusPosicao = 0;
		this.statusClassificacao = new StatusClassificacao();
	}

	public PontuacaoTime(final Ranking ranking, final Time time) {
		this();
		this.ranking = ranking;
		this.time = time;
	}

	public int getId() {
		return id;
	}

	public Time getTime() {
		return time;
	}

	public double getAproveitamento() {
		return aproveitamento;
	}

	public int getDerrotas() {
		return derrotas;
	}

	public int getEmpates() {
		return empates;
	}

	public int getGolsContra() {
		return golsContra;
	}

	public int getGolsPro() {
		return golsPro;
	}

	public int getJogos() {
		return jogos;
	}

	public int getPontos() {
		return pontos;
	}

	public int getSaldoGols() {
		return saldoGols;
	}

	public int getVitorias() {
		return vitorias;
	}

	public Ranking getRanking() {
		return ranking;
	}

	public int getPosicao() {
		return posicao;
	}

	/**
	 * Retorna o status da posição do time no ranking, sendo:
	 * <p/>
	 * <code>1</code>: subiu <br/>
	 * <code>0</code>: manteve <br/>
	 * <code>-1</code>: desceu
	 * 
	 * @return
	 */
	public int getStatusPosicao() {
		return statusPosicao;
	}

	public void setAproveitamento(double aproveitamento) {
		this.aproveitamento = aproveitamento;
	}

	public void setDerrotas(int derrotas) {
		this.derrotas = derrotas;
	}

	public void setEmpates(int empates) {
		this.empates = empates;
	}

	public void setGolsContra(int golsContra) {
		this.golsContra = golsContra;
	}

	public void setGolsPro(int golsPro) {
		this.golsPro = golsPro;
	}

	public void setJogos(int jogos) {
		this.jogos = jogos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public void setSaldoGols(int saldoGols) {
		this.saldoGols = saldoGols;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public void setVitorias(int vitorias) {
		this.vitorias = vitorias;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRanking(Ranking ranking) {
		this.ranking = ranking;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public void setStatusPosicao(int statusPosicao) {
		checkArgument(StatusPosicao.validar(statusPosicao),
				"Valor da posicao incorreto");

		this.statusPosicao = statusPosicao;
	}

	public StatusClassificacao getStatusClassificacao() {
		return statusClassificacao;
	}

	public void setStatusClassificacao(StatusClassificacao statusClassificacao) {
		if (statusClassificacao != null) {
			this.statusClassificacao = statusClassificacao;
		}

	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this.id, this.getTime());
	}

	// http://stackoverflow.com/questions/18730828/tostring-override-formatting-with-specific-tabbing-using-stringbuilder
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		Formatter formatter = new Formatter(stringBuilder);

		String template = "\n %-15s %1s(S) %1s(O) %-1s(P) %-1s(J) %-1s(V) %-1s(E) %-1s(D) %-1s(GP) %-1s(GC) %-1s(SG) %-1.2f(A) %-1s(Elite) %-1s(Inf)"; // a
		// rough
		// guess!
		formatter.format(template, this.time, this.statusPosicao, this.posicao,
				this.pontos, this.jogos, this.vitorias, this.empates,
				this.derrotas, this.golsPro, this.golsContra, this.saldoGols,
				this.aproveitamento, this.statusClassificacao.isElite(),
				this.statusClassificacao.isInferior());

		String line = stringBuilder.toString();
		formatter.close();

		return line;
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

		PontuacaoTime that = (PontuacaoTime) obj;

		return equal(this.id, that.getId()) && equal(this.time, that.getTime());
	}

	/**
	 * Realiza a comparação do placar de acordo com {@link #getPosicao()}
	 * 
	 */
	public int compareTo(PontuacaoTime that) {
		return Integer.valueOf(this.posicao).compareTo(that.getPosicao());
	}

}
