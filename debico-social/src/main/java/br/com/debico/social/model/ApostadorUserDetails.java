package br.com.debico.social.model;

import java.util.Collection;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.social.security.SocialUserDetails;

/**
 * Implementação de
 * {@link org.springframework.security.core.userdetails.UserDetails} com
 * atributos adicionais para tratar de use cases na interface: Google Analytics
 * requer o ID do usuário, o header das páginas da interface do módulo Web pode
 * requerer o nome ao invés do email e etc.
 * <p/>
 * Para não ter que ir ao banco de dados a todo o momento, armazenamos esses
 * dados na sessão do usuário.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @see <a
 *      href="https://developers.google.com/analytics/devguides/collection/analyticsjs/user-id">User-ID
 *      - acompanhamento da Web (analytics.js)</a>
 * @see <a href=
 *      "http://stackoverflow.com/questions/8764545/how-to-get-active-users-userdetails"
 *      >How to get active user's UserDetails</a>
 */
public final class ApostadorUserDetails extends User implements SocialUserDetails,
	Comparable<ApostadorUserDetails> {

    private static final long serialVersionUID = 6525890008500713493L;

    private int id = 0;

    private int idApostador = 0;

    private String name;
    
    private String userId;

    public ApostadorUserDetails(String username, String password,
	    Collection<? extends GrantedAuthority> authorities) {
	super(username, password, authorities);
    }

    public ApostadorUserDetails(String username, String password,
	    boolean enabled, boolean accountNonExpired,
	    boolean credentialsNonExpired, boolean accountNonLocked,
	    Collection<? extends GrantedAuthority> authorities) {
	super(username, password, enabled, accountNonExpired,
		credentialsNonExpired, accountNonLocked, authorities);
    }

    /**
     * Cria uma instância de apostador que ainda não está autenticado. Ideal
     * para as páginas públicas que precisam de uma interface com o Apostador.
     * 
     * @return
     */
    public static ApostadorUserDetails apostadorAnonimo(AnonymousAuthenticationToken token) {
	return new ApostadorUserDetails(token.getName(), "", token.getAuthorities());
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setId(int id) {
	this.id = id;
    }

    public void setIdApostador(int idApostador) {
	this.idApostador = idApostador;
    }
    
    public void setUserId(String userId) {
	this.userId = userId;
    }

    /**
     * Identificador do Usuário.
     * 
     * @return
     */
    public int getId() {
	return id;
    }

    /**
     * Nome do Apostador
     * 
     * @return
     */
    public String getName() {
	return name;
    }

    /**
     * Id de Apostador.
     * 
     * @return
     */
    public int getIdApostador() {
	return idApostador;
    }
    
    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object rhs) {
	return super.equals(rhs);
    }

    @Override
    public int hashCode() {
	return super.hashCode();
    }

    @Override
    public int compareTo(ApostadorUserDetails o) {
	return o.getUsername().compareToIgnoreCase(this.getUsername());
    }
}
