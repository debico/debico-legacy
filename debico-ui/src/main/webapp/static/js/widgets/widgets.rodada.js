// include: 

// deve ser incluido em templates/widgets/rodada.html

$(document).ready(function() {
	// se precisar mudar para um especifico...
	// ref: http://stackoverflow.com/questions/1191485/how-to-call-ajaxstart-on-specific-ajax-calls
	$(document).ajaxStart(function () {
		$('#rodada_table').block({
			// esse div é inserido em todas as views via footer.
			message : $('#throbber')
		});
	});
	
	$(document).ajaxStop(function() {
		$('#rodada_table').unblock();
	});
	
	var partidasListViewModel = new PartidasRodadaEditViewModel();
	partidasListViewModel.carregarPartidasRodada(RODADA_INICIAL);
	ko.applyBindings(partidasListViewModel, document.getElementById("rodada_table"));
	
	var rodadasNavViewModel = new RodadaNavViewModel();
	
	$('#rodada_table').on("click", "#rodadaBtn", function(){
		rodadasNavViewModel.carregarRodadas(URLS.widgets.rodadas);
	});
	
	$('#rodadas-nav').on("click", ".rodada-nav-item", function(){
		partidasListViewModel.carregarPartidasRodada(ko.dataFor(this));
	});
	
	ko.applyBindings(rodadasNavViewModel, document.getElementById("rodadas-nav"));
});