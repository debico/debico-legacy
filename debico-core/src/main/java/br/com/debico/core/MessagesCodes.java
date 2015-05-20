package br.com.debico.core;

//Lower case: CTRL+SHIFT+Y (CMD+SHIFT+Y on Mac OS X)
//Upper case: CTRL+SHIFT+X (CMD+SHIFT+X on Mac OS X)
/**
 * Acessor para o código das mensagens.
 * 
 * @author r_fernandes
 *
 */
public final class MessagesCodes {

    private MessagesCodes() {
        
    }
    
    public static final String PALPITE_FORA_PRAZO = "br.com.debico.core.exception.palpite_fora_prazo";
    
    public static final String PALPITE_EXISTENTE = "br.com.debico.core.exception.palpite_existente";
    
    public static final String USUARIO_NAO_ENCONTRADO = "br.com.debico.core.exception.usuario_nao_encontrado";
    
    public static final String APOSTADOR_JA_INSCRITO = "br.com.debico.core.exception.apostador_ja_inscrito";
    
    public static final String USUARIO_JA_CADASTRADO = "br.com.debico.core.exception.usuario_ja_cadastrado"; 
    
    public static final String SENHA_NAO_CONFERE = "br.com.debico.core.exception.senha_nao_confere";
    
    public static final String SENHA_FAIL_POLITICA = "br.com.debico.core.exception.senha_fail_politica";
    
    public static final String APOSTADOR_DADOS_INCORRETOS = "br.com.debico.core.exception.apostador_dados_incorretos";
    
    public static final String SENHA_ATUAL_NAO_CONFERE = "br.com.debico.social.exception.senha_atual_nao_confere";

    public static final String TOKEN_SENHA_INVALIDO = "br.com.debico.social.exception.token_senha_invalido";

}
