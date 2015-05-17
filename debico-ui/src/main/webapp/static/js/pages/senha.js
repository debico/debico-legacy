/**
 * Scripts especificos do template/pagina senha/html
 */
$(document).ready(function() {
	$('#senha_form').validate({
		rules : {
			"novaSenha" : {
				required : true,
				passwordRuleCheck : true
			},
			confirmacaoSenha : {
				required : true,
				equalTo : "#novaSenha"
			}

		}
	});
});