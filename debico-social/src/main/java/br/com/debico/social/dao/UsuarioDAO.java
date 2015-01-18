package br.com.debico.social.dao;

import br.com.debico.model.Usuario;

public interface UsuarioDAO {
	
	Usuario selecionarPorEmail(String email);
	
	Usuario atualizar(Usuario usuario);
	
	void inserir(Usuario usuario);

}
