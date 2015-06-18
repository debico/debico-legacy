package br.com.debico.bolao.model.view;

import static com.google.common.base.Objects.equal;
import br.com.debico.model.PalpiteBase;
import br.com.debico.model.PartidaBase;
import br.com.debico.model.Placar;

import com.google.common.base.Objects;

public class PartidaRodadaPalpiteView extends PartidaBase {

    private static final long serialVersionUID = -5231593131298396713L;
        
    private PalpiteView palpite;
    
    private Integer idRodada;
    
    private Integer ordemRodada;
    
    public PartidaRodadaPalpiteView() {
        super();
        this.setPlacar(new Placar());
    }
   
    public PalpiteBase getPalpite() {
        return palpite;
    }
    
    public Integer getIdRodada() {
        return idRodada;
    }
    
    public Integer getOrdemRodada() {
		return ordemRodada;
	}
    
    public void setPalpite(PalpiteView palpite) {
		this.palpite = palpite;
	}
    
    public void setIdRodada(Integer idRodada) {
		this.idRodada = idRodada;
	}
    
    public void setOrdemRodada(Integer ordemRodada) {
		this.ordemRodada = ordemRodada;
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

        PartidaRodadaPalpiteView that = (PartidaRodadaPalpiteView) obj;

        return super.equals(obj) 
                && equal(this.palpite, that.getPalpite())
                && equal(this.idRodada, that.getIdRodada());
    }
    
    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hashCode(this.palpite, this.idRodada);
    }
    
    @Override
    public String toString() {
        return toStringHelper()
                .addValue(this.palpite)
                .addValue(this.idRodada)
                .omitNullValues().toString();
    }
}
