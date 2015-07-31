package br.com.debico.social.services.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.model.Usuario;
import br.com.debico.social.CadastroApostadorException;
import br.com.debico.social.services.UsuarioService;


@Named
@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
class ApostadorConnectionSignUp implements ConnectionSignUp {
    
    @Inject
    private UsuarioService usuarioService;

    public ApostadorConnectionSignUp() {
	
    }

    @Override
    public String execute(Connection<?> connection) {
	try {
	    Usuario usuario = usuarioService.cadastrarOuRelacionarApostadorUserProfile(connection.fetchUserProfile());
	    return String.valueOf(usuario.getId());
	} catch (CadastroApostadorException e) {
	    return null;
	}
    }

}
