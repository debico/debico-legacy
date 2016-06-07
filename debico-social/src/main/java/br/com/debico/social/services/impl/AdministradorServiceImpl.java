package br.com.debico.social.services.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.com.debico.model.Perfil;
import br.com.debico.model.Usuario;
import br.com.debico.notify.model.Contato;
import br.com.debico.notify.model.ContatoImpl;
import br.com.debico.social.dao.UsuarioDAO;
import br.com.debico.social.services.AdministradorService;

@Named
@Transactional(readOnly = true)
class AdministradorServiceImpl implements AdministradorService {

	@Inject
	private UsuarioDAO usuarioDAO;

	public AdministradorServiceImpl() {

	}

	@Override
	public List<Contato> selecionarComoContato() {
		List<Usuario> usuarios = usuarioDAO.selecionarPorPerfil(Perfil.ROLE_ADMIN);
		List<Contato> contatos = new ArrayList<>();
		for (Usuario usuario : usuarios) {
			contatos.add(new ContatoImpl(usuario));
		}
		return contatos;
	}

}
