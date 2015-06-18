package br.com.debico.model;

import static com.google.common.base.Objects.equal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.google.common.base.Objects;

/**
 * Representa a base de acertos do palpite para que seja utilizado para extrair
 * estatísticas de pontuação em outros cenários.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
@Embeddable
public class AcertosPontuacao implements Serializable {

    private static final long serialVersionUID = 3505802837134251609L;

    @Column(name = "ST_PLACAR", nullable = false)
    private Boolean placar;

    @Column(name = "ST_VENCEDOR", nullable = false)
    private Boolean vencedor;

    @Column(name = "ST_GOL", nullable = false)
    private Boolean gol;

    @Column(name = "ST_EMPATE", nullable = false)
    private Boolean empate;

    @Column(name = "ST_ERRADO", nullable = false)
    private Boolean errado;

    public AcertosPontuacao() {
	this.empate = false;
	this.errado = false;
	this.gol = false;
	this.placar = false;
	this.vencedor = false;
    }

    public Boolean isPlacar() {
	return placar;
    }

    public void setPlacar(Boolean placar) {
	this.placar = placar;
    }

    public Boolean isVencedor() {
	return vencedor;
    }

    public void setVencedor(Boolean vencedor) {
	this.vencedor = vencedor;
    }

    public Boolean isGol() {
	return gol;
    }

    public void setGol(Boolean gol) {
	this.gol = gol;
    }

    public Boolean isEmpate() {
	return empate;
    }

    public void setEmpate(Boolean empate) {
	this.empate = empate;
    }

    public Boolean isErrado() {
	return errado;
    }

    public void setErrado(Boolean errado) {
	this.errado = errado;
    }

    @Override
    public int hashCode() {
	return Objects.hashCode(this.empate, this.errado, this.gol,
		this.placar, this.vencedor);
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

	AcertosPontuacao that = (AcertosPontuacao) obj;

	return equal(this.empate, that.isEmpate())
		&& equal(this.errado, that.isErrado())
		&& equal(this.gol, that.isGol())
		&& equal(this.placar, that.isPlacar())
		&& equal(this.vencedor, that.isVencedor());
    }
}
