-- Zera o placar para reprocessamento.

update tb_palpite set in_computado = 0, nu_pontos = 0, dh_computado = null;
update tb_partida set in_computada = 0, st_partida = 'ND', dh_computada = null;
update tb_pontuacao set 
    nu_aproveitamento = 0.0, nu_derrotas = 0, nu_empates = 0, nu_gols_contra = 0, 
    nu_gols_pro = 0, nu_jogos = 0, nu_saldo_gols = 0, nu_vitorias = 0, nu_pontos = 0, st_posicao = 0;
update tb_apostador_campeonato set 
    nu_empate = 0, nu_gols = 0, nu_errados = 0,
    nu_placar = 0, nu_pontos = 0, nu_vencedor = 0;
    
-- update do backup
    
select concat('update tb_apostador_campeonato set  nu_empate = ',cast(nu_empate as char(10)),', nu_gols = ',cast(nu_gols as char(10)),', 
    nu_errados = ',cast(nu_errados as char(10)),',
    nu_placar = ',cast(nu_placar as char(10)),', nu_pontos = ',cast(nu_pontos as char(10)),', nu_vencedor = ',cast(nu_vencedor as char(10)),'
    where id_apostador = ',cast(id_apostador as char(10)),' and id_campeonato = 2;') as query1
from tb_apostador_campeonato where id_campeonato = 2 and id_apostador = ?;

-- partida
update tb_partida set 
in_computada = 0, st_partida = 'ND', dh_computada = null
where id_fase = 4;

update tb_pontuacao set 
    nu_aproveitamento = 0.0, nu_derrotas = 0, nu_empates = 0, nu_gols_contra = 0, 
    nu_gols_pro = 0, nu_jogos = 0, nu_saldo_gols = 0, nu_vitorias = 0, nu_pontos = 0, st_posicao = 0,
    in_elite = 0, in_inferior = 0, nu_posicao = 1
where id_ranking = 4;

update tb_apostador_campeonato set 
    nu_empate = 0, nu_gols = 0, nu_errados = 0,
    nu_placar = 0, nu_pontos = 0, nu_vencedor = 0
where id_campeonato = 3;

update tb_apostador_pontuacao_rodada set
nu_empate = 0, nu_gols = 0, nu_errados = 0,
    nu_placar = 0, nu_pontos = 0, nu_vencedor = 0
    where id_campeonato = 3;

update tb_palpite set 
in_computado = 0, 
nu_pontos = 0, 
DH_computado = null, 
in_empate = 0, 
in_errado = 0, 
in_gol = 0, 
in_placar = 0,
in_vencedor = 0 where id_partida in (select id_partida from tb_partida where id_fase = 4);