package br.com.debico.core.spring.security;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.security.acls.model.ChildrenExistException;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;

/**
 * Apenas um Mock do {@link MutableAclService} para os nossos testes.
 * 
 * @author ricardozanini
 *
 */
public class MockAclService implements MutableAclService {

	public MockAclService() {
	}

	@Override
	public List<ObjectIdentity> findChildren(ObjectIdentity parentIdentity) {
		return Collections.emptyList();
	}

	@Override
	public Acl readAclById(ObjectIdentity object) throws NotFoundException {
		return null;
	}

	@Override
	public Acl readAclById(ObjectIdentity object, List<Sid> sids)
			throws NotFoundException {
		return null;
	}

	@Override
	public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects)
			throws NotFoundException {
		return Collections.emptyMap();
	}

	@Override
	public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects,
			List<Sid> sids) throws NotFoundException {
		return Collections.emptyMap();
	}

	@Override
	public MutableAcl createAcl(ObjectIdentity objectIdentity)
			throws AlreadyExistsException {
		return null;
	}

	@Override
	public void deleteAcl(ObjectIdentity objectIdentity, boolean deleteChildren)
			throws ChildrenExistException {
		// silence
	}

	@Override
	public MutableAcl updateAcl(MutableAcl acl) throws NotFoundException {
		return null;
	}

}
