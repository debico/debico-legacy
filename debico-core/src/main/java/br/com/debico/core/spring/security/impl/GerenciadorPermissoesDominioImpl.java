package br.com.debico.core.spring.security.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.core.spring.security.GerenciadorPermissoesDominio;
import br.com.debico.model.BaseModel;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class GerenciadorPermissoesDominioImpl implements GerenciadorPermissoesDominio {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GerenciadorPermissoesDominioImpl.class);

	@Inject
	private MutableAclService aclService;

	public GerenciadorPermissoesDominioImpl() {

	}

	@Override
	public void adicionarPermissaoAdministrador(String login, BaseModel<?> model) {
		LOGGER.debug(
				"[adicionarPermissao] Concedendo a permissao de admin para {} em {}",
				login, model);
		this.adicionarPermissao(login, model, BasePermission.ADMINISTRATION);
	}

	@Override
	public void adicionarPermissaoLeitura(String login, BaseModel<?> model) {
		LOGGER.debug(
				"[adicionarPermissao] Concedendo a permissao de leitura para {} em {}",
				login, model);
		this.adicionarPermissao(login, model, BasePermission.READ);
	}

	@Override
	public void adicionarPermissao(String login, BaseModel<?> model,
			boolean admin) {
		if (admin) {
			this.adicionarPermissaoAdministrador(login, model);
		} else {
			this.adicionarPermissaoLeitura(login, model);
		}
	}

	@Override
	public void removerPermissao(String login, BaseModel<?> model) {
		ObjectIdentity oid = new ObjectIdentityImpl(model.getClass(),
				model.getId());
		MutableAcl acl = (MutableAcl) aclService.readAclById(oid);

		if (acl != null) {
			Sid sid = new PrincipalSid(login);
			List<AccessControlEntry> entries = acl.getEntries();

			for (int i = 0; i < entries.size(); i++) {
				if (entries.get(i).getSid().equals(sid)) {
					acl.deleteAce(i);
				}
			}

			aclService.updateAcl(acl);
			LOGGER.debug(
					"[removerPermissao] Permissao de {} removida com sucesso do objeto {}",
					login, model);
		}
	}

	@Override
	public void removerTodasPermissoes(BaseModel<?> model) {
		ObjectIdentity oid = new ObjectIdentityImpl(model.getClass(),
				model.getId());
		aclService.deleteAcl(oid, false);
		LOGGER.debug(
				"[removerTodasPermissoes] Todas as permissoes de {} removidas",
				model);
	}

	private void adicionarPermissao(String login, BaseModel<?> model,
			Permission p) {
		// Prepare the information we'd like in our access control entry (ACE)
		ObjectIdentity oi = new ObjectIdentityImpl(model.getClass(),
				model.getId());
		Sid sid = new PrincipalSid(login);

		// Create or update the relevant ACL
		MutableAcl acl = null;
		try {

			acl = (MutableAcl) aclService.readAclById(oi);
		} catch (NotFoundException nfe) {
			acl = aclService.createAcl(oi);
		}

		// Now grant some permissions via an access control entry (ACE)
		if (acl != null) {
			acl.insertAce(acl.getEntries().size(), p, sid, true);
			aclService.updateAcl(acl);
		}

		LOGGER.debug(
				"[adicionarPermissao] Permissao {} concedida com sucesso.", acl);
	}

}
