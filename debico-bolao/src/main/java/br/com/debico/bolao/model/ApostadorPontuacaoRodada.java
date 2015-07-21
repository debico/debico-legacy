package br.com.debico.bolao.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.debico.model.AbstractApostadorPontuacao;
import br.com.debico.model.Apostador;
import br.com.debico.model.ApostadorPontuacao;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.model.campeonato.Rodada;

/**
 * Representa uma instância de {@link ApostadorPontuacao} indexado pela rodada.
 * 
 * @author ricardozanini
 * @since 2.0.3
 */
@Entity
@Table(name = "tb_apostador_pontuacao_rodada")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ApostadorPontuacaoRodada extends AbstractApostadorPontuacao {

    private static final long serialVersionUID = 8585174244663603518L;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "ID_RODADA")
    private Rodada rodada;

    public ApostadorPontuacaoRodada() {
	super();
    }

    public ApostadorPontuacaoRodada(Apostador apostador) {
	super(apostador);
    }

    public ApostadorPontuacaoRodada(Apostador apostador,
	    CampeonatoImpl campeonato) {
	super(apostador, campeonato);
    }

    public Rodada getRodada() {
	return rodada;
    }

    public void setRodada(Rodada rodada) {
	this.rodada = rodada;
    }

    @Override
    public String toString() {
	return super.toString();
    }

    @Override
    public int hashCode() {
	return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	return super.equals(obj);
    }

}
