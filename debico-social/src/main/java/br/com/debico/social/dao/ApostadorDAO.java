package br.com.debico.social.dao;

import java.util.Date;
import java.util.List;

import br.com.debico.model.Apostador;
import br.com.debico.model.Usuario;
import br.com.debico.model.campeonato.Campeonato;
import br.com.tecnobiz.spring.config.dao.Dao;

public interface ApostadorDAO extends Dao<Apostador, Integer> {

    /**
     * Seleciona pelo email do {@link Usuario}
     * 
     * @param email
     * @return
     */
    Apostador selecionarPorEmail(String email);

    /**
     * Seleciona pelo ID do {@link Usuario}
     * 
     * @param idUsuario
     * @return
     */
    Apostador selecionarPorIdUsuario(int idUsuario);

    /**
     * Efetua a busca pelo Id de usuário da rede social.
     * 
     * @param socialUserId
     * @return
     */
    Apostador selecionarPorSocialId(String socialUserId);

    /**
     * Recupera todas as informações do {@link Apostador} por meio de FETCH
     * JOINS.
     * 
     * @param email
     * @return
     * @since 1.2.0
     */
    Apostador selecionarPerfilPorEmail(String email);

    /**
     * Recupera uma lista de apostadores que estão inscritos em determinado
     * {@link Campeonato} e que não palpitaram nas partidas da data
     * especificada.
     * 
     * @param campeonato
     *            que o {@link Apostador} está inscrito
     * @param dataPartida
     *            data (sem informações sobre hora) de realização da partida.
     * @return {@link List} com a relação dos apostadores
     * @since 1.1.0-RC1
     */
    List<Apostador> selecionarApostadoresSemPalpite(Campeonato campeonato,
	    Date dataInicioPartida, Date dataFimPartida);

    /**
     * Efetua a busca de apostadores por parte do nome limitando o número máximo
     * de itens retornados em 20.
     * 
     * @param parteNome
     * @return lista com a relação dos apostadores
     * @since 2.0.0
     */
    List<Apostador> procurarPorParteNome(String parteNome);
}
