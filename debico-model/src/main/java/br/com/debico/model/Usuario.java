package br.com.debico.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.joda.time.DateTime;

import com.google.common.base.Objects;
import com.google.common.base.Strings;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable, Comparable<Usuario> {

    private static final long serialVersionUID = 4181501368783801877L;

    public static final String PERFIL_DEFAULT = "ROLE_USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private int id;

    @Column(name = "DC_EMAIL", length = 1024, unique = false, nullable = true, updatable = true)
    private String email;

    @Column(name = "DC_SENHA", length = 64, nullable = false, updatable = false)
    private String senha;

    @Column(name = "NM_SOCIAL_ID", length = 255, nullable = true, updatable = true)
    private String socialUserId;

    @Column(name = "NM_SOCIAL_NET", length = 255, nullable = true, updatable = true)
    private String socialNetwork;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "DH_ULTIMO_LOGIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoLogin;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "DH_CADASTRO", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCadastro;

    @Column(name = "NM_PERFIL", nullable = false, length = 255, updatable = false)
    private String perfil;

    public Usuario() {
        this.email = "";
        this.senha = "";
        this.perfil = PERFIL_DEFAULT;
        this.dataCadastro = new Date();
    }

    public Usuario(final String email) {
        this();
        this.email = email;
    }

    public Usuario(final String email, final String senha) {
        this(email);
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Date getUltimoLogin() {
        return new DateTime(ultimoLogin).toDate();
    }

    public void setUltimoLogin(Date ultimoLogin) {
        this.ultimoLogin = ultimoLogin;
    }

    public Date getDataCadastro() {
        return new DateTime(dataCadastro).toDate();
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    /**
     * Id do profile do usuário no serviço de rede social (Facebook, Google)
     * 
     * @return
     */
    public String getSocialUserId() {
        return socialUserId;
    }

    public void setSocialUserId(String socialUserId) {
        this.socialUserId = socialUserId;
    }

    /**
     * Combinação de {@link #getSocialNetwork()} e {@link #getSocialUserId()}.
     * Por exemplo: facebook-10983629. Sim, tem o "tracinho".
     * 
     * @return
     * @since 2.0.5
     */
    @Transient
    public String getSocialUserKey() {
        return String.format("%s-%s", this.socialNetwork, this.socialUserId);
    }

    /**
     * Recupera o que o nosso usuário utiliza para fazer a autenticação na
     * aplicação. Normalmente o email, se não for possível utilizamos
     * {@link #getSocialUserKey()}.
     * 
     * @return
     * @since 2.0.5
     */
    @Transient
    public String getUserName() {
        if (Strings.isNullOrEmpty(this.email)) {
            return this.getSocialUserKey();
        }

        return this.getEmail();
    }

    /**
     * Serviço de rede social que o usuário utilizou para fazer o login
     * 
     * @return
     */
    public String getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    @Override
    public String toString() {
        return toStringHelper(this).addValue(this.email).addValue(this.perfil)
                .toString();
    }

    public int compareTo(Usuario o) {
        if (Strings.isNullOrEmpty(o.getEmail())) {
            return -1;
        }
        return o.getEmail().compareTo(this.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.email, this.senha, this.ultimoLogin,
                this.perfil, this.dataCadastro);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (obj.getClass() != getClass()) {
            return false;
        }

        Usuario that = (Usuario) obj;

        return equal(this.email, that.getEmail());
    }

}
