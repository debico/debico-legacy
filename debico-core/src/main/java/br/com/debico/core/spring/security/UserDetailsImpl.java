package br.com.debico.core.spring.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * Implementação de {@link org.springframework.security.core.userdetails.UserDetails} com atributos adicionais para tratar de use cases na interface:
 * Google Analytics requer o ID do usuário, o header das páginas da interface do módulo Web pode requerer o nome ao invés do email e etc.
 * <p/>
 * Para não ter que ir ao banco de dados a todo o momento, armazenamos esses dados na sessão do usuário.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 * @see <a href="https://developers.google.com/analytics/devguides/collection/analyticsjs/user-id">User-ID - acompanhamento da Web (analytics.js)</a>
 */
public final class UserDetailsImpl extends User implements Comparable<UserDetailsImpl> {
    
    private static final long serialVersionUID = 6525890008500713493L;

    private int id;
    
    private int idApostador;
    
    private String name;

    public UserDetailsImpl(String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public UserDetailsImpl(String username, String password, boolean enabled,
            boolean accountNonExpired, boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
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
    public boolean equals(Object rhs) {
        return super.equals(rhs);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public int compareTo(UserDetailsImpl o) {
        return o.getUsername().compareToIgnoreCase(this.getUsername());
    }
}
