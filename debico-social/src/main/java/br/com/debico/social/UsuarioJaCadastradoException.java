package br.com.debico.social;

import br.com.debico.core.MessagesCodes;

/**
 * Ocorre quando um usuário com o mesmo email já existe durante o cadastro.
 * 
 * @author ricardozanini
 * @since 2.0.5
 */
public class UsuarioJaCadastradoException extends CadastroApostadorException {

    private static final long serialVersionUID = 1340982469242014556L;

    private UsuarioJaCadastradoException() {
	super();
	this.setMessageCode(MessagesCodes.USUARIO_JA_CADASTRADO);
    }

    public UsuarioJaCadastradoException(String emailCadastrado) {
	this();
	super.setMessageArgs(emailCadastrado);
    }

}
