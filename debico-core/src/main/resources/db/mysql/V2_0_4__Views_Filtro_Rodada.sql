/*
 * Relacao de rodadas que contem alguma partida jah computada
 */
CREATE VIEW vi_rodada_partida_processada AS
SELECT DISTINCT
    R.ID_RODADA, R.NM_RODADA, R.NU_ORDEM, F.ID_CAMPEONATO
FROM
    tb_rodada R
        INNER JOIN
    tb_partida P ON (R.ID_RODADA = P.ID_RODADA)
        INNER JOIN
    tb_fase F ON (P.ID_FASE = F.ID_FASE)
WHERE
    P.IN_COMPUTADA = 1;
    
/*
 * Total de partidas por rodada 
 */
CREATE VIEW vi_total_partidas_rodadas AS
SELECT 
    P.ID_RODADA, F.ID_CAMPEONATO, COUNT(0) AS NU_TOTAL
FROM
    tb_partida AS P
        INNER JOIN
    tb_rodada AS R ON (P.ID_RODADA = R.ID_RODADA)
        INNER JOIN
    tb_ranking AS A ON (R.ID_RANKING = A.ID_RANKING)
        INNER JOIN
    tb_fase AS F ON (A.ID_FASE = F.ID_FASE)
GROUP BY P.ID_RODADA , F.ID_CAMPEONATO;

/*
 * Total de partidas por rodada ja computadas
 */
CREATE VIEW vi_total_partidas_rodadas_computadas AS
SELECT 
    P.ID_RODADA, F.ID_CAMPEONATO, COUNT(0) AS NU_TOTAL
FROM
    tb_partida AS P
        INNER JOIN
    tb_rodada AS R ON (P.ID_RODADA = R.ID_RODADA)
        INNER JOIN
    tb_ranking AS A ON (R.ID_RANKING = A.ID_RANKING)
        INNER JOIN
    tb_fase AS F ON (A.ID_FASE = F.ID_FASE)
WHERE
    P.IN_COMPUTADA = 1
GROUP BY P.ID_RODADA , F.ID_CAMPEONATO;

/*
 * Total de partidas por rodada nao computadas
 */
CREATE VIEW vi_total_partidas_rodadas_nao_computadas AS
SELECT 
    P.ID_RODADA, F.ID_CAMPEONATO, COUNT(0) AS NU_TOTAL
FROM
    tb_partida AS P
        INNER JOIN
    tb_rodada AS R ON (P.ID_RODADA = R.ID_RODADA)
        INNER JOIN
    tb_ranking AS A ON (R.ID_RANKING = A.ID_RANKING)
        INNER JOIN
    tb_fase AS F ON (A.ID_FASE = F.ID_FASE)
WHERE
    P.IN_COMPUTADA = 0
GROUP BY P.ID_RODADA , F.ID_CAMPEONATO;

/*
 * Sumarizacao do total de partidas computadas e nao computadas
 */
CREATE VIEW vi_partidas_rodadas_status_computadas AS
SELECT 
	T.ID_CAMPEONATO,
    T.ID_RODADA,
    COALESCE(C.NU_TOTAL, 0) AS NU_COMPUTADAS,
    COALESCE(N.NU_TOTAL, 0) AS NU_NAO_COMPUTADAS,
    T.NU_TOTAL,
    COALESCE((C.NU_TOTAL = T.NU_TOTAL), 0) AS IN_COMPLETA
FROM
    vi_total_partidas_rodadas AS T
        LEFT JOIN
    vi_total_partidas_rodadas_computadas AS C ON (T.ID_RODADA = C.ID_RODADA)
        LEFT JOIN
    vi_total_partidas_rodadas_nao_computadas AS N ON (T.ID_RODADA = N.ID_RODADA);