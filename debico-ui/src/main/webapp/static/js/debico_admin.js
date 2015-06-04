/**
 * API Debico JS para tratar das funcionalidades da API Administrativa na UI
 * otimizando via uso de jQuery para Ajax e Q para as interações. A tendência é
 * iniciar a depreciação dos outros scripts. <p/> ref.:
 * http://toddmotto.com/mastering-the-module-pattern/
 * 
 * @since 2.0.3
 */
var DebicoAdmin = DebicoAdmin || {};

/**
 * Módulo para tratar dos Use Cases de Partidas.
 */
DebicoAdmin.Partida = (function() {

	// TODO: check undefined
	var _urlAPI = URLS.api.partida;

	var _sendAjax = function(args) {
		var self = this;
		var deferred = Q.defer();
		// TODO throw
		self.args = args || {};

		$.ajax(args.url, {
			type : args.method,
			data : JSON.stringfy(args.data),
			contentType : "application/json",
			dataType : 'json'
		}).done(function(response) {
			self.args.response = response;
			deferred.resolve(self.args);
		}).fail(function(err) {
			self.args.err = err;
			deferred.reject(self.args);
		});

		return deferred.promise;
	}

	var salvarPlacarPartida = function(placar) {
		var placar = placar || {
			isValido : function() {
				return false;
			}
		};

		if (placar.isValido()) {
			var args = {
				url : _urlAPI + "/" + placar.idPartida + "/placar/",
				method : 'PUT',
				// TODO: arrumar esse horror
				data : new SerializeJSON().placar(placar),
				sucessMsg : 'Placar salvo com sucesso!',
				errMsg : 'Erro ao tentar salvar o placar.'
			}
			// a promise
			return _sendAjax(args);
		}
	}

	var atualizarDataHorario = function(idPartida, dataHora) {
		var args = {
			url : _urlAPI + "/" + idPartida,
			method : 'PATCH',
			data : dataHora,
			sucessMsg : 'Data atualizada com sucesso!',
			errMsg : 'Erro ao tentar atualizar a data.'
		};

		// a promise
		return _sendAjax(args);
	}

	return {
		atualizarDataHorario : atualizarDataHorario,
		salvarPlacarPartida : salvarPlacarPartida
	};
})();
