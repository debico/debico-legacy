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
	 * @param login
	 *            dono do domínio
	 * @param model
	 *            referência do domínio
	 */
	void adicionarPermissaoAdministrador(String login, BaseModel<?> model);

	/**
	 * @see #adicionarPermissaoAdministrador(String, BaseModel)
	 */
	void adicionarPermissaoLeitura(String login, BaseModel<?> model);

	/**
	 * Concede a permissão ao usuário em determinado domínio
	 * 
	 * @param login
	 * @param model
	 * @param admin
	 *            se false, concede apenas leitura.
	 */
	void adicionarPermissao(String login, BaseModel<?> model, boolean admin);

	/**
	 * Remove a permissão do usuário de determinado domínio.
	 * 
	 * @param login
	 * @param model
	 */
	void removerPermissao(String login, BaseModel<?> model);

	/**
	 * Útil se o domínio em questão for removido da base de dados.
	 * 
	 * @param model
	 */
	void removerTodasPermissoes(BaseModel<?> model);

}
