var Debico = Debico || {};

// Widgets
Debico.Widgets = {};

Debico.Widgets.Partidas = (function() {
	var _urlWidget = URLS.widgets.partidas;

	var carregarPartidasRodadaDirecao = function(args) {

	}

	return {

	};
})();

Debico.Ranking = (function() {

	/**
	 * Monta o objeto do grafico e o adiciona aos elementos enviados.
	 * 
	 * @param args
	 *            {chartElement, yElement, xElement, chartData}
	 */
	var _renderChartDesempenhoApostador = function(args) {
		if (!args.response || args.response.data.length <= 0) {
			throw "Não há dados para carregar o gráfico";
		}

		var graph = new Rickshaw.Graph({
			element : args.chartElement,
			width : 800,
			height : 300,
			renderer : 'area',
			stroke : true,
			series : [ {
				data : args.response.data,
				name : args.response.nomeApostador,
				color : '#008CBA'
			} ]
		});

		var y_ticks = new Rickshaw.Graph.Axis.Y({
			graph : graph,
			orientation : 'left',
			tickFormat : Rickshaw.Fixtures.Number.formatKMBT,
			element : args.yElement
		});

		var x_ticks = new Rickshaw.Graph.Axis.X({
			graph : graph,
			orientation : 'bottom',
			element : args.xElement
		});

		graph.render();

		var hoverDetail = new Rickshaw.Graph.HoverDetail({
			graph : graph,
			xFormatter : function(x) {
				return x + "&#170; rodada"
			},
			yFormatter : function(y) {
				return parseInt(y) + " pontos"
			}
		});
	}

	/**
	 * Carrega o grafico do desempenho do apostador.
	 */
	var carregarGraficoDesempenhoApostador = function(args) {
		args = args || {
			chartElement : document.getElementById("chart"),
			yElement : document.getElementById('y_axis'),
			xElement : document.getElementById('x_axis')
		}

		args.method = 'GET';
		// carrega o desempenho do apostador logado, por isso nao precisa de
		// params ^.^
		args.url = URLS.widgets.desempenho;
		args.data = '';

		// a promise
		return DebicoUtils.Ajax.sendAjax(args).then(
				_renderChartDesempenhoApostador);
	}

	/**
	 * Aplica o filtro pela rodada, liga ou ambas. O que vier com o
	 * identificador do objeto maior do que zero.
	 * 
	 * @see <a href="http://medialize.github.io/URI.js/docs.html">URI.js</a>
	 */
	var aplicarFiltro = function(liga, rodada) {
		var l = parseInt(liga) || 0;
		var r = parseInt(rodada) || 0;
		var uri = new URI(window.location);

		uri.setSearch({
			"liga" : liga,
			"rodada" : rodada
		});
		
		if (liga <= 0) uri.removeSearch("liga");
		if (rodada <= 0) uri.removeSearch("rodada");

		window.location.replace(uri.toString());
	}
	
	var limparFiltro = function() {
		var uri = new URI(window.location);
		uri.removeSearch(["liga", "rodada"]);
		
		window.location.replace(uri.toString());
	}

	return {
		carregarGraficoDesempenhoApostador : carregarGraficoDesempenhoApostador,
		aplicarFiltro : aplicarFiltro,
		limparFiltro : limparFiltro
	}
})();