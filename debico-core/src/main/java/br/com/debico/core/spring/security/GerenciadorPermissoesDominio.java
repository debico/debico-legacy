package br.com.debico.core.spring.security;

import br.com.debico.model.BaseModel;

/**
 * Baseado no conceito do Spring-ACL, adiciona as devidas permissões aos objetos
 * de domínio.
 * <p/>
 * Outras funções de permissão podem ser adicionadas..
 * 
 * @see org.springframework.security.acls.domain.BasePermission
 * @see <a
 *      href="http://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#domain-acls">Domain
 *      Object Security (ACLs)</a>
 * @author ricardozanini
 * @since 2.0.0
 */
public interface GerenciadorPermissoesDominio {

	/**
	 * Adiciona a permissão de administrador ao login enviado ao objeto de
	 * domínio em questão.
	 * 
	 * @param login dono do domínio
	 * @param model referência do domínio
	 */
	void adicionarPermissaoAdministrador(final String login,
			final BaseModel<?> model);
	
	/**
	 * @see #adicionarPermissaoAdministrador(String, BaseModel)
	 */
	void adicionarPermissaoLeitura(final String login,
			final BaseModel<?> model);

}
