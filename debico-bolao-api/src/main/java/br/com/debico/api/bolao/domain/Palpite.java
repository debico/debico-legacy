package br.com.debico.api.bolao.domain;

import static com.google.common.base.Objects.equal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import com.google.common.base.Objects;

@Entity
@Table(name = "tb_palpite")
public class Palpite extends PalpiteBase {

    private static final long serialVersionUID = -5751428596780423656L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_PARTIDA", nullable = false, unique = false)
    private Partida partida;

    @Id
    @Column(name = "ID_PALPITE")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NU_PONTOS", nullable = true)
    private Integer pontos;

    @Column(name = "DH_PALPITE", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraPalpite;

    @Column(name = "DH_COMPUTADO", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataComputado;

    public Palpite(final Apostador apostador, final Partida partida, final Placar placar) {
        super(apostador, placar);

        this.partida = partida;
        this.pontos = 0;
        this.dataHoraPalpite = new Date();
    }

    public Palpite() {
        super();
        this.pontos = 0;
        this.dataHoraPalpite = new Date();
    }

    public void somarPontos(final int pontos) {
        this.pontos += pontos;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDataComputado(final Date dataComputado) {
        this.dataComputado = new DateTime(dataComputado).toDate();
    }

    @Override
    public String toString() {
        return toStringHelper().add("ID", this.id).addValue(this.pontos).addValue(this.getApostador())
                .addValue(this.partida).toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hashCode(this.partida, this.pontos, this.dataHoraPalpite, this.dataComputado);
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

        Palpite that = (Palpite) obj;

        return equal(this.id, that.getId()) && equal(this.partida, that.getPartida())
                && equal(this.pontos, that.getPontos()) && equal(this.getApostador(), that.getApostador())
                && equal(this.isComputado(), that.isComputado()) && equal(this.getPlacar(), that.getPlacar());
    }
}
