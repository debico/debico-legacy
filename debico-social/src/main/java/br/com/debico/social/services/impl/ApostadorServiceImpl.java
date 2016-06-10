package br.com.debico.social.services.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.emptyToNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.core.MessagesCodes;
import br.com.debico.model.Apostador;
import br.com.debico.social.CadastroApostadorException;
import br.com.debico.social.dao.ApostadorDAO;
import br.com.debico.social.services.ApostadorService;

/**
 * Realizações de interação com o objeto de domínio {@link Apostador}.
 * 
 * @author ricardozanini
 * @since 0.1
 */
@Named
@Transactional(readOnly = false)
class ApostadorServiceImpl implements ApostadorService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ApostadorServiceImpl.class);

	@Inject
	private ApostadorDAO apostadorDAO;

	@Inject
	@Named("resourceBundleMessageSource")
	private MessageSource messageSource;

	@Override
	public List<Apostador> pesquisarApostadoresPorParteNome(String nomeParcial) {
		checkNotNull(emptyToNull(nomeParcial), "O nome do apostador nao pode ser em branco.");
		checkArgument(nomeParcial.length() >= 3, "Informe ao menos 3 caracteres");

		return apostadorDAO.procurarPorParteNome(nomeParcial);
	}

	@Transactional(rollbackFor = CadastroApostadorException.class)
	public void atualizarApostador(final Apostador apostador) throws CadastroApostadorException {
		checkNotNull(apostador, "Apostador nao pode estar nulo");

		checkArgument(apostador.getId() > 0, "O apostador deve possuir um ID.");

		// TODO: melhorar a checagem de validação dos dados.
		if (emptyToNull(apostador.getNome()) == null) {
			throw new CadastroApostadorException(messageSource, MessagesCodes.APOSTADOR_DADOS_INCORRETOS);
		}

		final Apostador apostadorBase = apostadorDAO.findById(apostador.getId());

		// atualiza os dados..
		apostadorBase.setNome(apostador.getNome());
		apostadorBase.setApelido(apostador.getApelido());
		apostadorBase.getOpcoes().setLembretePalpites(apostador.getOpcoes().isLembretePalpites());
		this.atualizarTimeCoracao(apostadorBase, apostador);
	}

	protected void atualizarTimeCoracao(final Apostador apostadorBase, final Apostador apostador) {
		if (apostador.possuiTimeCoracao()) {
			apostadorBase.getOpcoes().setTimeCoracao(apostador.getOpcoes().getTimeCoracao());
		} else {
			apostadorBase.getOpcoes().setTimeCoracao(null);
		}
	}

	@Override
	public Apostador selecionarApostadorPorIdUsuario(int idUsuario) {
		checkArgument(idUsuario > 0, "Opa! Apostador NULO!");
		return apostadorDAO.selecionarPorIdUsuario(idUsuario);
	}

	public Apostador selecionarPerfilApostadorPorEmail(String email) {
		LOGGER.debug("[selecionarPerfilApostadorPorEmail] Selecionando o perfil completo de {}", email);

		return apostadorDAO.selecionarPerfilPorEmail(email);
	}

	public Apostador selecionarApostadorPorEmail(String email) {
		checkNotNull(emptyToNull(email), "O email deve ser informado");

		return apostadorDAO.selecionarPorEmail(email);
	}

	@Override
	public Apostador selecionarApostadorPorId(int id) {
		checkArgument(id > 0, "Informe o Id do Apostador");
		return apostadorDAO.findById(id);
	}

}
