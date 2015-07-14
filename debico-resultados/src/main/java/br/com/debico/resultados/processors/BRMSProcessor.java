package br.com.debico.resultados.processors;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.debico.core.brms.BRMSExecutor;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.Processor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * {@link Processor} que delega para o mecanismo de regras o processamento do
 * contexto.
 * 
 * @author Ricardo Zanini (ricardozanini@gmail.com)
 *
 */
public abstract class BRMSProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(BRMSProcessor.class);

    private BRMSExecutor brmsExecutor;

    public BRMSProcessor() {

    }

    @Inject
    public final void setBrmsExecutor(BRMSExecutor brmsExecutor) {
        this.brmsExecutor = brmsExecutor;
    }

    public final BRMSExecutor getBrmsExecutor() {
        return brmsExecutor;
    }

    public abstract String getAgendaRule();

    @PostConstruct
    public void init() {
        checkNotNull(this.brmsExecutor, "Executor nao pode ser nulo");
    }

    @Override
    public final boolean execute(Context context) {
        final Collection<?>[] fatos = selecionarFatos(context);
        if (fatos.length > 0) {
            LOGGER.debug("[execute] Iniciando o processamento da agenda {}",
                    getAgendaRule());
            this.postExecute(context,
                    brmsExecutor.processar(getAgendaRule(), fatos), fatos);
            LOGGER.debug("[execute] Execucao de {} terminada", getAgendaRule());
            return true;
        } else {
            LOGGER.debug(
                    "[execute] Execucao de {} cancelada por falta de fatos.",
                    getAgendaRule());
            return false;
        }
    }

    /**
     * Hook para selecionar os fatos que serão processados pelo BRMS.
     * 
     * @param context
     * @return
     */
    protected abstract Collection<?>[] selecionarFatos(Context context);

    /**
     * Hook para realizar alguma ação após o processamento da regra no BRMS.
     */
    protected void postExecute(Context context, int regrasProcessadasCount,
            Collection<?>... fatos) {

    }

}
