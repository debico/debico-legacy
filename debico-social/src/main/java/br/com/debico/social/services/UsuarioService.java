package br.com.debico.social.services;

import org.springframework.social.connect.Connection;

import br.com.debico.model.Apostador;
import br.com.debico.model.Usuario;
import br.com.debico.social.CadastroApostadorException;
import br.com.debico.social.TokenSenhaInvalidoException;
import br.com.debico.social.UsuarioInexistenteException;
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
     * Cadastra um novo apostador que acaba de tentar entrar utilizando a rede
     * social ou relaciona o perfil dele com o cadastro existente pelo email
     * como chave.
     * 
     * @param userProfile
     * @since 2.0.5
     */
    Usuario cadastrarOuRelacionarApostadorUserProfile(Connection<?> connection)
	    throws CadastroApostadorException;

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

    /**
     * Envia um email com um link+token para o usuário que deseja resgatar essa
     * senha. <br/>
     * O token tem validade de um dia e depois disso será descartado. <br/>
     * Ao clicar no link enviado, o sistema deve redirecionar o usuário para uma
     * tela onde ele poderá cadastrar uma nova senha.
     * 
     * @param emailUsuario
     *            que deseja resgatar a sua senha.
     * @throws UsuarioInexistenteException
     *             caso o email enviado não esteja cadastrado.
     * @since 2.0.2
     * @see #alterarSenhaApostadorUsuario(PasswordContext)
     */
    void enviarTokenEsqueciMinhaSenha(String emailUsuario)
	    throws UsuarioInexistenteException;

    /**
     * Valida o token de "esqueci minha senha" enviado. Um token válido não pode
     * ter sido utilizado, deve estar dentro do periodo de validade e existir na
     * base de dados!
     * 
     * @param token
     * @throws TokenSenhaInvalidoException
     * @since 2.0.2
     */
    void validarTokenEsqueciMinhaSenha(String token)
	    throws TokenSenhaInvalidoException;

}
