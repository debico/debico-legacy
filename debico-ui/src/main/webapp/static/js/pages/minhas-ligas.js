/**
 * Scripts especificos do template/pagina /minhas-ligas.html
 */
$(document).ready(function() {
	$("#editar_liga_form").validate({
		rules : {
			nome : {
				required : true,
				minlength : 4
			}
		}
	});

	var liga = new LigaViewModel();
	ko.applyBindings(liga, document.getElementById("editar_liga"));

	window.liga = liga;
});