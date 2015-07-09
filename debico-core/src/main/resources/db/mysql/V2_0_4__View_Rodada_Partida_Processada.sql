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