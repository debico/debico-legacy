package br.com.debico.campeonato.dao;

import java.util.List;

import br.com.debico.model.Time;

public interface TimeDAO {
    
    void inserir(Time time);
    
    List<Time> selecionarTodos();
    
    List<Time> pesquisarParteNome(String nome);

}
