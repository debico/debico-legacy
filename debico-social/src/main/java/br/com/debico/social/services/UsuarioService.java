package br.com.debico.social.services;

import br.com.debico.model.Apostador;
import br.com.debico.social.CadastroApostadorException;
import br.com.debico.social.model.PasswordContext;

/**
 * Implementação dos Casos de Uso relacionados ao Usuário do Bolão.
 * 
 * @author ricardozanini
 * @since 1.0.0
 */
public interface UsuarioService {

    /**
     * Realiza o cadastro do Usuario.
     * 
     * @param email
     *            o email do usuário (único e identificador)
     * @param senha
     *            em formato aberto, conforme cadastro.
     */
    void cadastrarApostadorUsuario(final Apostador apostador,
            final String confirmacaoSenha) throws CadastroApostadorException;

    /**
     * Altera uma senha baseada em um contexto que contenha um token de
     * alteração válido. Normalmente o usuário não está autenticado (esqueci
     * minha senha).
     * 
     * @param passwordContext
     * @return true se alterado
     * @throws CadastroApostadorException
     *             senha atual não seja a senha do usuário, em caso de token
     *             inválido, nova senha e anterior não bater ou a senha não
     *             estar na política de segurança
     * @since 2.0.2
     */
    boolean alterarSenhaApostadorUsuario(PasswordContext passwordContext)
            throws CadastroApostadorException;


}
