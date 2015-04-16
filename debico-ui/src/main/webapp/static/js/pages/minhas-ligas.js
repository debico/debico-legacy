/**
 * Scripts especificos do template/pagina /minhas-ligas.html
 */
$(document).ready(function() {
	$("#buscaCamarada").buscaapostador({
		hiddenElem : $("#idApostadorCamarada"),
		searchElem : $("#buscaCamarada"),
		btnAction : $("#btnAddCamarada"),
		appendTo : "#add_camaradas_form"
	});

	$("#editar_liga_form").validate({
		rules : {
			nome : {
				required : true,
				minlength : 4,
				maxlength : 500
			}
		}
	});

	var liga = new LigaViewModel();
	ko.applyBindings(liga, document.getElementById("editar_liga"));

	var ligaApostador = new LigaApostadorWidget();

	window.liga = liga;
	window.ligaApostador = ligaApostador;

	$("#add_camaradas_form").submit(ligaApostador.adicionarCamarada);
});