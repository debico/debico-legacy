package br.com.debico.social;

/**
 * Ocorre em caso usuário inexistente na base de dados decorrente de algum
 * processo onde o usuário é fundamental.
 * 
 * @author ricardozanini
 * @since 2.0.2
 */
public class UsuarioInexistenteException extends CadastroLigaException {

    private static final long serialVersionUID = 1340982469242014556L;

    public UsuarioInexistenteException(String message, Throwable inner,
            String messageCode) {
        super(message, inner, messageCode);
    }

    public UsuarioInexistenteException(String message, String messageCode) {
        super(message, messageCode);
    }

    public UsuarioInexistenteException(Throwable inner, String messageCode) {
        super(inner, messageCode);
    }

}
