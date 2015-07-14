package br.com.debico.resultados;

import java.util.List;

import br.com.debico.model.ApostadorPontuacao;
import br.com.debico.model.PalpiteBase;
import br.com.debico.model.PartidaBase;
import br.com.debico.model.campeonato.AbstractRodada;
import br.com.debico.model.campeonato.Campeonato;

import com.google.common.collect.Lists;

/**
 * Implementação básica de {@link Context}.
 * 
 * @author ricardozanini
 *
 */
public class ContextImpl implements Context {

    private static final long serialVersionUID = -2316667746008564072L;

    private final Campeonato campeonato;

    private List<AbstractRodada> rodadas;
    private List<PartidaBase> partidas;
    private List<ApostadorPontuacao> apostadores;
    private List<PalpiteBase> palpites;

    public ContextImpl(Campeonato campeonato) {
	this.campeonato = campeonato;
	this.rodadas = Lists.newArrayList();
	this.partidas = Lists.newArrayList();
	this.apostadores = Lists.newArrayList();
	this.palpites = Lists.newArrayList();
    }

    @Override
    public Campeonato getCampeonato() {
	return campeonato;
    }

    @Override
    public List<AbstractRodada> getRodadas() {
	return rodadas;
    }

    @Override
    public void setRodadas(List<? extends AbstractRodada> rodadas) {
	this.rodadas = Lists.newArrayList(rodadas);
    }

    @Override
    public List<PartidaBase> getPartidas() {
	return this.partidas;
    }

    @Override
    public void setPartidas(List<? extends PartidaBase> partidas) {
	this.partidas = Lists.newArrayList(partidas);
    }

    @Override
    public void addPartidas(List<? extends PartidaBase> partidas) {
	this.partidas.addAll(partidas);
    }

    @Override
    public List<ApostadorPontuacao> getApostadores() {
	return apostadores;
    }

    @Override
    public void setApostadores(List<? extends ApostadorPontuacao> apostadores) {
	this.apostadores = Lists.newArrayList(apostadores);
    }

    @Override
    public List<PalpiteBase> getPalpites() {
	return palpites;
    }

    @Override
    public void setPalpites(List<? extends PalpiteBase> palpites) {
	this.palpites = Lists.newArrayList(palpites);
    }

}
