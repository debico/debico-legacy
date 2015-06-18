package br.com.debico.bolao.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.debico.bolao.dao.PartidaPalpiteViewDAO;
import br.com.debico.bolao.model.view.PalpiteView;
import br.com.debico.bolao.model.view.PartidaRodadaPalpiteView;
import br.com.debico.model.Placar;
import br.com.debico.model.Time;

/*
 * ref: http://stackoverflow.com/questions/6777419/how-to-configure-spring-to-make-jpa-hibernate-and-jdbc-jdbctemplate-or-mybati
 * ref: http://stackoverflow.com/questions/2673678/what-transaction-manager-should-i-use-for-jbdc-template-when-using-jpa
 */
@Named
@Transactional(propagation = Propagation.MANDATORY)
class PartidaPalpiteViewDAOImpl extends NamedParameterJdbcDaoSupport implements PartidaPalpiteViewDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(PartidaPalpiteViewDAOImpl.class);
	
	private static final String SQL_VIEW_FIELDS = "SELECT ID_PARTIDA, ID_RODADA, ID_TIME_MANDANTE, NM_ALIAS_MANDANTE, DC_BRASAO_IMG_MANDANTE, NM_TIME_MANDANTE, ID_TIME_VISITANTE, NM_ALIAS_VISITANTE, DC_BRASAO_IMG_VISITANTE, NM_TIME_VISITANTE, DC_LOCAL, DH_PARTIDA, IN_COMPUTADA, ID_PALPITE, ID_APOSTADOR, ID_PLACAR, NU_GOLS_MANDANTE, NU_GOLS_VISITANTE, IN_COMPUTADO, NU_ORDEM, DC_PERMALINK, NU_PONTOS, ID_PLACAR_PART, NU_GOLS_MANDANTE_PART, NU_GOLS_VISITANTE_PART";
	private static final String SQL_VIEW_ORDER_BY = " ORDER BY 12, 4";
	
	@Inject
	private DataSource dataSource;
	
	public PartidaPalpiteViewDAOImpl() {
		
	}
	
	@PostConstruct
	public void init() {
		this.setDataSource(dataSource);
	}

	public List<PartidaRodadaPalpiteView> selecionarPartidasComSemPalpite(int idRodada, int idApostador) {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("id_apostador", idApostador);
		params.put("id_rodada", idRodada);
		
		final StringBuilder sb = 
				new StringBuilder(SQL_VIEW_FIELDS)
					.append(" FROM vi_partida_rodada_palpite WHERE ID_APOSTADOR = :id_apostador AND ID_RODADA = :id_rodada")
					.append(" UNION ")
					.append(SQL_VIEW_FIELDS)
					.append(" FROM vi_partida_rodada WHERE ID_RODADA = :id_rodada AND ID_PARTIDA NOT IN")
					.append("(SELECT A.ID_PARTIDA FROM TB_PALPITE AS A INNER JOIN TB_PARTIDA AS B ON (A.ID_PARTIDA = B.ID_PARTIDA) WHERE ID_RODADA = :id_rodada AND ID_APOSTADOR = :id_apostador)")
					.append(SQL_VIEW_ORDER_BY);
					
		LOGGER.debug("[selecionarPartidasComSemPalpite] Realizando a query {} com os parametros {}", sb, params);
		
		return getNamedParameterJdbcTemplate()
				.query(sb.toString(), params, new PartidaRodadaPalpiteViewMapper());
	}

	public List<PartidaRodadaPalpiteView> selecionarPartidasComSemPalpiteOrdinal(String campeonatoPermalink, int ordinalRodada, int idApostador) {
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("id_apostador", idApostador);
		params.put("ordinal", ordinalRodada);
		params.put("permalink", campeonatoPermalink);
		
		final StringBuilder sb = 
				new StringBuilder(SQL_VIEW_FIELDS)
					.append(" FROM vi_partida_rodada_palpite WHERE ID_APOSTADOR = :id_apostador AND NU_ORDEM = :ordinal AND DC_PERMALINK = :permalink")
					.append(" UNION ")
					.append(SQL_VIEW_FIELDS)
					.append(" FROM vi_partida_rodada AS vi WHERE NU_ORDEM = :ordinal AND DC_PERMALINK = :permalink AND ID_PARTIDA NOT IN")
					.append("(SELECT A.ID_PARTIDA FROM TB_PALPITE AS A INNER JOIN TB_PARTIDA AS B ON (A.ID_PARTIDA = B.ID_PARTIDA) WHERE ID_RODADA = vi.ID_RODADA AND ID_APOSTADOR = :id_apostador)")
					.append(SQL_VIEW_ORDER_BY);
		
		LOGGER.debug("[selecionarPartidasComSemPalpiteOrdinal] Realizando a query {} com os parametros {}", sb, params);
		
		return getNamedParameterJdbcTemplate()
				.query(sb.toString(), params, new PartidaRodadaPalpiteViewMapper());
	} 
	
	
	public static class PartidaRodadaPalpiteViewMapper implements RowMapper<PartidaRodadaPalpiteView> {
		
		public PartidaRodadaPalpiteView mapRow(ResultSet rs, int rowNum) throws SQLException {
			final PartidaRodadaPalpiteView partidaRodadaPalpiteView = new PartidaRodadaPalpiteView();
			final Time mandante = new Time(rs.getInt("ID_TIME_MANDANTE"), rs.getString("NM_TIME_MANDANTE"));
			final Time visitante = new Time(rs.getInt("ID_TIME_VISITANTE"), rs.getString("NM_TIME_VISITANTE"));
			final PalpiteView palpite = new PalpiteView();
			
			palpite.setComputado(rs.getBoolean("IN_COMPUTADO"));
			palpite.setId(rs.getInt("ID_PALPITE"));
			palpite.setPontos(rs.getInt("NU_PONTOS"));
			palpite.setPlacar(
					new Placar(
							rs.getInt("ID_PLACAR"), 
							rs.getInt("NU_GOLS_MANDANTE"), 
							rs.getInt("NU_GOLS_VISITANTE")));
			
			mandante.setAlias(rs.getString("NM_TIME_MANDANTE"));
			mandante.setBrasaoFigura(rs.getString("DC_BRASAO_IMG_MANDANTE"));
			
			visitante.setAlias(rs.getString("NM_TIME_VISITANTE"));
			visitante.setBrasaoFigura(rs.getString("DC_BRASAO_IMG_VISITANTE"));
			
			partidaRodadaPalpiteView.setComputadaCampeonato(rs.getBoolean("IN_COMPUTADA"));
			partidaRodadaPalpiteView.setDataHoraPartida(rs.getTimestamp("DH_PARTIDA"));
			partidaRodadaPalpiteView.setId(rs.getInt("ID_PARTIDA"));
			partidaRodadaPalpiteView.setLocal(rs.getString("DC_LOCAL"));
			partidaRodadaPalpiteView.setIdRodada(rs.getInt("ID_RODADA"));
			partidaRodadaPalpiteView.setOrdemRodada(rs.getInt("NU_ORDEM"));
			partidaRodadaPalpiteView.setMandante(mandante);
			partidaRodadaPalpiteView.setVisitante(visitante);
			partidaRodadaPalpiteView.setPalpite((palpite.getId() > 0 ? palpite : null));
			partidaRodadaPalpiteView.setPlacar(
					rs.getInt("ID_PLACAR_PART") > 0 ?
					new Placar(
							rs.getInt("ID_PLACAR_PART"), 
							rs.getInt("NU_GOLS_MANDANTE_PART"), 
							rs.getInt("NU_GOLS_VISITANTE_PART")) 
					: null);
			
			return partidaRodadaPalpiteView;
		}
		
	}

}
