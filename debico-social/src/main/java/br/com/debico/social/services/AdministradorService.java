package br.com.debico.social.services;

import java.util.List;

import br.com.debico.notify.model.Contato;

/**
 * Interface para UCs com relacionamento para os Administradores do sistema.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @since 2.0.5
 */
public interface AdministradorService {
    

    /**
     * Recupera uma lista de Administradores no formato de {@link Contato},
     * normalmente para ser utilizado dentro de um cenário de notificação
     * específica.
     * 
     * @return
     * @since 2.0.5
     */
    List<Contato> selecionarComoContato();

}
