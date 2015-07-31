package br.com.debico.social.utils;

public final class PropsKeys {

    /**
     * Chave de autenticação OAuth2 para o Google
     */
    public static final String GOOGLE_CONSUMER_KEY = "br.com.debico.social.google.consumerKey";
    /**
     * Senha de autenticação OAuth2 para o Google
     */
    public static final String GOOGLE_CONSUMER_SECRET = "br.com.debico.social.google.consumerSecret";
    /**
     * Senha de autenticação para SSO do Disqus
     */
    public static final String DISQUS_SECRET = "JFsWER3KbjtjOUlUnIlHtB61bkXNkXv1n8c35FIOmPdH4XwvdrKyC05Jv811POSH";
    /**
     * Chave pública para transação SSO/OAuth2 do Disqus
     */
    public static final String DISQUS_PUBLIC = "Dx2zjQrDq3SBVYb4pRqRVrjNeb9PYSjySxEsPQBmsOQhmpW92SR5Zkcj3vWXMu6T";
    
    /**
     * Senha para realizar a criptografia de dados sensíveis das redes
     */
    public static final String ENC_PASS = "br.com.debico.social.encryptor.pass";
    /**
     * Salt para realizar a criptografia de dados sensíveis das redes
     */
    public static final String ENC_SALT = "br.com.debico.social.encryptor.salt";

    private PropsKeys() {

    }

}
