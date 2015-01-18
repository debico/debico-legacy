package br.com.debico.notify.jpa.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.debico.notify.model.TipoNotificacao;

@Converter
public class TipoNotificacaoConverter implements AttributeConverter<TipoNotificacao, Integer> {

	public TipoNotificacaoConverter() {

	}
	
	public Integer convertToDatabaseColumn(TipoNotificacao attribute) {
		return attribute.getId();
	}
	
	public TipoNotificacao convertToEntityAttribute(Integer dbData) {
		for (TipoNotificacao type : TipoNotificacao.values()) {
            if(type.getId() == dbData) {
                return type;
            }
        }
        
        throw new IllegalArgumentException("Unknown database value:" + dbData);
	}

}
