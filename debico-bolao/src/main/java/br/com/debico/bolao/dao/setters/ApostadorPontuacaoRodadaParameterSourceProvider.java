package br.com.debico.bolao.dao.setters;

import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import br.com.debico.bolao.dao.names.TBApostadorPontuacaoRodada;
import br.com.debico.bolao.model.ApostadorPontuacaoRodada;

public class ApostadorPontuacaoRodadaParameterSourceProvider implements
	ItemSqlParameterSourceProvider<ApostadorPontuacaoRodada> {

    public ApostadorPontuacaoRodadaParameterSourceProvider() {
    }

    @Override
    public SqlParameterSource createSqlParameterSource(
	    ApostadorPontuacaoRodada item) {
	MapSqlParameterSource source = new MapSqlParameterSource();

	source.addValue(TBApostadorPontuacaoRodada.ID_APOSTADOR, item
		.getApostador().getId());
	source.addValue(TBApostadorPontuacaoRodada.ID_CAMPEONATO, item
		.getCampeonato().getId());
	source.addValue(TBApostadorPontuacaoRodada.ID_RODADA, item.getRodada()
		.getId());
	source.addValue(TBApostadorPontuacaoRodada.NU_EMPATE, item.getEmpates());
	source.addValue(TBApostadorPontuacaoRodada.NU_ERRADOS, item.getErrados());
	source.addValue(TBApostadorPontuacaoRodada.NU_GOLS, item.getGols());
	source.addValue(TBApostadorPontuacaoRodada.NU_PLACAR, item.getPlacar());
	source.addValue(TBApostadorPontuacaoRodada.NU_PONTOS, item.getPontosTotal());
	source.addValue(TBApostadorPontuacaoRodada.NU_VENCEDOR, item.getVencedor());
	
	return source;
    }

}
