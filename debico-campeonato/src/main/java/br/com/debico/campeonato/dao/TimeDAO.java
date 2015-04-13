package br.com.debico.campeonato.dao;

import java.util.List;

import br.com.debico.model.Time;
import br.com.tecnobiz.spring.config.dao.Dao;

public interface TimeDAO extends Dao<Time, Integer> {

	List<Time> pesquisarParteNome(String nome);

}
