package br.com.debico.social.services.impl;

import java.util.Collections;
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
	public List<Apostador> consultarApostadores(long idLiga) {
		checkArgument(idLiga > 0, "Informe a liga");

		return ligaDAO.selecionarApostadores(idLiga);
	}

	@Override
	public List<Liga> consultarLiga(String emailUsuario) {
		checkNotNull(emptyToNull(emailUsuario));

		final Apostador apostador = apostadorService
				.selecionarApostadorPorEmail(emailUsuario);

		if (apostador == null) {
			return Collections.emptyList();
		}

		return ligaDAO.selecionarPorApostador(apostador.getId());
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
		this.inscreverApostador(liga, apostador);

		// enviar email.

		return liga;
	}

	private boolean inscreverApostador(final LigaApostador ligaApostador) {
		checkNotNull(ligaApostador);
		ligaApostadorDAO.create(ligaApostador);
		// regras de negocio aqui

		return true;
	}

	public boolean inscreverApostador(final Liga liga, final Apostador apostador) {
		checkNotNull(liga, "A referencia de liga eh obrigatoria");
		checkNotNull(apostador, "a referencia de apostador eh obrigatoria");

		this.validarLigaApostador(liga.getId(), apostador.getId());

		return this.inscreverApostador(new LigaApostador(liga, apostador));
	}

	@Override
	public boolean inscreverApostador(long idLiga, int idUsuarioApostador) {
		this.validarLigaApostador(idLiga, idUsuarioApostador);

		return this.inscreverApostador(this.recuperarAssociacaoLigaApostador(
				idLiga, idUsuarioApostador));
	}

	@Override
	public boolean inscreverApostador(Liga liga, int idUsuarioApostador) {
		return this.inscreverApostador(liga,
				apostadorService.selecionarApostadorPorIdUsuario(idUsuarioApostador));
	}

	@Override
	public boolean inscreverApostador(Liga liga,
			List<Integer> idsUsuarioApostador) {

		for (Integer idUsuario : idsUsuarioApostador) {
			this.inscreverApostador(liga, idUsuario);
		}

		return true;
	}
	
	@Override
	public boolean inscreverApostador(long idLiga,
			List<Integer> idsUsuarioApostador) {
		return this.inscreverApostador(ligaDAO.findById(idLiga),
				idsUsuarioApostador);
	}

	private boolean removerApostador(final LigaApostador ligaApostador) {
		checkNotNull(ligaApostador);
		ligaApostadorDAO.remove(ligaApostador);

		return true;
	}

	@Override
	public boolean removerApostador(long idLiga, int idUsuarioApostador) {
		this.validarLigaApostador(idLiga, idUsuarioApostador);

		return this.removerApostador(this.recuperarAssociacaoLigaApostador(
				idLiga, idUsuarioApostador));
	}


	@Override
	public boolean removerApostador(Liga liga, Apostador apostador) {
		checkNotNull(liga, "A referencia de liga eh obrigatoria");
		checkNotNull(apostador, "a referencia de apostador eh obrigatoria");

		return this.removerApostador(ligaApostadorDAO.findById(new LigaApostador(liga, apostador)));
	}

	@Override
	public boolean removerApostador(Liga liga, int idUsuarioApostador) {
		return this.removerApostador(liga, apostadorService.selecionarApostadorPorIdUsuario(idUsuarioApostador));
	}

	@Override
	public boolean removerApostador(Liga liga, List<Integer> idsUsuarioApostador) {
		for (Integer idUsuario : idsUsuarioApostador) {
			this.removerApostador(liga, idUsuario);
		}
		
		return true;
	}
	
	@Override
	public boolean removerApostador(long idLiga,
			List<Integer> idsUsuarioApostador) {
		return this.removerApostador(ligaDAO.findById(idLiga), idsUsuarioApostador);
	}

	private LigaApostador recuperarAssociacaoLigaApostador(final long idLiga,
			final int idUsuarioApostador) {
		final Liga liga = ligaDAO.findById(idLiga);
		final Apostador apostador = apostadorService
				.selecionarApostadorPorIdUsuario(idUsuarioApostador);

		return ligaApostadorDAO.findById(new LigaApostador(liga, apostador));
	}

	private void validarLigaApostador(final long idLiga,
			final int idUsuarioApostador) {
		checkArgument(idLiga > 0, "O Id da liga deve ser informado");
		checkArgument(idUsuarioApostador > 0,
				"O Id do apostador deve ser informado");
	}

}
