package br.com.debico.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.joda.time.DateTime;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.common.collect.ComparisonChain;

import static com.google.common.base.Objects.equal;

import static com.google.common.base.Preconditions.checkNotNull;

@MappedSuperclass
public class PartidaBase implements Serializable, Comparable<PartidaBase> {

    private static final long serialVersionUID = 5084233650725169459L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PARTIDA")
    private int id;

    @Column(name = "DC_LOCAL", length = 255)
    private String local;

    @Column(name = "DH_PARTIDA", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataHoraPartida;

    @ManyToOne(cascade = { (CascadeType.ALL) }, optional = false)
    @JoinColumn(nullable = false, name = "ID_TIME_MANDANTE", unique = false)
    private Time mandante;

    @ManyToOne(cascade = { (CascadeType.ALL) }, optional = false)
    @JoinColumn(nullable = false, name = "ID_TIME_VISITANTE", unique = false)
    private Time visitante;

    @ManyToOne(cascade = { (CascadeType.ALL) }, optional = true)
    @JoinColumn(nullable = true, name = "ID_PLACAR", unique = false)
    private Placar placar;

    @Column(name = "IN_COMPUTADA", nullable = false)
    private boolean computadaCampeonato = false;

    public PartidaBase() {
        this.computadaCampeonato = false;
    }

    public PartidaBase(final Time mandante, final Time visitante) {
        this();
        checkNotNull(mandante);
        checkNotNull(visitante);
        this.mandante = mandante;
        this.visitante = visitante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocal() {
        return local;
    }

    public Date getDataHoraPartida() {
        return new DateTime(dataHoraPartida).toDate();
    }

    public Time getMandante() {
        return mandante;
    }

    public Time getVisitante() {
        return visitante;
    }

    public void setDataHoraPartida(Date dataHoraPartida) {
        this.dataHoraPartida = dataHoraPartida;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setMandante(Time mandante) {
        this.mandante = mandante;
    }

    public void setVisitante(Time visitante) {
        this.visitante = visitante;
    }

    public boolean isComputadaCampeonato() {
        return computadaCampeonato;
    }

    public void setComputadaCampeonato(boolean computada) {
        this.computadaCampeonato = computada;
    }

    public final void setPlacar(Placar placar) {
        if (this.placar != null && placar != null) {
            this.placar.setId(placar.getId());
            this.placar.setGolsMandante(placar.getGolsMandante());
            this.placar.setGolsVisitante(placar.getGolsVisitante());
        } else {
            this.placar = placar;
        }
    }

    public Placar getPlacar() {
        return placar;
    }

    /**
     * A partida já começou? Compara a data da partida com a data de agora.
     * 
     * @since 2.0.4
     * @return
     */
    @Transient
    public boolean isIniciada() {
        return new DateTime(this.dataHoraPartida).isAfterNow();
    }

    public int compareTo(PartidaBase o) {
        return ComparisonChain.start()
                .compare(this.dataHoraPartida, o.getDataHoraPartida())
                .compare(this.mandante, o.getMandante()).result();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id, this.local, this.mandante,
                this.visitante, this.dataHoraPartida, this.computadaCampeonato);
    }

    protected ToStringHelper toStringHelper() {
        return Objects
                .toStringHelper(this)
                .add("em ", this.local)
                .addValue(
                        String.format("%s X %s", this.mandante, this.visitante))
                .addValue(this.placar).omitNullValues();
    }

    @Override
    public String toString() {
        return this.toStringHelper().toString();
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

        PartidaBase that = (PartidaBase) obj;

        return equal(this.id, that.getId())
                && equal(this.mandante, that.getMandante())
                && equal(this.visitante, that.getVisitante());
    }
}
