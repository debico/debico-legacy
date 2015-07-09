package br.com.debico.bolao.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import br.com.debico.bolao.model.ApostadorPontuacaoRodada;
import br.com.debico.model.Apostador;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.model.campeonato.Rodada;

public class ApostadorPontuacaoRodadaRowMapper implements RowMapper<ApostadorPontuacaoRodada> {

    private static final String ID_CAMPEONATO = "ID_CAMPEONATO";
    private static final String ID_APOSTADOR = "ID_APOSTADOR";
    private static final String ID_RODADA = "ID_RODADA";
    private static final String NU_PONTOS = "NU_PONTOS";
    private static final String NU_EMPATE = "NU_EMPATE";
    private static final String NU_ERRADOS = "NU_ERRADOS";
    private static final String NU_GOLS = "NU_GOLS";
    private static final String NU_PLACAR = "NU_PLACAR";
    private static final String NU_VENCEDOR = "NU_VENCEDOR";
    
    public ApostadorPontuacaoRodadaRowMapper() {

    }
    
    @Override
    public ApostadorPontuacaoRodada mapRow(ResultSet rs, int rowNum)
            throws SQLException {
        final ApostadorPontuacaoRodada apostadorPontuacaoRodada = new ApostadorPontuacaoRodada();
        apostadorPontuacaoRodada.setApostador(new Apostador(rs.getInt(ID_APOSTADOR)));
        apostadorPontuacaoRodada.setCampeonato(new CampeonatoImpl(rs.getInt(ID_CAMPEONATO)));
        apostadorPontuacaoRodada.setRodada(new Rodada(rs.getInt(ID_RODADA)));
        apostadorPontuacaoRodada.setEmpates(rs.getInt(NU_EMPATE));
        apostadorPontuacaoRodada.setErrados(rs.getInt(NU_ERRADOS));
        apostadorPontuacaoRodada.setGols(rs.getInt(NU_GOLS));
        apostadorPontuacaoRodada.setPlacar(rs.getInt(NU_PLACAR));
        apostadorPontuacaoRodada.setPontosTotal(rs.getInt(NU_PONTOS));
        apostadorPontuacaoRodada.setVencedor(rs.getInt(NU_VENCEDOR));

        return apostadorPontuacaoRodada;
    }

}
