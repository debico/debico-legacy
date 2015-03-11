// include: partidas_rodada_list_view_model.js

function PartidasPalpiteListViewModel() {
	PartidasRodadaListViewModel.call(this, URLS.widgets.palpite);

	var self = this;
	
	self.pontosTotal = ko.pureComputed(function() {
        var total = 0;
        $.each(self.partidas(), function() { total += this.palpite.pontos })
        return total;
    });
	
	self.getNomeJogo = function(partidaPalpite) {
		if(partidaPalpite.mandante && partidaPalpite.visitante) {
			return partidaPalpite.mandante.alias + " X " +  partidaPalpite.visitante.alias;
		} else {
			return "";
		}
	}

	self.palpitar = function(partidaPalpite) {
		var palpite = new Palpite(partidaPalpite.palpite);
		
		palpite.idPartida = partidaPalpite.id;

		if (!palpite.placar.isValido()) {
			return;
		}

		$.ajax(self.baseUrl, {
			type : 'POST',
			data : new SerializeJSON().palpite(palpite),
			contentType : "application/json",
			dataType : 'json',
			beforeSend : function() {
			}
		}).done(function(data) {
			if(isNaN(partidaPalpite.palpite.id) || isNaN(parseInt(partidaPalpite.palpite.id))) {
				$.notify("Palpite " + self.getNomeJogo(partidaPalpite) + " realizado com sucesso!", "success");
			} else {
				$.notify("Palpite " + self.getNomeJogo(partidaPalpite) + " atualizado com sucesso!", "success");
			}
			
			partidaPalpite.palpite.id = data.id;
			partidaPalpite.palpite.placar.id = data.placar.id;
			
		}).always(function() {
		}).fail(function() {
			// ocorreu algum erro durante a atualizacao do placar
			if(partidaPalpite.palpite.id === "") {
				partidaPalpite.palpite.placar.zerarPlacar();
			}
		});

	};
}

PartidasPalpiteListViewModel.prototype = Object
		.create(PartidasRodadaListViewModel.prototype);
PartidasPalpiteListViewModel.prototype.constructor = PartidasPalpiteListViewModel;

PartidasPalpiteListViewModel.prototype.carregarPartidas = function(url) {
	var self = this;

	$("#palpites").hide();
	$("#alert-loading").show();
	
	return $.getJSON(url, function(data) {
		var mapped = $.map(data, function(partida) {
			var p = new PartidaPalpite(partida);

			p.palpite.placar.golsMandante.subscribe(function(newValue) {
				self.palpitar(p);
			}, p);

			p.palpite.placar.golsVisitante.subscribe(function(newValue) {
				self.palpitar(p);
			}, p);
			
			return p;
		})

		self.partidas(mapped);
	}).always(function() {
		$("#alert-loading").hide();
		$("#palpites").show();
	});
};