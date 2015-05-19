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
    'br/com/debico/notify/email/templates/esqueci_minha_senha.vm', 
    'http://www.debico.com.br/app/public/senha?token=%s', 
    3, 
    'Camarada, você esqueceu a sua senha? Recupere já!', 
    'De Bico', 
    'noreply@debico.com.br');
