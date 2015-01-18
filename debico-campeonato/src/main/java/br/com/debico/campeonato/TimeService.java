package br.com.debico.campeonato;

import java.util.List;

import br.com.debico.model.Time;

/**
 * Interface para interação com o modelo de domínio de {@link Time}
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 1.2.0
 */
public interface TimeService {

    /**
     * Busca por um {@link Time} pelo nome ou parte dele.
     * <p/>
     * É limitada em 20 registros. Ideal para utilizar em conjunto com widgets de busca.
     * 
     * @param nomeParcial
     * @return lista com times encontrados pelo nome.
     */
    List<Time> procurarTimePorNome(String nomeParcial);
    
}
