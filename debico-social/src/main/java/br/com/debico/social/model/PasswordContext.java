package br.com.debico.social.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;
import com.google.common.base.Strings;

import static com.google.common.base.Objects.equal;
import static com.google.common.base.Objects.toStringHelper;

/**
 * Estrutura utilizada durante os casos de uso de Alteração de Senha e Esqueci
 * Minha Senha
 * 
 * @author ricardozanini
 *
 */
public final class PasswordContext implements Serializable {

    private static final long serialVersionUID = 5546868746326127564L;
    
    private String senhaAtual;
    private String novaSenha;
    private String confirmacaoSenha;
    private String tokenSenha;
    private String emailUsuario;

    public PasswordContext() {

    }
    
    public PasswordContext(final String tokenSenha) {
	this.tokenSenha = tokenSenha;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }

    public String getTokenSenha() {
        return tokenSenha;
    }

    public void setTokenSenha(String tokenSenha) {
        this.tokenSenha = tokenSenha;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    /**
     * A {@link #getNovaSenha()} é não nula e igual à
     * {@link #getConfirmacaoSenha()}?
     * 
     * @return
     */
    @JsonIgnore
    public boolean isSenhaNovaConfirma() {
        if (Strings.isNullOrEmpty(this.novaSenha)) {
            return false;
        }

        return this.novaSenha.equals(this.confirmacaoSenha);
    }
    
    @JsonIgnore
    public boolean hasToken() {
        return !Strings.isNullOrEmpty(this.tokenSenha);
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

        PasswordContext that = (PasswordContext) obj;

        // @formatter:off
        return equal(this.emailUsuario, that.getEmailUsuario())
                && equal(this.confirmacaoSenha, that.getConfirmacaoSenha())
                && equal(this.novaSenha, that.getNovaSenha())
                && equal(this.senhaAtual, that.getSenhaAtual())
                && equal(this.tokenSenha, that.getTokenSenha());
        // @formatter:on
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.confirmacaoSenha, this.emailUsuario,
                this.novaSenha, this.senhaAtual, this.tokenSenha);
    }

    @Override
    public String toString() {
        return toStringHelper(this).addValue(this.emailUsuario).toString();
    }

}
