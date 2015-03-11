/**
 * Scripts especificos do template/pagina cadastro.html
 */
$(document).ready(function() {
	$('#cadastro_form').validate({
		rules : {
			nome : {
				required : true,
				minlength : 4
			},
			"usuario.email" : {
				required : true,
				email : true
			},
			"usuario.senha" : {
				required : true,
				passwordRuleCheck : true
			},
			confirmar_password : {
				required : true,
				equalTo : "#usuario\\.senha"
			}

		}
	});
});