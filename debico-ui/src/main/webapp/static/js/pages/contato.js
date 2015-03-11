/**
 * Scripts especificos do template/pagina /modal/contato.html
 */
$(document).ready(function() {
	$('#contato_form').validate({
		rules : {
			remetente : {
				required : true,
				email : true
			},
			assunto : {
				minlength : 2,
				required : true
			},
			mensagem : {
				minlength : 10,
				required : true
			}
		},
		submitHandler : function(form) {
			$('#contato').block({
				message : $('#throbber')
			});

			form.submit();
		}
	});
});