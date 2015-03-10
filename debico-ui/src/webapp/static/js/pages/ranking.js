function DesempenhoApostador(aposCarregarGrafico) {
	var self = this;
	
	self.onAposCarregarGrafico = aposCarregarGrafico || function() {};
	
	self.carregarGrafico = function() {
		$.getJSON(URLS.widgets.desempenho, function(responseBody) {
			if(!responseBody || !responseBody.data || responseBody.data.length <= 0) {
				self.onAposCarregarGrafico.call(self, null, false);
				return;
			}
			
			var graph = new Rickshaw.Graph({
				element : document.getElementById("chart"),
				width : 800,
				height : 300,
				renderer : 'area',
				stroke : true,
				series : [ {
					data : responseBody.data,
					name : responseBody.nomeApostador,
					color : '#008CBA'
				} ]
			});

			var y_ticks = new Rickshaw.Graph.Axis.Y({
				graph : graph,
				orientation : 'left',
				tickFormat : Rickshaw.Fixtures.Number.formatKMBT,
				element : document.getElementById('y_axis')
			});

			var x_ticks = new Rickshaw.Graph.Axis.X({
				graph : graph,
				orientation : 'bottom',
				element : document.getElementById('x_axis')
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
			
			self.onAposCarregarGrafico.call(self, graph, true);
		}).fail(function(){
			$.notify("Houvem um problema ao carregar os seus dados de desempenho.", "error");
			self.onAposCarregarGrafico.call(self, null, false);
		});
	};
}

$(document).ready(function() {
	$("#alert-semdados").hide();
	$("#chart_container").hide();
	$("#alert-loading").show();
	
	new DesempenhoApostador(function(graph, carregou) {
		if(!carregou) {
			$("#alert-semdados").show();
		}
		
		$("#chart_container").show();
		$("#alert-loading").hide();
	}).carregarGrafico();

});

// referencia para mais tarde:
//-----------------------------
// var palette = new Rickshaw.Color.Palette('cool');

// var graphSeries = $.map(data, function(item) {
// var serie = {
// data : item.data,
// name : item.nomeApostador,
// color : palette.color()
// };
//
// return serie;
// });