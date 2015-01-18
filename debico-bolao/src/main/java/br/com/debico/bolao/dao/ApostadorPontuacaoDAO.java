package br.com.debico.bolao.dao;

import java.util.List;

import br.com.debico.model.Apostador;
import br.com.debico.model.ApostadorPontuacao;
import br.com.debico.model.campeonato.Campeonato;

public interface ApostadorPontuacaoDAO {
    
    List<ApostadorPontuacao> selecionarApostadores(Campeonato campeonato);
    
    void inserir(ApostadorPontuacao apostadorPontuacao);
    
    ApostadorPontuacao selecionarApostador(Apostador apostador, Campeonato campeonato);

}
