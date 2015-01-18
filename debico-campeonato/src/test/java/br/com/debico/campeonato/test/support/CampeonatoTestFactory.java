package br.com.debico.campeonato.test.support;

import java.util.Date;

import javax.inject.Inject;

import org.joda.time.DateTime;

import br.com.debico.campeonato.dao.CampeonatoDAO;
import br.com.debico.campeonato.dao.FaseDAO;
import br.com.debico.campeonato.dao.PartidaDAO;
import br.com.debico.campeonato.dao.PontuacaoTimeDAO;
import br.com.debico.campeonato.dao.RankingDAO;
import br.com.debico.campeonato.dao.RodadaDAO;
import br.com.debico.core.DebicoException;
import br.com.debico.model.PartidaRodada;
import br.com.debico.model.Placar;
import br.com.debico.model.Time;
import br.com.debico.model.campeonato.CampeonatoImpl;
import br.com.debico.model.campeonato.CampeonatoParametrizacao;
import br.com.debico.model.campeonato.CampeonatoPontosCorridos;
import br.com.debico.model.campeonato.FaseGrupos;
import br.com.debico.model.campeonato.Grupo;
import br.com.debico.model.campeonato.PontuacaoTime;
import br.com.debico.model.campeonato.Rodada;

public class CampeonatoTestFactory {

    @Inject
    protected RankingDAO rankingDAO;

    @Inject
    protected RodadaDAO rodadaDAO;

    @Inject
    protected PontuacaoTimeDAO pontuacaoTimeDAO;

    @Inject
    protected FaseDAO faseDAO;

    @Inject
    protected CampeonatoDAO campeonatoDAO;

    @Inject
    protected PartidaDAO partidaDAO;

    public Time BRASIL;
    public Time ARGENTINA ;
    public Time EUA;
    public Time HOLANDA;
    public Time NIGERIA;
    public Time ITALIA;

    public CampeonatoImpl COPA_GRUPO_UNICO;

    public Rodada RODADA_UNICA;

    public Grupo GRUPO_UNICO;

    public FaseGrupos FASE_UNICA;

  //  public Apostador ADRIANO, ZAPA, ZANINI;

    public PartidaRodada PARTIDA1, PARTIDA2, PARTIDA3;

    public Date AMANHA;

    public CampeonatoTestFactory() {

    }

    /*
     * Partidas:
     * 
     * partida1 = new PartidaRodada(BRASIL, ARGENTINA, new Placar(2, 0));
     * partida2 = new PartidaRodada(EUA, HOLANDA, new Placar(1, 3)); 
     * partida3 = new PartidaRodada(NIGERIA, ITALIA, new Placar(1, 1));
     * 
     * Resultados esperados a partir dos palpites abaixo:
     * 
     * Apostador{Zaparoli} 6(P) 1(A) 0(V) 0(E) 1(G) 1(R) 
     * Apostador{Adriano}  6(P) 0(A) 2(V) 0(E) 2(G) 1(R) 
     * Apostador{Zanini}   3(P) 0(A) 0(V) 1(E) 1(G) 2(R)
     */
    /**
     * Cria um campeonato com uma fase e um grupo.
     * <p/>
     * Objetos definidos: {@link #COPA_GRUPO_UNICO}, {@link #RODADA_UNICA},
     * {@link #GRUPO_UNICO}, {@link #FASE_UNICA}.
     * <p/>
     * Apostadores: {@link #ZANINI}, {@link #ZAPA}, {@link #ADRIANO}
     * <p/>
     * Partidas: {@link #PARTIDA1}, {@link #PARTIDA2}, {@link #PARTIDA3}
     * 
     * @throws DebicoException
     */
    public void criarCampeonatoPontosCorridos() throws DebicoException {
        BRASIL = new Time("Brasil");
        ARGENTINA = new Time("Argentina");
        EUA = new Time("EUA");
        HOLANDA = new Time("Holanda");
        NIGERIA = new Time("Nigeria");
        ITALIA = new Time("Italia");
        
        AMANHA = new DateTime().plusDays(1).toDate();

        COPA_GRUPO_UNICO = new CampeonatoPontosCorridos("Copa do Mundo dos Solteiros");
        COPA_GRUPO_UNICO.setAtivo(true);
        COPA_GRUPO_UNICO.setFinalizado(false);

        COPA_GRUPO_UNICO.addTime(ARGENTINA);
        COPA_GRUPO_UNICO.addTime(BRASIL);
        COPA_GRUPO_UNICO.addTime(ITALIA);
        COPA_GRUPO_UNICO.addTime(NIGERIA);
        COPA_GRUPO_UNICO.addTime(EUA);
        COPA_GRUPO_UNICO.addTime(HOLANDA);
        
        CampeonatoParametrizacao param = new CampeonatoParametrizacao();
        param.setPosicaoLimiteElite(2);
        param.setPosicaoLimiteInferior(5);
        
        COPA_GRUPO_UNICO.setParametrizacao(param);
        
        // campeonato
        campeonatoDAO.inserir(COPA_GRUPO_UNICO);

        // fase
        FASE_UNICA = new FaseGrupos();
        FASE_UNICA.setNome("Fase Grupos");
        FASE_UNICA.setCampeonato(COPA_GRUPO_UNICO);
        faseDAO.inserir(FASE_UNICA);
        // grupo
        GRUPO_UNICO = new Grupo();
        GRUPO_UNICO.setFase(FASE_UNICA);
        GRUPO_UNICO.setNome("Grupo Unico");
        rankingDAO.inserir(GRUPO_UNICO);
        // rodada
        RODADA_UNICA = new Rodada("Rodada Unica");
        RODADA_UNICA.setRanking(GRUPO_UNICO);
        rodadaDAO.inserir(RODADA_UNICA);
        // times no grupo
        pontuacaoTimeDAO
                .inserirPontuacao(new PontuacaoTime(GRUPO_UNICO, BRASIL));
        pontuacaoTimeDAO.inserirPontuacao(new PontuacaoTime(GRUPO_UNICO,
                ARGENTINA));
        pontuacaoTimeDAO.inserirPontuacao(new PontuacaoTime(GRUPO_UNICO, EUA));
        pontuacaoTimeDAO.inserirPontuacao(new PontuacaoTime(GRUPO_UNICO,
                HOLANDA));
        pontuacaoTimeDAO.inserirPontuacao(new PontuacaoTime(GRUPO_UNICO,
                NIGERIA));
        pontuacaoTimeDAO
                .inserirPontuacao(new PontuacaoTime(GRUPO_UNICO, ITALIA));

//        ZANINI = new Apostador("Zanini", new Usuario("zanini@bolao.com.br"));
//        ZAPA = new Apostador("Zaparoli", new Usuario("zapa@bolao.com.br"));
//        ADRIANO = new Apostador("Adriano", new Usuario("adriano@bolao.com.br"));
//
//        // apostadores
//        apostadorDAO.inserir(ZANINI);
//        apostadorDAO.inserir(ZAPA);
//        apostadorDAO.inserir(ADRIANO);
//
//        // inscricao
//        apostadorService
//                .inscreverApostadorCampeonato(ADRIANO, COPA_GRUPO_UNICO);
//        apostadorService.inscreverApostadorCampeonato(ZANINI, COPA_GRUPO_UNICO);
//        apostadorService.inscreverApostadorCampeonato(ZAPA, COPA_GRUPO_UNICO);

        // partidas
        PARTIDA1 = new PartidaRodada(BRASIL, ARGENTINA, new Placar(2, 0));
        PARTIDA2 = new PartidaRodada(EUA, HOLANDA, new Placar(1, 3));
        PARTIDA3 = new PartidaRodada(NIGERIA, ITALIA, new Placar(1, 1));

        PARTIDA1.setRodada(RODADA_UNICA);
        PARTIDA2.setRodada(RODADA_UNICA);
        PARTIDA3.setRodada(RODADA_UNICA);
        PARTIDA1.setDataHoraPartida(AMANHA);
        PARTIDA2.setDataHoraPartida(AMANHA);
        PARTIDA3.setDataHoraPartida(AMANHA);
        PARTIDA1.setFase(FASE_UNICA);
        PARTIDA2.setFase(FASE_UNICA);
        PARTIDA3.setFase(FASE_UNICA);

        partidaDAO.inserir(PARTIDA1);
        partidaDAO.inserir(PARTIDA2);
        partidaDAO.inserir(PARTIDA3);
    }

}
