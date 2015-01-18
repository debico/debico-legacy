package br.com.debico.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class TestApostadorPontuacao {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(TestApostadorPontuacao.class);

	@Test
	public void testCompareTo() {
		List<ApostadorPontuacao> ranking = new ArrayList<ApostadorPontuacao>();
		
		ApostadorPontuacao p1 = new ApostadorPontuacao(new Apostador("p1"));
		p1.setEmpates(4);
		p1.setErrados(25);
		p1.setGols(21);
		p1.setPlacar(9);
		p1.setPontosTotal(100);
		p1.setVencedor(13);
		
		
		ApostadorPontuacao p2 = new ApostadorPontuacao(new Apostador("p2"));
		p2.setEmpates(4);
		p2.setErrados(21);
		p2.setGols(19);
		p2.setPlacar(9);
		p2.setPontosTotal(100);
		p2.setVencedor(14);
		
		ApostadorPontuacao p4 = new ApostadorPontuacao(new Apostador("p4"));
		p4.setEmpates(4);
		p4.setErrados(20);
		p4.setGols(19);
		p4.setPlacar(9);
		p4.setPontosTotal(100);
		p4.setVencedor(14);
		
		ApostadorPontuacao p3 = new ApostadorPontuacao(new Apostador("p3"));
		p3.setEmpates(5);
		p3.setErrados(25);
		p3.setGols(21);
		p3.setPlacar(9);
		p3.setPontosTotal(100);
		p3.setVencedor(13);
		
		ranking.add(p1);
		ranking.add(p2);
		ranking.add(p3);
		ranking.add(p4);
		
		Collections.sort(ranking);
		
		LOGGER.debug("Ranking {}", ranking);
		
		assertTrue(ranking.get(0).getApostador().getNome().equals("p3"));
		assertTrue(ranking.get(1).getApostador().getNome().equals("p4"));
		assertTrue(ranking.get(2).getApostador().getNome().equals("p2"));
		assertTrue(ranking.get(3).getApostador().getNome().equals("p1"));
	}

}
