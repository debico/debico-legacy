/**
 * Scripts especificos do template/pagina /modal/esqueci_senha.html
 */
$(document).ready(function() {
	$('#esqueci_senha_form').validate({
		rules : {
			email_usuario : {
				required : true,
				email : true
			}
		},
		submitHandler : function(form) {
			$('#esqueci_senha').block({
				message : $('#throbber')
			});

			form.submit();
		}
	});
});