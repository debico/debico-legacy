package br.com.debico.social.dao;

import br.com.debico.model.Usuario;
import br.com.tecnobiz.spring.config.dao.Dao;

public interface UsuarioDAO extends Dao<Usuario, Integer> {

	Usuario selecionarPorEmail(String email);

}
