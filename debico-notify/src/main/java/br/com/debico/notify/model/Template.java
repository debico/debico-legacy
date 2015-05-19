package br.com.debico.notify.model;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.debico.notify.jpa.converters.TipoNotificacaoConverter;
import br.com.debico.notify.services.TemplateContextoBuilder;

import com.google.common.base.Objects;

import static com.google.common.base.Objects.equal;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TP_TEMPLATE", discriminatorType = DiscriminatorType.STRING, length = 1)
@Table(name = "tb_msg_template")
public abstract class Template implements Serializable {

    private static final long serialVersionUID = -6974859939761054400L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TEMPLATE")
    private int id;

    @Column(name = "DC_CLASSPATH", nullable = false, length = 2048)
    private String classpathTemplate;

    @Column(name = "TP_NOTIFICACAO")
    @Convert(converter = TipoNotificacaoConverter.class)
    private TipoNotificacao tipoNotificacao;

    @Column(name = "DC_LINK_ACESSO")
    private String linkAcessoFormat;

    public Template() {

    }

    public final int getId() {
	return id;
    }

    public final String getClasspathTemplate() {
	return classpathTemplate;
    }

    public String getLinkAcessoFormat() {
	return linkAcessoFormat;
    }

    public TipoNotificacao getTipoNotificacao() {
	return tipoNotificacao;
    }

    @Transient
    public String getLinkAcesso(Map<String, Object> contextoLink) {
	return TemplateContextoBuilder.linkBuilder(this.linkAcessoFormat,
		contextoLink);
    }

    @Override
    public int hashCode() {
	return Objects.hashCode(this.classpathTemplate, this.id);
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

	Template that = (Template) obj;

	return equal(this.id, that.getId());
    }

}
