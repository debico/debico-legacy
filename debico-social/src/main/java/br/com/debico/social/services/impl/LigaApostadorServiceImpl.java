package br.com.debico.social.services.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import br.com.debico.model.Apostador;
import br.com.debico.social.InscricaoLigaException;
import br.com.debico.social.dao.LigaApostadorDAO;
import br.com.debico.social.dao.LigaDAO;
import br.com.debico.social.model.Liga;
import br.com.debico.social.model.LigaApostador;
import br.com.debico.social.model.LigaApostadorLite;
import br.com.debico.social.services.ApostadorService;
import br.com.debico.social.services.LigaApostadorService;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Named
@Transactional(readOnly = false)
public class LigaApostadorServiceImpl implements LigaApostadorService {

	@Inject
	private LigaDAO ligaDAO;

	@Inject
	private LigaApostadorDAO ligaApostadorDAO;

	@Inject
	private ApostadorService apostadorService;

	public LigaApostadorServiceImpl() {

	}

	@Override
	public List<Apostador> consultarApostadores(long idLiga) {
		checkArgument(idLiga > 0, "Informe a liga");

		return ligaDAO.selecionarApostadores(idLiga);
	}

	private boolean inscreverApostador(final LigaApostador ligaApostador) throws InscricaoLigaException {
		checkNotNull(ligaApostador);
		
		final LigaApostador inscricaoExistente = ligaApostadorDAO.findById(ligaApostador);
		
		if(inscricaoExistente != null) {
			throw new InscricaoLigaException("Apostador ja cadastrado", "liga.err.inscricao_existente");
		}
		
		ligaApostadorDAO.create(ligaApostador);

		return true;
	}

	@Override
	public boolean inscreverApostador(LigaApostadorLite ligaApostador) throws InscricaoLigaException {
		checkNotNull(ligaApostador, "Estrutura de ligaApostador nula");
		this.validarLigaApostador(ligaApostador.getIdLiga(),
				ligaApostador.getIdApostador());

		// @formatter:off
		return this.inscreverApostador(
				this.recuperarAssociacaoLigaApostador(
						ligaApostador.getIdLiga(), 
						ligaApostador.getIdApostador()));
		// @formatter:on
	}

	@Override
	public boolean inscreverApostador(final Liga liga, final Apostador apostador) throws InscricaoLigaException {
		checkNotNull(liga, "A referencia de liga eh obrigatoria");
		checkNotNull(apostador, "a referencia de apostador eh obrigatoria");

		this.validarLigaApostador(liga.getId(), apostador.getId());

		return this.inscreverApostador(new LigaApostador(liga, apostador));
	}

	private boolean removerApostador(final LigaApostador ligaApostador) throws InscricaoLigaException {
		checkNotNull(ligaApostador);
		
		final LigaApostador inscricaoExistente = ligaApostadorDAO.findById(ligaApostador);
		
		if(inscricaoExistente == null) {
			throw new InscricaoLigaException("Erro ao excluir inscricao", "liga.err.inscricao_not_found");
		}
		
		ligaApostadorDAO.remove(inscricaoExistente);

		return true;
	}

	@Override
	public boolean removerApostador(Liga liga, Apostador apostador) throws InscricaoLigaException {
		checkNotNull(liga, "A referencia de liga eh obrigatoria");
		checkNotNull(apostador, "a referencia de apostador eh obrigatoria");

		return this.removerApostador(ligaApostadorDAO
				.findById(new LigaApostador(liga, apostador)));
	}

	@Override
	public boolean removerApostador(LigaApostadorLite ligaApostadorLite) throws InscricaoLigaException  {
		checkNotNull(ligaApostadorLite, "Estrutura de ligaApostador nula");
		
		// @formatter:off
		return this.removerApostador(
				this.recuperarAssociacaoLigaApostador(
						ligaApostadorLite.getIdLiga(), 
						ligaApostadorLite.getIdApostador()));
		// @formatter:on
	}

	private LigaApostador recuperarAssociacaoLigaApostador(final long idLiga,
			final int idApostador) {
		final Liga liga = ligaDAO.findById(idLiga);
		final Apostador apostador = apostadorService.selecionarApostadorPorId(idApostador);
		return new LigaApostador(liga, apostador);
	}

	private void validarLigaApostador(final long idLiga,
			final int idUsuarioApostador) {
		checkArgument(idLiga > 0, "O Id da liga deve ser informado");
		checkArgument(idUsuarioApostador > 0,
				"O Id do apostador deve ser informado");
	}

}
