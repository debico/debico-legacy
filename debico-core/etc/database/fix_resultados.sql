-- Zera o placar para reprocessamento.

update tb_palpite set in_computado = 0, nu_pontos = 0, dh_computado = null;
update tb_partida set in_computada = 0, st_partida = 'ND', dh_computada = null;
update tb_pontuacao set 
    nu_aproveitamento = 0.0, nu_derrotas = 0, nu_empates = 0, nu_gols_contra = 0, 
    nu_gols_pro = 0, nu_jogos = 0, nu_saldo_gols = 0, nu_vitorias = 0, nu_pontos = 0, st_posicao = 0;
update tb_apostador_campeonato set 
    nu_empate = 0, nu_gols = 0, nu_errados = 0,
    nu_placar = 0, nu_pontos = 0, nu_vencedor = 0;