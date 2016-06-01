package br.com.debico.resultados.processors;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.campeonato.services.ExploraWebResultadosJogosService;
import br.com.debico.core.DebicoException;
import br.com.debico.model.PartidaRodada;
import br.com.debico.resultados.Context;
import br.com.debico.resultados.ProcessorBeans;

@Named(ProcessorBeans.RECUPERA_PARTIDAS_WEB)
@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
public class RecuperaPartidasWebProcessor extends ProcessorSupport {

	// Quando tivermos mais de uma implementação, trocar por uma lista
	private ExploraWebResultadosJogosService<PartidaRodada> exploraWebResultadosJogosService;

	@Inject
	public void setExploraWebResultadosJogosService(
			ExploraWebResultadosJogosService<PartidaRodada> exploraWebResultadosJogosService) {
		this.exploraWebResultadosJogosService = exploraWebResultadosJogosService;
	}

	public RecuperaPartidasWebProcessor() {
		
	}

	@PostConstruct
	public void init() {
		checkNotNull(this.exploraWebResultadosJogosService, "Referência de ExploraResultadosWeb não pode ser nula.");
	}

	@Override
	public void execute(Context context) throws DebicoException {
		context.addPartidas(this.exploraWebResultadosJogosService.recuperarPartidas(context.getCampeonato()));
		this.executeNext(context);
	}

}
