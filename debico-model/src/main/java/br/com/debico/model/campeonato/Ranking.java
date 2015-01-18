package br.com.debico.model.campeonato;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.common.base.Objects;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TP_RANKING", discriminatorType = DiscriminatorType.STRING, length = 1)
@Table(name = "tb_ranking")
public abstract class Ranking implements Serializable {

	private static final long serialVersionUID = -7124610917606416675L;
	
	public static final String RANKING_TABELA = "T";
	public static final String RANKING_GRUPO = "G";

	@Id
    @Column(name = "ID_RANKING")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_FASE", nullable = false, unique = false)
	private FaseImpl fase;
	
    public Ranking() {
        
    }
    
    public Ranking(final FaseImpl fase) {
        this.fase = fase;
    }
    
    public int getId() {
        return id;
    }
    
    public FaseImpl getFase() {
        return fase;
    }
    
    public void setFase(FaseImpl fase) {
        this.fase = fase;
    }
    
    @Override
    public int hashCode() {
    	return Objects.hashCode(this.id, this.fase);
    }
    
    @Override
    public String toString() {
    	return toStringHelper(this)
    			.addValue(this.id)
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

		Ranking that = (Ranking) obj;

		return equal(this.id, that.getId());
    }

}
