package br.com.debico.social.dao;

import br.com.debico.model.Usuario;
import br.com.tecnobiz.spring.config.dao.Dao;

public interface UsuarioDAO extends Dao<Usuario, Integer> {

    Usuario selecionarPorEmail(String email);

    /**
     * Recupera a senha atual com base no email.
     * 
     * @param email
     * @return
     * @since 2.0.2
     */
    String recuperarSenhaAtual(String email);

    /**
     * Altera a senha do usuário com base no login/email.
     * <p/>
     * A senha deve estar criptografada.
     * 
     * @param email
     * @param novaSenha
     * @since 2.0.2
     */
    void alterarSenha(String email, String novaSenha);

}
