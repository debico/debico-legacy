/**
 * Scripts especificos do template/pagina /perfil.html
 */
$(document).ready(function() {
	$("#buscaTime").buscatime({
		hiddenElem : $("#timeCoracao")
	});
	
	$("#perfil_form").validate({
		rules : {
			nome : {
				required : true,
				minlength : 4
			},
			apelido : {
				minlength : 2
			}
		}
	});
});
