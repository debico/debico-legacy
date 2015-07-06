/**
 * Utilidades úteis. ;)
 * 
 * @since 2.0.3
 */
var DebicoUtils = DebicoUtils || {};

/**
 * Utilidades para a View.
 */
DebicoUtils.View = (function() {
	var notify = function(args) {
		var args = args || {};
		if (args.err) {
			// TODO: tratar err (parse e etc)
			$.notify(args.errMsg + ": " + args.err, "error");
		} else {
			$.notify(args.sucessMsg, "success");
		}
	}

	var block = function(args) {
		$(el).block();
	}

	var unblock = function(args) {
		$(el).unblock();
	}

	return {
		notify : notify,
		block : block,
		unblock : unblock
	};
})();

DebicoUtils.Ajax = (function() {
	/**
	 * Retorna uma promessa de envio de Ajax. Refatorar as funções para cá.
	 * 
	 * @param args { data, method, url }
	 * @author ricardozanini
	 */
	var sendAjax = function(args) {
		var self = this;
		self.deferred = Q.defer();

		// TODO throw
		self.args = args || {};

		$.ajax(args.url, {
			type : args.method,
			data : (args.method === 'GET') ? null : ko.toJSON(args.data),
			contentType : "application/json",
			dataType : 'json'
		}).done(function(response) {
			self.args.response = response;
			self.deferred.resolve(self.args);
		}).fail(function(err) {
			self.args.err = err;
			self.deferred.reject(self.args);
		});

		return self.deferred.promise;
	}

	return {
		sendAjax : sendAjax
	}
})();