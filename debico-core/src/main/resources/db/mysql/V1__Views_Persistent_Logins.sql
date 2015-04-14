-- http://stackoverflow.com/questions/1720244/create-new-user-in-mysql-and-give-it-full-access-to-one-database
-- GRANT ALL PRIVILEGES ON bolao_campeao.* To 'user_bolao'@'%' IDENTIFIED BY 'user_bolao';

-- remember me Spring Security
-- ref.: http://www.mkyong.com/spring-security/spring-security-remember-me-example/
CREATE TABLE persistent_logins (
    username VARCHAR(64) NOT NULL,
    series VARCHAR(64) NOT NULL,
    token VARCHAR(64) NOT NULL,
    last_used TIMESTAMP NOT NULL,
    PRIMARY KEY (series)
);

-- criacao da view.
CREATE VIEW vi_partida_rodada_palpite AS 
    SELECT 
        P.ID_PARTIDA,
        P.ID_RODADA,
        P.ID_TIME_MANDANTE,
        M.NM_ALIAS AS NM_ALIAS_MANDANTE,
        M.DC_BRASAO_IMG AS DC_BRASAO_IMG_MANDANTE,
        M.NM_TIME AS NM_TIME_MANDANTE,
        P.ID_TIME_VISITANTE,
        V.NM_ALIAS AS NM_ALIAS_VISITANTE,
        V.DC_BRASAO_IMG AS DC_BRASAO_IMG_VISITANTE,
        V.NM_TIME AS NM_TIME_VISITANTE,
        P.DC_LOCAL,
        P.DH_PARTIDA,
        P.IN_COMPUTADA,
        L.ID_PALPITE,
        L.ID_APOSTADOR,
        L.ID_PLACAR,
        A.NU_GOLS_MANDANTE,
        A.NU_GOLS_VISITANTE,
        L.IN_COMPUTADO,
        R.NU_ORDEM,
        C.DC_PERMALINK,
        L.NU_PONTOS,
        E.ID_PLACAR as ID_PLACAR_PART,
        E.NU_GOLS_MANDANTE as NU_GOLS_MANDANTE_PART,
		E.NU_GOLS_VISITANTE as NU_GOLS_VISITANTE_PART
    FROM
        tb_partida as P LEFT JOIN
        tb_placar as E ON (P.ID_PLACAR = E.ID_PLACAR) INNER JOIN
        tb_palpite as L ON (P.ID_PARTIDA = L.ID_PARTIDA) INNER JOIN
        tb_rodada as R ON (P.ID_RODADA = R.ID_RODADA) INNER JOIN
        tb_time as M ON (P.ID_TIME_MANDANTE = M.ID_TIME) INNER JOIN
        tb_time as V ON (P.ID_TIME_VISITANTE = V.ID_TIME) INNER JOIN
        tb_placar as A ON (L.ID_PLACAR = A.ID_PLACAR) INNER JOIN
        tb_ranking as G ON (R.ID_RANKING = G.ID_RANKING) INNER JOIN
        tb_fase as F ON (G.ID_FASE = F.ID_FASE) INNER JOIN
        tb_campeonato as C ON (F.ID_CAMPEONATO = C.ID_CAMPEONATO)
    WHERE
        P.TP_PARTIDA = 'R';

CREATE VIEW vi_partida_rodada as
	SELECT 
		P.ID_PARTIDA,
		P.ID_RODADA,
		P.ID_TIME_MANDANTE,
		M.NM_ALIAS AS NM_ALIAS_MANDANTE,
        M.DC_BRASAO_IMG AS DC_BRASAO_IMG_MANDANTE,
        M.NM_TIME AS NM_TIME_MANDANTE,
        P.ID_TIME_VISITANTE,
        V.NM_ALIAS AS NM_ALIAS_VISITANTE,
        V.DC_BRASAO_IMG AS DC_BRASAO_IMG_VISITANTE,
        V.NM_TIME AS NM_TIME_VISITANTE,
		P.DC_LOCAL,
		P.DH_PARTIDA,
		P.IN_COMPUTADA,
		NULL as ID_PALPITE,
		NULL as ID_APOSTADOR,
		NULL as ID_PLACAR,
		NULL as NU_GOLS_MANDANTE,
       	NULL as NU_GOLS_VISITANTE,
		NULL as IN_COMPUTADO,
		R.NU_ORDEM,
		C.DC_PERMALINK,
		NULL as NU_PONTOS,
		E.ID_PLACAR as ID_PLACAR_PART,
		E.NU_GOLS_MANDANTE as NU_GOLS_MANDANTE_PART,
		E.NU_GOLS_VISITANTE as NU_GOLS_VISITANTE_PART 
	FROM
		tb_partida as P LEFT JOIN
		tb_placar as E ON (P.ID_PLACAR = E.ID_PLACAR) INNER JOIN
		tb_rodada as R ON (P.ID_RODADA = R.ID_RODADA) INNER JOIN
		tb_time as M ON (P.ID_TIME_MANDANTE = M.ID_TIME) INNER JOIN
        tb_time as V ON (P.ID_TIME_VISITANTE = V.ID_TIME) INNER JOIN
        tb_ranking as G ON (R.ID_RANKING = G.ID_RANKING) INNER JOIN
        tb_fase as F ON (G.ID_FASE = F.ID_FASE) INNER JOIN
        tb_campeonato as C ON (F.ID_CAMPEONATO = C.ID_CAMPEONATO) 
	WHERE
		P.TP_PARTIDA = 'R';
		
