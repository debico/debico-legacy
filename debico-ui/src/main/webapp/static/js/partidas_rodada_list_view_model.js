// include: model.js

/*
 * Definir no contexto da pagina:
 * 
 * URL_BASE.campeonato
 * RODADA_ORDEM_INICIAL
 * 
 */
var NOME_RODADA_PADRAO = "Rodada ";

function PartidasRodadaListViewModel(baseUrl) {
	var self = this;
	self.init();
	
	self.baseUrl = baseUrl || URLS.widgets.partidas;
	
	// utilizado para atualizar a navegacao apos o request ajax.
	self.tempRodadaOrdinal = RODADA_ORDEM_INICIAL;
	
	self.partidas = ko.observableArray([]);
	self.rodada = {
			id: RODADA_INICIAL,
			ordem:  RODADA_ORDEM_INICIAL,
			nome: ko.observable(NOME_RODADA_PADRAO + RODADA_ORDEM_INICIAL)
	};
	//so assim pra funcionar o bind. ele nao acessa propriedade de outro objeto dentro do template.
	self.rodadaNome = self.rodada.nome;
}

//init
PartidasRodadaListViewModel.prototype.init = function() {
	if(typeof URLS === "undefined") {
		throw "[PartidasRodadaListViewModel] Constante URLS nao definida.";
	}
	
	if(typeof RODADA_ORDEM_INICIAL === "undefined") {
		throw "[PartidasRodadaListViewModel] Constante RODADA_ORDEM_INICIAL nao definida.";
	}
};

/**
 * Deve ser implementada de forma que carregue as partidas de acordo com o contexto. 
 * 
 * @param urlPartidas
 */
PartidasRodadaListViewModel.prototype.carregarPartidas = function(url){
	throw "[PartidasRodadaListViewModel] carregarPartidas nao implementado.";
};

/**
 * Carrega as partidas a partir do identificador
 */
PartidasRodadaListViewModel.prototype.carregarPartidasRodada = function(rodada) {
	var self = this;
	
	if(typeof rodada === "object") {
		self.rodada = rodada;
	} else {
		self.rodada.id = rodada;
	}
	
	var urlPartidas = self.baseUrl  + "/rodada/" + self.rodada.id;

	this.carregarPartidas(urlPartidas).done(function() {
		if(rodada.nome) {
			self.rodadaNome(rodada.nome);	
		}
	});
};

/**
 * Carrega as partidas a partir do ordinal na memória do ViewModel.
 */
PartidasRodadaListViewModel.prototype.carregarPartidasRodadaDirecao = function(direcao) {
	var self = this;
	
	this.tempRodadaOrdinal = self.rodada.ordem;
	this.tempRodadaOrdinal += direcao;

	if (parseInt(this.tempRodadaOrdinal) <= 0) {
		return false;
	}

	var urlPartidas = self.baseUrl + "/rodada/seq/" + this.tempRodadaOrdinal;

	this.carregarPartidas(urlPartidas).done(function() {
		self.rodada.ordem = self.tempRodadaOrdinal;
		self.rodadaNome(NOME_RODADA_PADRAO + self.rodada.ordem);
	});
};

PartidasRodadaListViewModel.prototype.carregarProximaRodada = function() {
	//TODO: limitar o número de rodadas.
	this.carregarPartidasRodadaDirecao(1);
};

PartidasRodadaListViewModel.prototype.carregarRodadaAnterior = function() {
	var self = this;
	
	if (self.rodada.ordem - 1 <= 0) {
		return false;
	}

	this.carregarPartidasRodadaDirecao(-1);
};
