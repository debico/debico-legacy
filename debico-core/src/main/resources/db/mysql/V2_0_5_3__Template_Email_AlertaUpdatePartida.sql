DELETE FROM tb_msg_template WHERE TP_NOTIFICACAO IN (4,5);

INSERT INTO tb_msg_template 
    (TP_TEMPLATE, 
    ID_TEMPLATE, 
    DC_CLASSPATH, 
    DC_LINK_ACESSO, 
    TP_NOTIFICACAO, 
    NM_ASSUNTO, 
    NM_REMETENTE, 
    DC_EMAIL_REMETENTE) 
VALUES 
    ('E', 
    NULL, 
    'br/com/debico/notify/email/templates/alerta_update_partida.vm', 
    'http://www.debico.com.br/app/campeonatos/%s/%s', 
    4, 
    'Administrador: informe de atualização de partidas', 
    'De Bico', 
    'noreply@debico.com.br');
    
    INSERT INTO tb_msg_template 
    (TP_TEMPLATE, 
    ID_TEMPLATE, 
    DC_CLASSPATH, 
    DC_LINK_ACESSO, 
    TP_NOTIFICACAO, 
    NM_ASSUNTO, 
    NM_REMETENTE, 
    DC_EMAIL_REMETENTE) 
VALUES 
    ('E', 
    NULL, 
    'br/com/debico/notify/email/templates/alerta_erro_update_partida.vm', 
    'http://www.debico.com.br/app/campeonatos/%s/%s', 
    5, 
    'Administrador: houve um problema na atualização das partidas', 
    'De Bico', 
    'noreply@debico.com.br');
