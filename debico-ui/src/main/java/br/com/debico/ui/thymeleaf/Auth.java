package br.com.debico.ui.thymeleaf;

import org.springframework.expression.spel.support.StandardEvaluationContext;

public class Auth extends StandardEvaluationContext {

	public Auth() {
		
	}

	public Auth(Object rootObject) {
		super(rootObject);
	}

}
