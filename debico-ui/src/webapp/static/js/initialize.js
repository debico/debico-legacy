$(document).foundation();

function sendException(error, fatal) {
	error = error || '';
	fatal = fatal || false;
	
	if(window.ga) {
		ga('send', 'exception', {
			  'exDescription': error,
			  'exFatal': fatal,
			  'appName': APP.name,
			  'appVersion' : APP.version
			});
	} else {
		console.error(error);
	}
}

//global init
//escape: http://www.the-art-of-web.com/javascript/escape/
$(document).ready(function() {
	
	//ref: http://api.jquery.com/jQuery.ajaxSetup/
	//ref: http://docs.spring.io/spring-security/site/docs/3.2.4.RELEASE/reference/htmlsingle/#csrf
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	
	if(typeof token !== "undefined" 
		&& typeof header !== "undefined") {
		$(document).ajaxSend(function (event, xhr, settings) {
			xhr.setRequestHeader(header, token);
		});
	}
	
	// http://api.jquery.com/ajaxError/
	$(document).ajaxError(function(event, request, settings, thrownError){
		var error = null;
		
		if(request.status == 500) {
			try {
				error = JSON.parse(request.responseText);
			} catch (e) {
				// TODO melhorar essa tratativa tanto aqui (com uma funcao mais inteligente de parse), quanto no servidor a saber que estou fazendo uma query Ajax.
				// erro generico do apache
				var msg = $.parseHTML(request.responseText, null);
				error = {ex: "N\u00e3o foi poss\u00edvel processar a requisi\u00e7\u00e3o: " + msg[3].innerText}
			}
			
			sendException(error.ex, true);
		} else if(request.status == 403){
			error = {ex : "Opa! Sua sess\u00e3o expirou, por favor fa\u00e7a o login novamente."};
		} else {
			error = {ex : "Erro desconhecido."};
			sendException("Erro desconhecido.", true);
		}
		
		$.notify(error.ex, "error");
		
	});
	
	$(".carousel").owlCarousel({
		navigation : false,
		slideSpeed : 300,
		paginationSpeed : 400,
		singleItem : true
	});
});
