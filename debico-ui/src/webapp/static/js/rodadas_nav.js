function RodadaNavViewModel() {
	var self = this;

	self.rodadas = ko.observableArray([]);

	self.carregarRodadas = function(urlWidget) {
		//ja carregada
		if(self.rodadas().length > 0) {
			return;
		}
		
		$.getJSON(urlWidget, function(data) {
			self.rodadas(data);
		});
	};

};