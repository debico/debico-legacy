function LigaViewModel() {
	var self = this;

	self.baseUrl = URLS.widgets.liga;

	self.id = ko.observable(0);
	self.nome = ko.observable("");

	self.reloadWindow = function() {
		$('#editar_liga_form').unblock();
		location.reload(true);
	}

	self.salvar = function() {
		if ($("#editar_liga_form").valid()) {
			$('#editar_liga_form').block();
			if (parseInt(self.id()) > 0) {
				self.atualizar(self.id(), self.nome());
			} else {
				self.cadastrar(self.nome());
			}
		}
	}

	self.cadastrar = function(nomeLiga) {
		$.ajax(self.baseUrl, {
			type : 'POST',
			data : JSON.stringify(nomeLiga),
			contentType : "application/json",
			dataType : 'json',
		}).done(function(data) {
			$.notify("Liga " + data.nome + " criada com sucesso!", "success");

			window.setTimeout(self.reloadWindow, 2000);
		}).fail(function() {
			$('#editar_liga_form').unblock();
		});
	}

	self.atualizar = function(idLiga, nomeLiga) {
		$.ajax(self.baseUrl + "/" + idLiga, {
			type : 'PATCH',
			data : JSON.stringify(nomeLiga),
			contentType : "application/json",
			dataType : 'json'
		}).done(
				function(data) {
					$.notify("Liga " + data.nome + " atualizada com sucesso!",
							"success");
					window.setTimeout(self.reloadWindow, 2000);
				}).fail(function() {
			$('#editar_liga_form').unblock();
		});
	}

	self.recuperar = function(idLiga) {
		$.getJSON(self.baseUrl + "/" + idLiga, function(data) {
			self.id(data.id);
			self.nome(data.nome);
			$('#editar_liga').foundation('reveal', 'open');
		});
	}

}