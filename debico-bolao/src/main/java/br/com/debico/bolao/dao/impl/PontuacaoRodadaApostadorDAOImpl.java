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

import br.com.debico.bolao.dao.PontuacaoRodadaApostadorDAO;
import br.com.debico.bolao.model.PontuacaoRodadaApostador;
import br.com.debico.model.campeonato.Campeonato;

@Named
@Transactional(propagation = Propagation.MANDATORY)
class PontuacaoRodadaApostadorDAOImpl extends NamedParameterJdbcDaoSupport
        implements PontuacaoRodadaApostadorDAO {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PontuacaoRodadaApostadorDAOImpl.class);

    private static final String SQL_FIELDS = "SELECT P.ID_APOSTADOR, P.NM_APOSTADOR, D.NU_ORDEM, SUM(A.NU_PONTOS) AS NU_PONTOS_TOTAL FROM ";
    private static final String SQL_JOINS1 = "TB_APOSTADOR AS P LEFT JOIN ";
    private static final String SQL_JOINS1_TOP10 = "TB_APOSTADOR AS P ON (P.ID_APOSTADOR = C.ID_APOSTADOR) LEFT JOIN ";
    private static final String SQL_JOINS2 = "TB_PALPITE AS A ON (A.ID_APOSTADOR = P.ID_APOSTADOR) INNER JOIN ";
    private static final String SQL_JOINS3 = "TB_PARTIDA AS B ON (A.ID_PARTIDA = B.ID_PARTIDA) INNER JOIN ";
    private static final String SQL_JOINS4 = "TB_RODADA AS D ON (B.ID_RODADA = D.ID_RODADA) INNER JOIN ";
    private static final String SQL_JOINS5 = "TB_RANKING AS E ON (D.ID_RANKING = E.ID_RANKING) INNER JOIN ";
    private static final String SQL_JOINS6 = "TB_FASE AS F ON (E.ID_FASE = F.ID_FASE) ";
    private static final String SQL_WHERE1 = "WHERE F.ID_CAMPEONATO = :id_campeonato ";
    private static final String SQL_WHERE2 = "AND P.ID_APOSTADOR = :id_apostador ";
    private static final String SQL_GROUP_ORDER = "GROUP BY P.ID_APOSTADOR, P.NM_APOSTADOR, D.NU_ORDEM ORDER BY A.ID_APOSTADOR, D.NU_ORDEM ";
    
    private static final String SQL_JOIN_TOP10 = "(SELECT ID_APOSTADOR, ID_CAMPEONATO, NU_PONTOS FROM TB_APOSTADOR_CAMPEONATO ORDER BY NU_PONTOS DESC LIMIT 10) AS C INNER JOIN ";
    
    private static final int INITIAL_SB_CAPACITY = 580;
            
    @Inject
    private DataSource dataSource;

    public PontuacaoRodadaApostadorDAOImpl() {

    }

    @PostConstruct
    public void init() {
        this.setDataSource(dataSource);
    }

    @Override
    public List<PontuacaoRodadaApostador> recuperarTop10Apostadores(Campeonato campeonato) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("id_campeonato", campeonato.getId());
        
        final StringBuilder sb = new StringBuilder(INITIAL_SB_CAPACITY);
        sb.append(SQL_FIELDS)
            .append(SQL_JOIN_TOP10)
            .append(SQL_JOINS1_TOP10)
            .append(SQL_JOINS2)
            .append(SQL_JOINS3)
            .append(SQL_JOINS4)
            .append(SQL_JOINS5)
            .append(SQL_JOINS6)
            .append(SQL_WHERE1)
            .append(SQL_GROUP_ORDER);
        
        LOGGER.debug("[recuperarTop10Apostadores] Realizando a query {} com os parametros {}", sb, params);
            
        return getNamedParameterJdbcTemplate()
                .query(sb.toString(), params, new PontuacaoRodadaApostadorMapper());
    }

    @Override
    public List<PontuacaoRodadaApostador> recuperarPontuacaoApostador(Campeonato campeonato, int idApostador) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("id_campeonato", campeonato.getId());
        params.put("id_apostador", idApostador);
        
        final StringBuilder sb = new StringBuilder(INITIAL_SB_CAPACITY);
        sb.append(SQL_FIELDS)
            .append(SQL_JOINS1)
            .append(SQL_JOINS2)
            .append(SQL_JOINS3)
            .append(SQL_JOINS4)
            .append(SQL_JOINS5)
            .append(SQL_JOINS6)
            .append(SQL_WHERE1)
            .append(SQL_WHERE2)
            .append(SQL_GROUP_ORDER);
        
        LOGGER.debug("[recuperarPontuacaoApostador] Realizando a query {} com os parametros {}", sb, params);
            
        return getNamedParameterJdbcTemplate().query(sb.toString(), params, new PontuacaoRodadaApostadorMapper());
    }

    public static class PontuacaoRodadaApostadorMapper implements RowMapper<PontuacaoRodadaApostador> {
        
        @Override
        public PontuacaoRodadaApostador mapRow(ResultSet rs, int rowNum) throws SQLException {
            final PontuacaoRodadaApostador p = 
                    new PontuacaoRodadaApostador(
                            rs.getString("NM_APOSTADOR"), 
                            rs.getInt("NU_ORDEM"), 
                            rs.getInt("NU_PONTOS_TOTAL"));
            
            p .setIdApostador(rs.getInt("ID_APOSTADOR"));
            
            return p;
        }
        
    }

}
