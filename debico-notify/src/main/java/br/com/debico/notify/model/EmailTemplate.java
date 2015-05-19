package br.com.debico.notify.model;

import java.beans.Transient;
import java.io.UnsupportedEncodingException;

import javax.mail.internet.InternetAddress;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.google.common.base.Objects;

@Entity
@DiscriminatorValue("E")
public final class EmailTemplate extends Template implements Contato {

    private static final long serialVersionUID = -6568092089395287068L;

    @Column(name="DC_EMAIL_REMETENTE", nullable = true, length = 1024)
    private String emailRemetente;
    
    @Column(name="NM_REMETENTE", nullable = true, length = 1024)
    private String nomeRemetente;
    
    @Column(name="NM_ASSUNTO", nullable = true, length = 1024)
    private String assunto;
    
    public EmailTemplate() {

    }
    
    public String getAssunto() {
        return assunto;
    }
    
    public String getNomeRemetente() {
        return nomeRemetente;
    }
    
    public String getEmailRemetente() {
		return emailRemetente;
	}
    
    @Transient
    public String getEmail() {
    	return this.getEmailRemetente();
    }
    
    @Transient
    public String getNome() {
    	return this.getNomeRemetente();
    }
    
    @Transient
    public InternetAddress getInternetAddress() throws UnsupportedEncodingException {
    	return new InternetAddress(this.emailRemetente, this.nomeRemetente);
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.assunto, this.nomeRemetente, this.emailRemetente) + super.hashCode();
    }

}
