CREATE TABLE IF NOT EXISTS `tb_apostador` (
`ID_APOSTADOR` int(11) NOT NULL,
  `NM_APOSTADOR` longtext NOT NULL,
  `ID_USUARIO` int(11) NOT NULL,
  `IN_LEMBRETE_PALPITE` bit(1) DEFAULT NULL,
  `NM_APELIDO` varchar(128) DEFAULT NULL,
  `ID_TIME` int(11) DEFAULT NULL
) ;

CREATE TABLE IF NOT EXISTS `tb_apostador_campeonato` (
  `NU_EMPATE` int(11) NOT NULL,
  `NU_ERRADOS` int(11) NOT NULL,
  `NU_GOLS` int(11) NOT NULL,
  `NU_PLACAR` int(11) NOT NULL,
  `NU_PONTOS` int(11) NOT NULL,
  `NU_VENCEDOR` int(11) NOT NULL,
  `ID_CAMPEONATO` int(11) NOT NULL,
  `ID_APOSTADOR` int(11) NOT NULL
) ;

CREATE TABLE IF NOT EXISTS `tb_apostador_pontuacao_rodada` (
  `NU_EMPATE` int(11) NOT NULL,
  `NU_ERRADOS` int(11) NOT NULL,
  `NU_GOLS` int(11) NOT NULL,
  `NU_PLACAR` int(11) NOT NULL,
  `NU_PONTOS` int(11) NOT NULL,
  `NU_VENCEDOR` int(11) NOT NULL,
  `ID_RODADA` int(11) NOT NULL,
  `ID_CAMPEONATO` int(11) NOT NULL,
  `ID_APOSTADOR` int(11) NOT NULL
) ;

CREATE TABLE IF NOT EXISTS `tb_campeonato` (
  `TP_CAMPEONATO` varchar(2) NOT NULL,
`ID_CAMPEONATO` int(11) NOT NULL,
  `IN_ATIVO` bit(1) NOT NULL,
  `IN_FINALIZADO` bit(1) NOT NULL,
  `NM_CAMPEONATO` longtext NOT NULL,
  `DC_IMG_CAPA` longtext,
  `DC_PERMALINK` longtext NOT NULL,
  `DT_FIM` datetime DEFAULT NULL,
  `DT_INICIO` datetime DEFAULT NULL
) ;


CREATE TABLE IF NOT EXISTS `tb_campeonato_param` (
  `ID_CAMPEONATO` int(11) NOT NULL,
  `NU_POS_LIM_ELITE` int(11) DEFAULT NULL,
  `NU_POS_LIM_INFERIOR` int(11) DEFAULT NULL,
  `DC_URL_FETCH_JOGOS` varchar(2048) DEFAULT NULL
) ;


CREATE TABLE IF NOT EXISTS `tb_campeonato_time` (
  `ID_CAMPEONATO` int(11) NOT NULL,
  `ID_TIME` int(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS `tb_chave` (
  `TP_CHAVE` varchar(2) NOT NULL,
`ID_CHAVE` int(11) NOT NULL,
  `NU_ORDEM` int(11) NOT NULL,
  `ID_FASE` int(11) NOT NULL,
  `ID_TIME_VENCEDOR` int(11) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `tb_fase` (
  `TP_FASE` varchar(2) NOT NULL,
`ID_FASE` int(11) NOT NULL,
  `NM_FASE` longtext,
  `NU_ORDEM` int(11) NOT NULL,
  `ID_CAMPEONATO` int(11) NOT NULL,
  `ID_PROX_FASE` int(11) DEFAULT NULL
) ;

CREATE TABLE IF NOT EXISTS `tb_liga` (
`ID_LIGA` bigint(20) NOT NULL,
  `IN_ATIVA` bit(1) NOT NULL,
  `NM_LIGA` varchar(500) NOT NULL,
  `DC_PERMALINK` varchar(1000) NOT NULL,
  `ID_APOSTADOR_ADMIN` int(11) NOT NULL
) ;

CREATE TABLE IF NOT EXISTS `tb_liga_apostador` (
  `ID_LIGA` bigint(20) NOT NULL,
  `ID_APOSTADOR` int(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS `tb_msg_template` (
  `TP_TEMPLATE` varchar(1) NOT NULL,
`ID_TEMPLATE` int(11) NOT NULL,
  `DC_CLASSPATH` longtext NOT NULL,
  `DC_LINK_ACESSO` varchar(255) DEFAULT NULL,
  `TP_NOTIFICACAO` int(11) DEFAULT NULL,
  `NM_ASSUNTO` longtext,
  `DC_EMAIL_REMETENTE` longtext,
  `NM_REMETENTE` longtext
) ;

CREATE TABLE IF NOT EXISTS `tb_palpite` (
`ID_PALPITE` int(11) NOT NULL,
  `IN_COMPUTADO` bit(1) NOT NULL,
  `DH_PALPITE` datetime NOT NULL,
  `NU_PONTOS` int(11) NOT NULL,
  `ID_APOSTADOR` int(11) NOT NULL,
  `ID_PLACAR` int(11) NOT NULL,
  `ID_PARTIDA` int(11) NOT NULL,
  `DH_COMPUTADO` datetime DEFAULT NULL,
  `IN_EMPATE` bit(1) NOT NULL,
  `IN_ERRADO` bit(1) NOT NULL,
  `IN_GOL` bit(1) NOT NULL,
  `IN_PLACAR` bit(1) NOT NULL,
  `IN_VENCEDOR` bit(1) NOT NULL
);

CREATE TABLE IF NOT EXISTS `tb_partida` (
  `TP_PARTIDA` varchar(1) NOT NULL,
`ID_PARTIDA` int(11) NOT NULL,
  `IN_COMPUTADA` bit(1) NOT NULL,
  `DH_PARTIDA` datetime DEFAULT NULL,
  `DC_LOCAL` varchar(255) DEFAULT NULL,
  `ST_PARTIDA` varchar(2) NOT NULL,
  `ID_TIME_MANDANTE` int(11) NOT NULL,
  `ID_TIME_VISITANTE` int(11) NOT NULL,
  `ID_PLACAR_PENALTI` int(11) DEFAULT NULL,
  `ID_PLACAR` int(11) DEFAULT NULL,
  `ID_RODADA` int(11) DEFAULT NULL,
  `ID_CHAVE` int(11) DEFAULT NULL,
  `ID_FASE` int(11) NOT NULL,
  `DH_COMPUTADA` datetime DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `tb_placar` (
`ID_PLACAR` int(11) NOT NULL,
  `NU_GOLS_MANDANTE` int(11) NOT NULL,
  `NU_GOLS_VISITANTE` int(11) NOT NULL
) ;

CREATE TABLE IF NOT EXISTS `tb_pontuacao` (
`ID_PONTUACAO` int(11) NOT NULL,
  `NU_APROVEITAMENTO` double NOT NULL,
  `NU_DERROTAS` int(11) NOT NULL,
  `NU_EMPATES` int(11) NOT NULL,
  `NU_GOLS_CONTRA` int(11) NOT NULL,
  `NU_GOLS_PRO` int(11) NOT NULL,
  `NU_JOGOS` int(11) NOT NULL,
  `NU_PONTOS` int(11) NOT NULL,
  `NU_SALDO_GOLS` int(11) NOT NULL,
  `NU_VITORIAS` int(11) NOT NULL,
  `ID_RANKING` int(11) NOT NULL,
  `ID_TIME` int(11) NOT NULL,
  `NU_POSICAO` int(11) NOT NULL,
  `IN_ELITE` bit(1) DEFAULT NULL,
  `IN_INFERIOR` bit(1) DEFAULT NULL,
  `ST_POSICAO` int(11) NOT NULL
) ;

CREATE TABLE IF NOT EXISTS `tb_ranking` (
  `TP_RANKING` varchar(1) NOT NULL,
`ID_RANKING` int(11) NOT NULL,
  `DC_RANKING` varchar(255) DEFAULT NULL,
  `ID_FASE` int(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS `tb_rodada` (
`ID_RODADA` int(11) NOT NULL,
  `NM_RODADA` varchar(255) DEFAULT NULL,
  `ID_RANKING` int(11) NOT NULL,
  `DT_FIM_RODADA` date DEFAULT NULL,
  `DT_INI_RODADA` date DEFAULT NULL,
  `NU_ORDEM` int(11) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `tb_time` (
`ID_TIME` int(11) NOT NULL,
  `DC_BRASAO_IMG` longtext,
  `NM_TIME` varchar(255) NOT NULL,
  `NM_ALIAS` varchar(3) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `tb_token_password` (
  `ID_TOKEN` varchar(128) NOT NULL,
  `IN_UTILIZADO` bit(1) DEFAULT NULL,
  `DH_VALIDADE` datetime DEFAULT NULL,
  `ID_USUARIO` int(11) NOT NULL
);

CREATE TABLE IF NOT EXISTS `tb_usuario` (
`ID_USUARIO` int(11) NOT NULL,
  `DH_CADASTRO` datetime NOT NULL,
  `DC_EMAIL` longtext,
  `NM_PERFIL` varchar(255) NOT NULL,
  `DC_SENHA` varchar(64) NOT NULL,
  `DH_ULTIMO_LOGIN` datetime DEFAULT NULL,
  `NM_SOCIAL_NET` varchar(255) DEFAULT NULL,
  `NM_SOCIAL_ID` varchar(255) DEFAULT NULL
);



ALTER TABLE `tb_apostador`
 ADD PRIMARY KEY (`ID_APOSTADOR`), ADD KEY `FK_7pm36bw6e420aqoxm0mysknqv` (`ID_USUARIO`), ADD KEY `FK_80d07jf7e83010xv9grqodw1y` (`ID_TIME`);


ALTER TABLE `tb_apostador_campeonato`
 ADD PRIMARY KEY (`ID_CAMPEONATO`,`ID_APOSTADOR`), ADD KEY `FK_emaoe9e2v5h7qfc8prccsu18r` (`ID_APOSTADOR`);


ALTER TABLE `tb_apostador_pontuacao_rodada`
 ADD PRIMARY KEY (`ID_RODADA`,`ID_CAMPEONATO`,`ID_APOSTADOR`), ADD KEY `FK_sqsrhj4g6w3ypitpry21y2lw8` (`ID_CAMPEONATO`), ADD KEY `FK_sa7ecbti2um7mpe9fxm50ym4x` (`ID_APOSTADOR`);


ALTER TABLE `tb_campeonato`
 ADD PRIMARY KEY (`ID_CAMPEONATO`);


ALTER TABLE `tb_campeonato_param`
 ADD PRIMARY KEY (`ID_CAMPEONATO`);


ALTER TABLE `tb_campeonato_time`
 ADD PRIMARY KEY (`ID_CAMPEONATO`,`ID_TIME`), ADD KEY `FK_71upehh9pt5s0uo78q5trleit` (`ID_TIME`);


ALTER TABLE `tb_chave`
 ADD PRIMARY KEY (`ID_CHAVE`), ADD KEY `FK_qtxq0f00d2stfhun48409rgjh` (`ID_FASE`), ADD KEY `FK_h85i4250xxcjq6wr3nskreuoh` (`ID_TIME_VENCEDOR`);


ALTER TABLE `tb_fase`
 ADD PRIMARY KEY (`ID_FASE`), ADD KEY `FK_2qc9e8u19d9t5bae5ydxh4rh0` (`ID_CAMPEONATO`), ADD KEY `FK_chds4aayrfjyeltucvg7y2ov8` (`ID_PROX_FASE`);


ALTER TABLE `tb_liga`
 ADD PRIMARY KEY (`ID_LIGA`), ADD KEY `FK_6bekk96bp3oci6ldpyfbvr6b5` (`ID_APOSTADOR_ADMIN`);


ALTER TABLE `tb_liga_apostador`
 ADD PRIMARY KEY (`ID_LIGA`,`ID_APOSTADOR`), ADD KEY `FK_p8j5b4cxb8nfjd9vp22svvrhe` (`ID_APOSTADOR`);


ALTER TABLE `tb_msg_template`
 ADD PRIMARY KEY (`ID_TEMPLATE`);


ALTER TABLE `tb_palpite`
 ADD PRIMARY KEY (`ID_PALPITE`), ADD KEY `FK_44u3rrq6u9t32r8hil3qis2c3` (`ID_APOSTADOR`), ADD KEY `FK_oh2529l2guq8ojg3mfilo3g55` (`ID_PLACAR`), ADD KEY `FK_5oidio7s06votukleyfsbpexb` (`ID_PARTIDA`);


ALTER TABLE `tb_partida`
 ADD PRIMARY KEY (`ID_PARTIDA`), ADD KEY `FK_r3oebg5bvx8cs7kcrey3dn2tk` (`ID_TIME_MANDANTE`), ADD KEY `FK_tn4aj1t7uit5j2a80n3bbkogd` (`ID_TIME_VISITANTE`), ADD KEY `FK_9hq4wjoukrgrpa4ris899nuqs` (`ID_PLACAR_PENALTI`), ADD KEY `FK_8a73paaswn8sj55d1tet8anhs` (`ID_PLACAR`), ADD KEY `FK_ah2tofbc5djepahg8wb3yyktr` (`ID_RODADA`), ADD KEY `FK_s8dajhjs9d5y3bxcqfg6jtuy1` (`ID_CHAVE`), ADD KEY `FK_5gingwcoxugityaathls3doom` (`ID_FASE`);


ALTER TABLE `tb_placar`
 ADD PRIMARY KEY (`ID_PLACAR`);


ALTER TABLE `tb_pontuacao`
 ADD PRIMARY KEY (`ID_PONTUACAO`), ADD KEY `FK_4uqw8yyn6tktvhwhyveft2ydp` (`ID_RANKING`), ADD KEY `FK_qpedluw45ws97qkqjfsh617u7` (`ID_TIME`);


ALTER TABLE `tb_ranking`
 ADD PRIMARY KEY (`ID_RANKING`), ADD KEY `FK_9d4apd4joeo0b6dsb6wqsj9xi` (`ID_FASE`);


ALTER TABLE `tb_rodada`
 ADD PRIMARY KEY (`ID_RODADA`), ADD KEY `FK_bllm7ea78x5eumwnx6f1i285y` (`ID_RANKING`);


ALTER TABLE `tb_time`
 ADD PRIMARY KEY (`ID_TIME`);


ALTER TABLE `tb_token_password`
 ADD PRIMARY KEY (`ID_TOKEN`), ADD KEY `FK_7cfcf9i3mc9w5wglmgqljjx1e` (`ID_USUARIO`);


ALTER TABLE `tb_usuario`
 ADD PRIMARY KEY (`ID_USUARIO`);
 
 
 --
-- Constraints for table `tb_apostador`
--
ALTER TABLE `tb_apostador`
ADD CONSTRAINT `FK_7pm36bw6e420aqoxm0mysknqv` FOREIGN KEY (`ID_USUARIO`) REFERENCES `tb_usuario` (`ID_USUARIO`),
ADD CONSTRAINT `FK_80d07jf7e83010xv9grqodw1y` FOREIGN KEY (`ID_TIME`) REFERENCES `tb_time` (`ID_TIME`);

--
-- Constraints for table `tb_apostador_campeonato`
--
ALTER TABLE `tb_apostador_campeonato`
ADD CONSTRAINT `FK_emaoe9e2v5h7qfc8prccsu18r` FOREIGN KEY (`ID_APOSTADOR`) REFERENCES `tb_apostador` (`ID_APOSTADOR`),
ADD CONSTRAINT `FK_k869ted88yxtilimanhc4visf` FOREIGN KEY (`ID_CAMPEONATO`) REFERENCES `tb_campeonato` (`ID_CAMPEONATO`);

--
-- Constraints for table `tb_apostador_pontuacao_rodada`
--
ALTER TABLE `tb_apostador_pontuacao_rodada`
ADD CONSTRAINT `FK_p364d1d7y0pn71liyggdip4jd` FOREIGN KEY (`ID_RODADA`) REFERENCES `tb_rodada` (`ID_RODADA`),
ADD CONSTRAINT `FK_sa7ecbti2um7mpe9fxm50ym4x` FOREIGN KEY (`ID_APOSTADOR`) REFERENCES `tb_apostador` (`ID_APOSTADOR`),
ADD CONSTRAINT `FK_sqsrhj4g6w3ypitpry21y2lw8` FOREIGN KEY (`ID_CAMPEONATO`) REFERENCES `tb_campeonato` (`ID_CAMPEONATO`);

--
-- Constraints for table `tb_campeonato_time`
--
ALTER TABLE `tb_campeonato_time`
ADD CONSTRAINT `FK_5hr05plvtb8tddkjnucef1hd1` FOREIGN KEY (`ID_CAMPEONATO`) REFERENCES `tb_campeonato` (`ID_CAMPEONATO`),
ADD CONSTRAINT `FK_71upehh9pt5s0uo78q5trleit` FOREIGN KEY (`ID_TIME`) REFERENCES `tb_time` (`ID_TIME`);

--
-- Constraints for table `tb_chave`
--
ALTER TABLE `tb_chave`
ADD CONSTRAINT `FK_h85i4250xxcjq6wr3nskreuoh` FOREIGN KEY (`ID_TIME_VENCEDOR`) REFERENCES `tb_time` (`ID_TIME`),
ADD CONSTRAINT `FK_qtxq0f00d2stfhun48409rgjh` FOREIGN KEY (`ID_FASE`) REFERENCES `tb_fase` (`ID_FASE`);

--
-- Constraints for table `tb_fase`
--
ALTER TABLE `tb_fase`
ADD CONSTRAINT `FK_2qc9e8u19d9t5bae5ydxh4rh0` FOREIGN KEY (`ID_CAMPEONATO`) REFERENCES `tb_campeonato` (`ID_CAMPEONATO`),
ADD CONSTRAINT `FK_chds4aayrfjyeltucvg7y2ov8` FOREIGN KEY (`ID_PROX_FASE`) REFERENCES `tb_fase` (`ID_FASE`);

--
-- Constraints for table `tb_liga`
--
ALTER TABLE `tb_liga`
ADD CONSTRAINT `FK_6bekk96bp3oci6ldpyfbvr6b5` FOREIGN KEY (`ID_APOSTADOR_ADMIN`) REFERENCES `tb_apostador` (`ID_APOSTADOR`);

--
-- Constraints for table `tb_liga_apostador`
--
ALTER TABLE `tb_liga_apostador`
ADD CONSTRAINT `FK_oekinorak8d6qrjkk23i9u9w2` FOREIGN KEY (`ID_LIGA`) REFERENCES `tb_liga` (`ID_LIGA`),
ADD CONSTRAINT `FK_p8j5b4cxb8nfjd9vp22svvrhe` FOREIGN KEY (`ID_APOSTADOR`) REFERENCES `tb_apostador` (`ID_APOSTADOR`);

--
-- Constraints for table `tb_log_processamento`
--
ALTER TABLE `tb_log_processamento`
ADD CONSTRAINT `FK_ablxc3s83c5voynmqihyvyrtr` FOREIGN KEY (`ID_RODADA`) REFERENCES `tb_rodada` (`ID_RODADA`),
ADD CONSTRAINT `FK_g0dtujuq6l7mh7layl62eycrh` FOREIGN KEY (`ID_CHAVE`) REFERENCES `tb_chave` (`ID_CHAVE`),
ADD CONSTRAINT `FK_gksxmga0oj6tvup4ux6mr0u0u` FOREIGN KEY (`ID_CAMPEONATO`) REFERENCES `tb_campeonato` (`ID_CAMPEONATO`);

--
-- Constraints for table `tb_palpite`
--
ALTER TABLE `tb_palpite`
ADD CONSTRAINT `FK_44u3rrq6u9t32r8hil3qis2c3` FOREIGN KEY (`ID_APOSTADOR`) REFERENCES `tb_apostador` (`ID_APOSTADOR`),
ADD CONSTRAINT `FK_5oidio7s06votukleyfsbpexb` FOREIGN KEY (`ID_PARTIDA`) REFERENCES `tb_partida` (`ID_PARTIDA`),
ADD CONSTRAINT `FK_oh2529l2guq8ojg3mfilo3g55` FOREIGN KEY (`ID_PLACAR`) REFERENCES `tb_placar` (`ID_PLACAR`);

--
-- Constraints for table `tb_partida`
--
ALTER TABLE `tb_partida`
ADD CONSTRAINT `FK_5gingwcoxugityaathls3doom` FOREIGN KEY (`ID_FASE`) REFERENCES `tb_fase` (`ID_FASE`),
ADD CONSTRAINT `FK_8a73paaswn8sj55d1tet8anhs` FOREIGN KEY (`ID_PLACAR`) REFERENCES `tb_placar` (`ID_PLACAR`),
ADD CONSTRAINT `FK_9hq4wjoukrgrpa4ris899nuqs` FOREIGN KEY (`ID_PLACAR_PENALTI`) REFERENCES `tb_placar` (`ID_PLACAR`),
ADD CONSTRAINT `FK_ah2tofbc5djepahg8wb3yyktr` FOREIGN KEY (`ID_RODADA`) REFERENCES `tb_rodada` (`ID_RODADA`),
ADD CONSTRAINT `FK_r3oebg5bvx8cs7kcrey3dn2tk` FOREIGN KEY (`ID_TIME_MANDANTE`) REFERENCES `tb_time` (`ID_TIME`),
ADD CONSTRAINT `FK_s8dajhjs9d5y3bxcqfg6jtuy1` FOREIGN KEY (`ID_CHAVE`) REFERENCES `tb_chave` (`ID_CHAVE`),
ADD CONSTRAINT `FK_tn4aj1t7uit5j2a80n3bbkogd` FOREIGN KEY (`ID_TIME_VISITANTE`) REFERENCES `tb_time` (`ID_TIME`);

--
-- Constraints for table `tb_pontuacao`
--
ALTER TABLE `tb_pontuacao`
ADD CONSTRAINT `FK_4uqw8yyn6tktvhwhyveft2ydp` FOREIGN KEY (`ID_RANKING`) REFERENCES `tb_ranking` (`ID_RANKING`),
ADD CONSTRAINT `FK_qpedluw45ws97qkqjfsh617u7` FOREIGN KEY (`ID_TIME`) REFERENCES `tb_time` (`ID_TIME`);

--
-- Constraints for table `tb_ranking`
--
ALTER TABLE `tb_ranking`
ADD CONSTRAINT `FK_9d4apd4joeo0b6dsb6wqsj9xi` FOREIGN KEY (`ID_FASE`) REFERENCES `tb_fase` (`ID_FASE`);

--
-- Constraints for table `tb_rodada`
--
ALTER TABLE `tb_rodada`
ADD CONSTRAINT `FK_bllm7ea78x5eumwnx6f1i285y` FOREIGN KEY (`ID_RANKING`) REFERENCES `tb_ranking` (`ID_RANKING`);

--
-- Constraints for table `tb_token_password`
--
ALTER TABLE `tb_token_password`
ADD CONSTRAINT `FK_7cfcf9i3mc9w5wglmgqljjx1e` FOREIGN KEY (`ID_USUARIO`) REFERENCES `tb_usuario` (`ID_USUARIO`);
