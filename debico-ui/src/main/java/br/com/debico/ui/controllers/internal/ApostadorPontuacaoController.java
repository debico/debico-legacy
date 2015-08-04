package br.com.debico.ui.controllers.internal;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.debico.bolao.services.ApostadorPontuacaoService;
import br.com.debico.campeonato.services.CampeonatoService;
import br.com.debico.campeonato.services.RodadaService;
import br.com.debico.model.AbstractApostadorPontuacao;
import br.com.debico.model.campeonato.Campeonato;
import br.com.debico.social.services.LigaService;
import br.com.debico.ui.controllers.AbstractViewController;

import static com.google.common.base.Objects.firstNonNull;

/**
 * Carrega o ranking dos Apostadores, por campeonato.
 * 
 * @author ricardozanini
 *
 */
@Controller
public class ApostadorPontuacaoController extends AbstractViewController {

    @Inject
    @Named("campeonatoServiceImpl")
    private CampeonatoService campeonatoService;

    @Inject
    private ApostadorPontuacaoService apostadorPontuacaoService;

    @Inject
    private LigaService ligaService;

    @Inject
    private RodadaService rodadaService;

    @Override
    public void init() {
        super.init();
        this.carregarFiltroLigas();
    }

    @Override
    protected String getViewName() {
        return "ranking";
    }

    @RequestMapping(value = "/ranking/{permalink}", method = RequestMethod.GET)
    public ModelAndView ranking(
            @PathVariable(value = "permalink") String permalink,
            @RequestParam(value = "liga", required = false) final Long idLigaParam,
            @RequestParam(value = "rodada", required = false) final Integer idRodadaParam) {
        final Campeonato campeonato = campeonatoService
                .selecionarCampeonato(permalink);

        this.carregarFiltroRodadas(campeonato);

        final int idRodada = firstNonNull(idRodadaParam, 0);
        final long idLiga = firstNonNull(idLigaParam, 0L);

        this.addObject("liga", ligaService.recuperarLiga(idLiga).orNull());
        this.addObject("rodada", rodadaService.selecionarRodada(idRodada)
                .orNull());
        this.addObject("campeonato", campeonato);
        this.addObject("ranking",
                this.listarRanking(campeonato, idLiga, idRodada));

        return getModelAndView();
    }

    private List<? extends AbstractApostadorPontuacao> listarRanking(
            final Campeonato campeonato, final long idLiga, final int idRodada) {

        if (idLiga > 0 && idRodada > 0) {
            return apostadorPontuacaoService.listarRankingPorRodadaELiga(
                    idRodada, idLiga);
        }

        if (idLiga > 0) {
            return apostadorPontuacaoService.listarRankingPorLiga(
                    campeonato.getId(), idLiga);
        }

        if (idRodada > 0) {
            return apostadorPontuacaoService.listarRankingPorRodada(idRodada);
        }

        return apostadorPontuacaoService.listarRanking(campeonato);
    }

    private void carregarFiltroLigas() {
        final int usuarioId = this.getUsuarioIdAutenticado();

        if (usuarioId > 0) {
            this.addObject("ligas", ligaService.consultarLiga(usuarioId));
        }
    }

    private void carregarFiltroRodadas(Campeonato campeonato) {
        this.addObject("rodadas",
                rodadaService.selecionarRodadasCalculadas(campeonato));
    }

}
