package br.com.debico.core.support;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Converte um {@link Map} com <code>key : value</code> para
 * <code>x : key</code> e <code>y : value</code>.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * 
 */
public class ChartSeriesDataSerializer extends StdSerializer<Map<?, ?>> {

	private static final long serialVersionUID = -1310533958079057724L;
	
	private static final String X_AXIS = "x";
    private static final String Y_AXIS = "y";
    
    public ChartSeriesDataSerializer() {
        super(Map.class, false);
    }
    
    @Override
    public boolean isEmpty(Map<?, ?> value) {
        return (value == null || value.isEmpty());
    }

    @Override
    public void serialize(Map<?, ?> value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonGenerationException {
        
        if(value == null) {
            jgen.writeNull();
            return;
        }
        
        jgen.writeStartArray(value.size());
        
        final Iterator<?> it = value.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<?, ?> pairs = (Entry<?, ?>) it.next();
            Map<String, Object> axis = new HashMap<String, Object>();
            axis.put(X_AXIS, pairs.getKey());
            axis.put(Y_AXIS, pairs.getValue());
            
            jgen.writeObject(axis);
        }
        
        jgen.writeEndArray();
    }
    

}
