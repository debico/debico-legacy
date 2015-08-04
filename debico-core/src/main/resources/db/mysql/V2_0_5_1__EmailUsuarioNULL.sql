-- Como a chave agora tambem sera por meio das redes sociais, liberamos a obrigatoriedade do email
ALTER TABLE tb_usuario
CHANGE COLUMN `DC_EMAIL` `DC_EMAIL` LONGTEXT NULL ;