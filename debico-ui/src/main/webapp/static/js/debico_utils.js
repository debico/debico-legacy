/**
 * Utilidades úteis.
 *  ;)
 * 
 * @since 2.0.3
 */
var DebicoUtils = DebicoUtils || {};

/**
 * Utilidades para a View.
 */
DebicoUtils.View = (function() {
	var _notifyUI = function(args) {
		var args = args || {};
		if (args.err) {
			// TODO: tratar err (parse e etc)
			$.notify(args.errMsg + ": " + args.err, "error");
		} else {
			$.notify(args.sucessMsg, "success");
		}
	}

	var _blockUI = function(el) {
		$(el).block();
	}

	var _unblockUI = function(el) {
		$(el).unblock();
	}
});