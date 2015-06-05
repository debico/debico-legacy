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
		console.log("enviando o ajax");
		var self = this;
		self.deferred = Q.defer();
		
		// TODO throw
		self.args = args || {};

		$.ajax(args.url, {
			type : args.method,
			data : ko.toJSON(args.data),
			contentType : "application/json",
			dataType : 'json'
		}).done(function(response) {
			console.log("ajax recebico OK");
			self.args.response = response;
			self.deferred.resolve(self.args);
		}).fail(function(err) {
			console.log("falhou no ajax!");
			self.args.err = err;
			self.deferred.reject(self.args);
		});

		return self.deferred.promise;
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

	var atualizarDataHorario = function(args) {
		var args = args || {};
		var mData = moment(args.dataPartida, "DD/MM/YYYY HH:mm");
		if (!mData.isValid()) {
			throw "Data " + args.dataPartida + " invalida.";
		}

		args.url = _urlAPI + "/" + args.partidaId;
		args.method = 'PATCH';
		args.data = mData.toISOString();
		args.sucessMsg = 'Data atualizada com sucesso!';
		args.errMsg = 'Erro ao tentar atualizar a data.';

		// a promise
		return _sendAjax(args);
	}

	return {
		atualizarDataHorario : atualizarDataHorario,
		salvarPlacarPartida : salvarPlacarPartida
	};
})();
