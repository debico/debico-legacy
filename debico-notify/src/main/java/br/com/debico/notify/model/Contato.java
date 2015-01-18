package br.com.debico.notify.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import javax.mail.internet.InternetAddress;

public interface Contato extends Serializable {
	
	String getNome();
	
	String getEmail();
	
	InternetAddress getInternetAddress() throws UnsupportedEncodingException;

}
