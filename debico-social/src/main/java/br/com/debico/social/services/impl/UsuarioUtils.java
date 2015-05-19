package br.com.debico.social.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.debico.core.spring.security.ApostadorUserDetails;
import br.com.debico.model.Apostador;
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

    /**
     * Constrói a estrutura base de um {@link User} de acordo com a
     * especificação do Spring Security.
     * 
     * @param usuario
     * @return
     */
    public static User construirUsuario(final Apostador apostador) {
        final ApostadorUserDetails user = new ApostadorUserDetails(apostador
                .getUsuario().getEmail(), apostador.getUsuario().getSenha(),
                construirPerfil(apostador.getUsuario()));

        user.setId(apostador.getUsuario().getId());
        user.setName(apostador.getNome());
        user.setIdApostador(apostador.getId());

        return user;
    }

    /**
     * Constrói a estrutura de perfil de acordo com a especificação do Spring
     * Security.
     * 
     * @param usuario
     * @return
     */
    public static List<GrantedAuthority> construirPerfil(final Usuario usuario) {
        List<GrantedAuthority> perfis = new ArrayList<GrantedAuthority>();
        perfis.add(new SimpleGrantedAuthority(usuario.getPerfil()));

        return perfis;
    }

}
