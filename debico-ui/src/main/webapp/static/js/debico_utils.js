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