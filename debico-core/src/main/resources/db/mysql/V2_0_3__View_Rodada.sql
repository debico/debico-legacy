CREATE VIEW vi_rodada_partida_processada AS
SELECT 
    R.ID_RODADA, R.NM_RODADA, F.ID_RODADA
FROM
    tb_rodada R
        INNER JOIN
    (SELECT DISTINCT
        ID_RODADA, ID_FASE
    FROM
        tb_partida
    WHERE
        IN_COMPUTADA = 1) AS P ON (R.ID_RODADA = P.ID_RODADA)
        INNER JOIN
    tb_fase F ON (P.ID_FASE = F.ID_FASE)
ORDER BY F.ID_CAMPEONATO, R.NU_ORDEM;