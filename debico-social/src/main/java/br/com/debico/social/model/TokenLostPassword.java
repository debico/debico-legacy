package br.com.debico.social.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.joda.time.DateTime;

import br.com.debico.model.Usuario;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

/**
 * Estrutura de dados do Token do caso de uso "Esqueci Minha Senha"
 * 
 * @author ricardozanini
 * @since 2.0.2
 */
@Entity
@Table(name = "tb_token_password")
public class TokenLostPassword implements Serializable {

    private static final long serialVersionUID = -8846565237116258640L;

    /**
     * Padrão de horas de validade do token.
     */
    private static final int VALIDADE_HORAS = 24;

    @Id
    @Column(name = "ID_TOKEN", updatable = false, length = 128)
    private String token;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = { CascadeType.ALL })
    @JoinColumn(name = "ID_USUARIO", nullable = false, updatable = false)
    private Usuario usuario;

    @Column(name = "DH_VALIDADE", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date validade;

    @Column(name = "IN_UTILIZADO", updatable = true)
    private boolean utilizado;

    /**
     * Cria uma nova instância com os dados pré-definidos de acordo com as
     * regras da estrutura do modelo:
     * <ol>
     * <li>Token gerado via UUID</li>
     * <li>Data de validade do token 24 a partir de agora</li>
     * <li>Flag de utilizado como falso</li> </lo>
     * 
     * @return token criado de acordo com a regra acima descrita.
     */
    public static final TokenLostPassword newInstance(final Usuario usuario) {
        final TokenLostPassword token = new TokenLostPassword();
        token.setValidade(new DateTime(new Date()).plusHours(VALIDADE_HORAS)
                .toDate());
        token.setUtilizado(false);
        token.setUsuario(usuario);
        token.setToken(UUID.randomUUID().toString());

        return token;
    }

    public TokenLostPassword() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getValidade() {
        return new Date(validade.getTime());
    }

    public void setValidade(Date validade) {
        this.validade = new Date(validade.getTime());
    }

    public boolean isUtilizado() {
        return utilizado;
    }

    public void setUtilizado(boolean utilizado) {
        this.utilizado = utilizado;
    }
    
    /**
     * Essa instância é um token válido?
     * Um token válido não deve ter sido utilizado e deve estar dentro do prazo de validade. 
     * 
     * @return
     */
    @Transient
    public boolean isValido() {
	if(this.isUtilizado()) {
	    return false;
	}
	
	return new DateTime(this.validade).isAfterNow();
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

        TokenLostPassword that = (TokenLostPassword) obj;

        // @formatter:off
        return equal(this.token, that.getToken()) &&
                equal(this.usuario, that.getUsuario());
        // @formatter:on
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.token, this.usuario);
    }

    @Override
    public String toString() {
        // @formatter:off
        return toStringHelper(this)
                .omitNullValues()
                .addValue(this.token)
                .addValue(this.validade)
                .addValue(this.usuario)
                .addValue(this.utilizado)
                .toString();
        // @formatter:on
    }

}
