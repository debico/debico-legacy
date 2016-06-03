-- Adição de novos campos de data na tabela de campeonato
-- DT_INICIO
-- DT_FIM
ALTER TABLE tb_campeonato
ADD COLUMN `DT_INICIO` DATE NULL ;

ALTER TABLE tb_campeonato
ADD COLUMN `DT_FIM` DATE NULL ;

ALTER TABLE tb_campeonato_param
ADD COLUMN `DC_URL_FETCH_JOGOS` VARCHAR(2048) NULL ;

