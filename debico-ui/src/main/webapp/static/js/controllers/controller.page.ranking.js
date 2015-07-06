/**
 * Controller da página do Ranking dos Apostadores. Já refatorado para utilizar
 * a nova API do app.
 * 
 * @since 2.0.4
 */
$(document).ready(function() {
	$("#alert-semdados").hide();
	$("#chart_container").hide();
	$("#alert-loading").show();

	Debico.Ranking.carregarGraficoDesempenhoApostador({
		chartElement : document.getElementById('chart'),
		yElement : document.getElementById('y_axis'),
		xElement : document.getElementById('x_axis')
	}).then(function(args) {
		$("#chart_container").show();
	}).fail(function(error) {
		$("#alert-semdados").show();
	}).fin(function() {
		$("#alert-loading").hide();
	});

	$("#btn_filtro").on('click', function() {
		Debico.Ranking.aplicarFiltro($("#filtro_lista").val());
	});

});

// referencia para mais tarde:
// -----------------------------
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
