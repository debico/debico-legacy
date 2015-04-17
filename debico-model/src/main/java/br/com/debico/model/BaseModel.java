package br.com.debico.model;

import java.io.Serializable;

/**
 * Interface comum aos modelos de domínio que precisam ser serializados.
 * 
 * @author ricardozanini
 *
 */
public interface BaseModel<I> extends Serializable {

	I getId();
	
	void setId(I id);
	
}
