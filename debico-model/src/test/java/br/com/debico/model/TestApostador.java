package br.com.debico.model;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.hamcrest.text.IsEmptyString;
import org.junit.Test;

import static org.junit.Assert.assertThat;


public class TestApostador {

    public TestApostador() {

    }
    
    @Test
    public void testApostadorApelido() {
        final String nome = "Peter Parker";
        final String apelido = "Aranha";
        final Apostador semApelido = new Apostador(nome);
       
        assertThat(semApelido.getApelido(), IsEmptyString.isEmptyOrNullString());
        assertThat(semApelido.getNomeTela(), Is.is(nome));
        
        final Apostador comApelido = new Apostador(nome);
        comApelido.setApelido(apelido);
        
        assertThat(comApelido.getApelido(), IsNot.not(nome));
        assertThat(comApelido.getApelido(), Is.is(apelido));
        assertThat(comApelido.getNomeTela(), Is.is(apelido));
    }

}
