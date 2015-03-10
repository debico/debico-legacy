package br.com.debico.bolao.model;

import static com.google.common.base.Objects.equal;
import br.com.debico.model.PalpiteBase;

import com.google.common.base.Objects;

/**
 * Utilizado para complementar os dados da View.
 * <p/>
 * Feito dessa forma para não descaracterizar a modelagem de Palpite utilizado no model principal.
 * 
 * @author ricardozanini
 *
 */
public class PalpiteView extends PalpiteBase {

	private static final long serialVersionUID = -4284856915528913135L;
	
	private int id;
	
	private int pontos;

	public PalpiteView() {

	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hashCode(this.id);
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

        PalpiteView that = (PalpiteView) obj;
        
        return equal(this.id, that.getId());
	}
}
