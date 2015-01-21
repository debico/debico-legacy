package br.com.debico.core.dao.spring;

public enum TipoDAOConfig {

    /**
     * Configuração JPADAO para testes automatizados utilizando um banco de dados embutido.
     */
    JPA_EMBEDDED_DATABASE,
    /**
     * Configuração com JPA em um servidor de aplicação com JNDI e <code>pool</code> de conexão.
     */
    JPA_APP_SERVER,
    /**
     * Configuração com JPA em um servidor de aplicação sem JNDI ou aplicações desktop
     */
    JPA_STANDALONE,
    /**
     * Banco de dados apenas para inglês ver.
     */
    JPA_MOCK;

}
