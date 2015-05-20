package br.com.debico.social;

import br.com.debico.core.MessagesCodes;

/**
 * Ocorre em caso usuário inexistente na base de dados decorrente de algum
 * processo onde o usuário é fundamental.
 * 
 * @author ricardozanini
 * @since 2.0.2
 */
public class UsuarioInexistenteException extends CadastroApostadorException {

    private static final long serialVersionUID = 1340982469242014556L;

    private UsuarioInexistenteException() {
	super();
	this.setMessageCode(MessagesCodes.USUARIO_NAO_ENCONTRADO);
    }

    public UsuarioInexistenteException(String emailNaoEncontrado) {
	this();
	super.setMessageArgs(emailNaoEncontrado);
    }

}
