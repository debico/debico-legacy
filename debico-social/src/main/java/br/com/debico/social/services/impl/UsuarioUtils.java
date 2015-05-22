package br.com.debico.social.services.impl;

import org.jasypt.util.password.PasswordEncryptor;

import br.com.debico.model.Usuario;

/**
 * Pacote de utilidades para tratar dos casos de uso de usuário.
 * 
 * @author ricardozanini
 * @since 2.0.2
 */
class UsuarioUtils {

    private UsuarioUtils() {

    }

    /**
     * Realiza a criptografia de acordo com o algoritimo fornecido pelo
     * framework <code>jasypt</code>.
     * 
     * @param usuario
     *            que possui a senha definida.
     * @see <a href="http://www.jasypt.org/howtoencryptuserpasswords.html">How
     *      to encrypt user passwords</a>.
     */
    public static void criptografarSenha(final PasswordEncryptor encryptor,
            final Usuario usuario) {
        usuario.setSenha(criptografarSenha(encryptor, usuario.getSenha()));
    }

    public static String criptografarSenha(final PasswordEncryptor encryptor,
            final String senha) {
        return encryptor.encryptPassword(senha);
    }


}
