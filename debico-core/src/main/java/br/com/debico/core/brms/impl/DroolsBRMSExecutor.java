package br.com.debico.core.brms.impl;

import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.internal.command.CommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.core.brms.BRMSExecutor;

@Named
@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
public class DroolsBRMSExecutor implements BRMSExecutor {

	protected static final Logger LOGGER = LoggerFactory
			.getLogger(DroolsBRMSExecutor.class);

	@Inject
	protected KieBase kieBase;

	@SuppressWarnings("unchecked")
	public int processar(final String agenda, final Collection<?>... fatos) {
		final KieSession kieSession = kieBase.newKieSession();

		try {
			// TODO: colocar uma propriedade no ambiente para ligar e desligar
			// esse comportamento
			// kieSession.addEventListener(new DebugAgendaEventListener());
			// kieSession.addEventListener(new DebugRuleRuntimeEventListener());

			kieSession.getAgenda().getAgendaGroup(agenda).setFocus();

			LOGGER.debug("[processar] Inserindos os fatos no motor de regras.");

			for (Collection<?> c : fatos) {
				kieSession.execute(CommandFactory.newInsertElements(c));
				LOGGER.debug("[processar] '{}' fatos inseridos.", c.size());
			}

			LOGGER.debug("[processar] Efetuando o processamento das regras.");
			final int rules = (Integer) kieSession.execute(CommandFactory
					.newFireAllRules());
			LOGGER.debug(
					"[processar] Processamento realizado com sucesso! {} regras processadas",
					rules);

			return rules;
		} finally {
			if (kieSession != null) {
				kieSession.dispose();
			}
		}
	}

}
