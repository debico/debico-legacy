package br.com.debico.social.services.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.com.debico.model.Apostador;
import br.com.debico.social.CadastroLigaException;
import br.com.debico.social.dao.LigaApostadorDAO;
import br.com.debico.social.dao.LigaDAO;
import br.com.debico.social.model.Liga;
import br.com.debico.social.model.LigaApostador;
import br.com.debico.social.services.ApostadorService;
import br.com.debico.social.services.LigaService;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import static com.google.common.base.Strings.emptyToNull;

@Named
@Transactional(readOnly = false)
class LigaServiceImpl implements LigaService {

	@Inject
	private LigaDAO ligaDAO;

	@Inject
	private LigaApostadorDAO ligaApostadorDAO;

	@Inject
	private ApostadorService apostadorService;

	public LigaServiceImpl() {

	}

	@Override
	public Liga cadastrarNovaLiga(String nome, String emailAdmin)
			throws CadastroLigaException {

		checkNotNull(emptyToNull(nome));
		checkNotNull(emptyToNull(emailAdmin));

		Apostador apostador = apostadorService
				.selecionarApostadorPorEmail(emailAdmin);

		if (apostador == null) {
			throw new CadastroLigaException(String.format(
					"Apostador com o email '%s' nao foi encontrador",
					emailAdmin));
		}

		final Liga liga = new Liga(nome);
		liga.setAdministrador(apostador);

		ligaDAO.create(liga);

		// enviar email.

		return liga;
	}

	@Override
	public boolean removerApostador(long idLiga, int idUsuarioApostador) {
		this.validarLigaApostador(idLiga, idUsuarioApostador);

		ligaApostadorDAO.remove(new LigaApostador(idUsuarioApostador, idLiga));
		
		return true;
	}

	@Override
	public boolean inscreverApostador(long idLiga, int idUsuarioApostador) {
		this.validarLigaApostador(idLiga, idUsuarioApostador);

		ligaApostadorDAO.create(new LigaApostador(idUsuarioApostador, idLiga));
		
		// enviar email de notificacao
		
		return true;
	}

	@Override
	public boolean inscreverApostador(long idLiga,
			List<Integer> idsUsuarioApostador) {
		for (Integer usuario : idsUsuarioApostador) {
			this.inscreverApostador(idLiga, usuario);
		}

		return true;
	}

	@Override
	public boolean removerApostador(long idLiga,
			List<Integer> idsUsuarioApostador) {
		for (Integer usuario : idsUsuarioApostador) {
			this.removerApostador(idLiga, usuario);
		}

		return true;
	}

	private void validarLigaApostador(final long idLiga,
			final int idUsuarioApostador) {
		checkArgument(idLiga > 0, "O Id da liga deve ser informado");
		checkArgument(idUsuarioApostador > 0,
				"O Id do apostador deve ser informado");
	}

}
