// adicionar junto ao modal adicionar_camaradas
function LigaApostador(idLiga, idApostador) {
	this.idLiga = idLiga || 0;
	this.idApostador = idApostador || 0;
}

function LigaApostadorWidget() {
	var self = this;

	self.baseUrl = URLS.widgets.liga_apostador;
	self.idLiga = 0;
	self.buscaElem = $("#buscaCamarada");
	self.idApostadorElem = $("#idApostadorCamarada");

	self.reset = function() {
		$("#btnAddCamarada").attr('disabled', true);
		self.buscaElem.val("");
		self.idApostadorElem.val(0);
	}

	self.carregarBuscador = function(idLiga) {
		self.idLiga = idLiga;
		self.reset();
		$('#add_camaradas').foundation('reveal', 'open');
	}

	self.adicionarCamarada = function() {
		if (parseInt(self.idApostadorElem.val()) <= 0) {
			return false;
		}

		$('#add_camaradas_form').block();

		var idApostador = parseInt(self.idApostadorElem.val());

		$.ajax(self.baseUrl, {
			type : 'POST',
			data : JSON.stringify(new LigaApostador(self.idLiga, idApostador)),
			contentType : "application/json",
			dataType : 'json'
		}).done(
				function(data) {
					$.notify("Camarada " + self.buscaElem.val()
							+ " adicionado com sucesso!", "success");
				}).always(function(data) {
			self.reset();
			$('#add_camaradas_form').unblock();
		});

		return false;
	}
}