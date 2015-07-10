package br.com.debico.bolao.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.debico.bolao.dao.names.TBApostadorPontuacaoRodada;
import br.com.debico.bolao.model.ApostadorPontuacaoRodada;
import br.com.debico.model.Apostador;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.model.campeonato.Rodada;

public class ApostadorPontuacaoRodadaRowMapper implements RowMapper<ApostadorPontuacaoRodada> {


    public ApostadorPontuacaoRodadaRowMapper() {

    }
    
    @Override
    public ApostadorPontuacaoRodada mapRow(ResultSet rs, int rowNum)
            throws SQLException {
        final ApostadorPontuacaoRodada apostadorPontuacaoRodada = new ApostadorPontuacaoRodada();
        apostadorPontuacaoRodada.setApostador(new Apostador(rs.getInt(TBApostadorPontuacaoRodada.ID_APOSTADOR)));
        apostadorPontuacaoRodada.setCampeonato(new CampeonatoImpl(rs.getInt(TBApostadorPontuacaoRodada.ID_CAMPEONATO)));
        apostadorPontuacaoRodada.setRodada(new Rodada(rs.getInt(TBApostadorPontuacaoRodada.ID_RODADA)));
        apostadorPontuacaoRodada.setEmpates(rs.getInt(TBApostadorPontuacaoRodada.NU_EMPATE));
        apostadorPontuacaoRodada.setErrados(rs.getInt(TBApostadorPontuacaoRodada.NU_ERRADOS));
        apostadorPontuacaoRodada.setGols(rs.getInt(TBApostadorPontuacaoRodada.NU_GOLS));
        apostadorPontuacaoRodada.setPlacar(rs.getInt(TBApostadorPontuacaoRodada.NU_PLACAR));
        apostadorPontuacaoRodada.setPontosTotal(rs.getInt(TBApostadorPontuacaoRodada.NU_PONTOS));
        apostadorPontuacaoRodada.setVencedor(rs.getInt(TBApostadorPontuacaoRodada.NU_VENCEDOR));

        return apostadorPontuacaoRodada;
    }

}
