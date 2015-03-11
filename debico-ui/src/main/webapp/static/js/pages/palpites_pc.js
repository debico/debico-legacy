// include: partidas_palpite_list_view_model.js

$(document).ready(function() {
	var partidasListViewModel = new PartidasPalpiteListViewModel();
	partidasListViewModel.carregarPartidasRodada(RODADA_INICIAL);
	ko.applyBindings(partidasListViewModel, document.getElementById("palpites"));
	
	$("#partidasPalpite").on("keydown", ".placar-input", function(e) {	
		// Allow: backspace, delete, tab, escape, enter and
		if ($.inArray(e.keyCode, [8, 9, 27, 13, 110]) !== -1 ||
             // Allow: Ctrl+A
            (e.keyCode == 65 && e.ctrlKey === true) || 
             // Allow: home, end, left, right
            (e.keyCode >= 35 && e.keyCode <= 39)) {
                 // let it happen, don't do anything
                 return;
        }
		
        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
	});
});