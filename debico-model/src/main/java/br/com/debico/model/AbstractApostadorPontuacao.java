package br.com.debico.model;

import java.io.Serializable;
import java.util.Formatter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.model.campeonato.CampeonatoImpl;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

import static com.google.common.base.Objects.equal;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AbstractApostadorPontuacao implements Serializable, Comparable<AbstractApostadorPontuacao> {

    private static final long serialVersionUID = 4664473075086303426L;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_APOSTADOR", nullable = false, unique = false)
    private Apostador apostador;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CAMPEONATO", nullable = false, unique = false)
    private CampeonatoImpl campeonato;

    @Column(name = "NU_PONTOS", nullable = false)
    private Integer pontosTotal;

    @Column(name = "NU_PLACAR", nullable = false)
    private Integer placar;

    @Column(name = "NU_VENCEDOR", nullable = false)
    private Integer vencedor;

    @Column(name = "NU_GOLS", nullable = false)
    private Integer gols;

    @Column(name = "NU_EMPATE", nullable = false)
    private Integer empates;

    @Column(name = "NU_ERRADOS", nullable = false)
    private Integer errados;

    public AbstractApostadorPontuacao() {
        this.pontosTotal = 0;
        this.empates = 0;
        this.gols = 0;
        this.placar = 0;
        this.vencedor = 0;
        this.errados = 0;
    }

    public AbstractApostadorPontuacao(final Apostador apostador) {
        this();
        this.apostador = apostador;
    }

    public AbstractApostadorPontuacao(final Apostador apostador,
            final CampeonatoImpl campeonato) {
        this(apostador);
        this.campeonato = campeonato;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(final CampeonatoImpl campeonato) {
        this.campeonato = campeonato;
    }

    public Apostador getApostador() {
        return apostador;
    }

    public void setApostador(Apostador apostador) {
        this.apostador = apostador;
    }

    public Integer getPontosTotal() {
        return pontosTotal;
    }

    public void setPontosTotal(final Integer pontosTotal) {
        this.pontosTotal = pontosTotal;
    }

    public void somarPontosTotal(final int pontos) {
        this.pontosTotal += pontos;
    }

    public Integer getGols() {
        return gols;
    }

    public void setGols(Integer gols) {
        this.gols = gols;
    }

    public Integer getPlacar() {
        return placar;
    }

    public void setPlacar(Integer placar) {
        this.placar = placar;
    }

    public Integer getVencedor() {
        return vencedor;
    }

    public void setVencedor(Integer vencedor) {
        this.vencedor = vencedor;
    }

    public Integer getEmpates() {
        return empates;
    }

    public void setEmpates(Integer empates) {
        this.empates = empates;
    }

    public Integer getErrados() {
        return errados;
    }

    public void setErrados(Integer errados) {
        this.errados = errados;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.apostador, this.pontosTotal, this.empates,
                this.errados, this.gols, this.placar, this.vencedor);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Formatter formatter = new Formatter(stringBuilder);

        String template = "\n %-20s %-1s(P) %-1s(A) %-1s(V) %-1s(E) %-1s(G) %-1s(R)";
        formatter.format(template, this.apostador, this.pontosTotal,
                this.placar, this.vencedor, this.empates, this.gols,
                this.errados);

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

        AbstractApostadorPontuacao that = (AbstractApostadorPontuacao) obj;

        return equal(this.apostador, that.getApostador())
                && equal(this.campeonato, that.getCampeonato())
                && equal(this.pontosTotal, that.getPontosTotal());
    }
    
    /**
     * Critério de desempate:
     * <ol>
     * <li>Quantidade de placar na cabe&ccedil;a.</li>
     * <li>Quantidade de empates.</li>
     * <li>Quantidade de vit&oacute;rias.</li>
     * <li>Quantidade de gols.</li>
     * <li>Menor quantidade de palpites errados.</li>
     * <li>Se mesmo assim empatar, vai ter que ser no palitinho (na
     * pr&aacute;tica o sistema ordena por ordem alfab&eacute;tica).</li>
     * </ol>
     */
    public int compareTo(AbstractApostadorPontuacao o) {
        return ComparisonChain.start()
                .compare(o.getPontosTotal(), this.getPontosTotal())
                .compare(o.getPlacar(), this.getPlacar())
                .compare(o.getEmpates(), this.getEmpates())
                .compare(o.getVencedor(), this.getVencedor())
                .compare(o.getGols(), this.getGols())
                .compare(this.getErrados(), o.getErrados())
                .compare(o.getApostador(), this.getApostador()).result();
    }

}
