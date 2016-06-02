package br.com.debico.model.campeonato;

import java.util.Date;
import java.util.Set;

import br.com.debico.model.Time;

/**
 * Interface para as definições de campeonatos.
 * 
 * @author r_fernandes
 *
 */
public interface Campeonato {

    public static final String PONTOS_CORRIDOS = "PC";
    public static final String COPA = "CO";

    String getNome();

    int getId();

    void setNome(final String nome);

    /**
     * Campeonato está ativo e correndo? Pode ser palpitado?
     * 
     * @return
     */
    boolean isAtivo();

    /**
     * O Campeonato já foi finalizado?
     * 
     * @return
     */
    boolean isFinalizado();

    /**
     * Times que participam desse campeonato.
     * 
     * @return
     */
    Set<Time> getTimes();

    /**
     * Recupera o permalink referente à esse campeonato.
     * 
     * @return
     */
    String getPermalink();

    /**
     * Image da Home do Campeonato. Uma imagem bem grande e legal nos tamanhos:
     * <code>970x500</code>
     * 
     * @return uma string com o nome da imagem.
     */
    String getImagemCapa();

    /**
     * Tipo do campeonato de acordo com as variáveis estáticas dessa interface.
     * 
     * @return
     */
    String getTipo();

    /**
     * Recupera os dados de parametrização desse campeonato. É uma estrutura de
     * meta-dados para auxiliar funções e casos de uso de apoio. Não carrega do
     * banco por padrão.
     * 
     * @return
     */
    ParametrizacaoCampeonato getParametrizacao();

    /**
     * Data de Início do Campeonato.
     * 
     * @return
     */
    Date getDataInicio();

    /**
     * Data de Fim do Campeonato.
     * 
     * @return
     */
    Date getDataFim();

}
