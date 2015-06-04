// include: partidas_rodada_list_view_model.js

function PartidasRodadaEditViewModel() {
	PartidasRodadaListViewModel.call(this);

	var self = this;

	/**
	 * Funciona apenas para usuários administradores.
	 */
	self.salvarPlacarPartida = function(placar) {
		if (placar.isValido()) {
			var url = URLS.api.partida + "/" + placar.idPartida + "/placar/";

			$.ajax(url, {
				type : 'PUT',
				data : new SerializeJSON().placar(placar),
				contentType : "application/json",
				dataType : 'json',
				success : function() {
					$.notify("Placar salvo com sucesso!", "success");
				}
			});
		}
	};
}

// Estencao
PartidasRodadaEditViewModel.prototype = Object
		.create(PartidasRodadaListViewModel.prototype);
PartidasRodadaEditViewModel.prototype.constructor = PartidasRodadaEditViewModel;

/**
 * Carrega as partidas do servidor a partir de uma URL. <p/> Para entender o
 * "subscribe" das entidades abaixo, ver o capítulo <b>Explicitly subscribing to
 * observables</b> no link abaixo.
 * 
 * @see http://api.jquery.com/jquery.getjson/
 * @see http://knockoutjs.com/documentation/observables.html
 */
PartidasRodadaEditViewModel.prototype.carregarPartidas = function(url) {
	var self = this;

	return $.getJSON(url, function(data) {
		var mapped = $.map(data, function(partida) {
			var p = new Partida(partida);

			p.placar.golsMandante.subscribe(function(newValue) {
				self.salvarPlacarPartida(p.placar);
			}, p.placar);

			p.placar.golsVisitante.subscribe(function(newValue) {
				self.salvarPlacarPartida(p.placar);
			}, p.placar);

			p.dataHoraPartida.subscribe(function(newValue) {
				// DebicoAdmin.Partida.atualizarDataHorario(p.id,
				// p.dataHoraPartida());
				console.log("a data mudou");
			}, p.dataHoraPartida);

			return p;
		});

		self.partidas(mapped);
		$(".data-input").datetimepicker();
	});
};