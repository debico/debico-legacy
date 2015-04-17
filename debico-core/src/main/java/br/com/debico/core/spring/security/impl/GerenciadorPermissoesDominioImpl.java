package br.com.debico.core.spring.security.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

import br.com.debico.core.spring.security.GerenciadorPermissoesDominio;
import br.com.debico.model.BaseModel;

@Named
class GerenciadorPermissoesDominioImpl implements GerenciadorPermissoesDominio {

	@Inject
	private MutableAclService aclService;

	public GerenciadorPermissoesDominioImpl() {

	}

	@Override
	public void adicionarPermissaoAdministrador(String login, BaseModel<?> model) {
		this.adicionarPermissao(login, model, BasePermission.ADMINISTRATION);
	}

	@Override
	public void adicionarPermissaoLeitura(String login, BaseModel<?> model) {
		this.adicionarPermissao(login, model, BasePermission.READ);
	}

	private void adicionarPermissao(String login, BaseModel<?> model,
			Permission p) {
		// Prepare the information we'd like in our access control entry (ACE)
		ObjectIdentity oi = new ObjectIdentityImpl(BaseModel.class, model);
		Sid sid = new PrincipalSid(login);

		// Create or update the relevant ACL
		MutableAcl acl = null;
		try {
			acl = (MutableAcl) aclService.readAclById(oi);
		} catch (NotFoundException nfe) {
			acl = aclService.createAcl(oi);
		}

		// Now grant some permissions via an access control entry (ACE)
		acl.insertAce(acl.getEntries().size(), p, sid, true);
		aclService.updateAcl(acl);
	}

}
