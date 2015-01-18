package br.com.debico.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.joda.time.DateTime;

import br.com.debico.model.campeonato.FaseImpl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TP_PARTIDA", discriminatorType = DiscriminatorType.STRING, length = 1)
@Table(name = "tb_partida")
@JsonIgnoreProperties({"fase"})
public class Partida extends PartidaBase {
    
    public static final String PARTIDA_COM_RODADA = "R";
    public static final String PARTIDA_COM_CHAVE = "C";

    private static final long serialVersionUID = -8257544700946262505L;

    @Column(name = "ST_PARTIDA", nullable = false, length = 2)
    @Enumerated(EnumType.STRING)
    private StatusPartida status = StatusPartida.ND;

    @ManyToOne(cascade = { (CascadeType.ALL) }, optional = true)
    @JoinColumn(nullable = true, name = "ID_PLACAR_PENALTI", unique = false)
    private Placar penaltis;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "ID_FASE", unique = false)
    private FaseImpl fase;
    
    @Column(name= "DH_COMPUTADA", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataComputada;

    public Partida() {
        super();
    }

    public Partida(final Time mandante, final Time visitante) {
        super(mandante, visitante);
    }

    public Partida(final Time mandante, final Time visitante, final Placar placar) {
        this(mandante, visitante);
        this.setPlacar(placar);
    }

    public StatusPartida getStatus() {
        return status;
    }

    public void setStatus(final StatusPartida status) {
        this.status = status;
    }

    public void setPenaltis(Placar penaltis) {
        this.penaltis = penaltis;
    }
    
    public void setFase(final FaseImpl fase) {
        this.fase = fase;
    }

    public Placar getPenaltis() {
        return penaltis;
    }
    
    public FaseImpl getFase() {
        return fase;
    }

    public void setDataComputada(final Date dataComputada) {
		this.dataComputada = new DateTime(dataComputada).toDate();
	}
    
    @Transient
    public boolean isDecisaoPenaltis() {
        return this.penaltis != null;
    }
    
	@Override
	public int hashCode() {
		return super.hashCode()
				+ Objects.hashCode(this.status, this.dataComputada);
	}

    @Override
    public String toString() {
        return super.toStringHelper()
                .addValue(this.status)
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

        Partida that = (Partida) obj;

        return  equal(this.getId(), that.getId())
                && equal(this.getMandante(), that.getMandante())
                && equal(this.getVisitante(), that.getVisitante())
                && equal(this.getPlacar(), that.getPlacar())
                && equal(this.status, that.getStatus());
    }

}
