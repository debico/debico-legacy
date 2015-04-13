package br.com.debico.notify.dao.impl;

import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.notify.dao.TemplateDAO;
import br.com.debico.notify.model.EmailTemplate;
import br.com.debico.notify.model.Template;
import br.com.debico.notify.model.TipoNotificacao;
import br.com.tecnobiz.spring.config.dao.AbstractJPADao;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class TemplateDAOImpl extends AbstractJPADao<Template, Integer> implements TemplateDAO {
    
    public TemplateDAOImpl() {
        super(Template.class);
    }

    public EmailTemplate selecionarEmailTemplate(final TipoNotificacao tipoNotificacao) {
        final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery<EmailTemplate> query = cb.createQuery(EmailTemplate.class);
        final Root<EmailTemplate> root = query.from(EmailTemplate.class);
        
        query.select(root).where(cb.equal(root.get("tipoNotificacao"), tipoNotificacao));
        
        return DataAccessUtils.singleResult(getEntityManager().createQuery(query).getResultList());
    }

}
