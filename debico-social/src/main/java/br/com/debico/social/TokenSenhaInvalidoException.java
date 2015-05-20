package br.com.debico.social;

import br.com.debico.core.MessagesCodes;

/**
 * Lançado quando o Token de recuperar senha mostrou-se inválido ou não encontrado na base de dados.
 * 
 * @author ricardozanini
 * @since 2.0.2
 */
public class TokenSenhaInvalidoException extends CadastroApostadorException {

    private static final long serialVersionUID = 1340982469242014556L;

    private TokenSenhaInvalidoException() {
	super();
	this.setMessageCode(MessagesCodes.TOKEN_SENHA_INVALIDO);
    }

    public TokenSenhaInvalidoException(String token) {
	this();
	super.setMessageArgs(token);
    }

}
