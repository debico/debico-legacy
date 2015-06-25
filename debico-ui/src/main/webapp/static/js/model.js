/**
 * Util para serializar os objetos para JSON.
 */
function SerializeJSON() {	
	var self = this;
	
	this.placar = function(model) {
		return ko.toJSON(model, function(key, value){
			//ref: http://stackoverflow.com/questions/12461037/knockout-serialization-with-ko-tojson-how-to-ignore-properties-that-are-null
			if(key === "idPartida" || key === "isValido" || key === "existe") {
				return;
			}
			
			return value;
		});
	};
	
	this.palpite = function(model) {
		return ko.toJSON(model, function(key, value){
			//o placar serializa
			if(key === "placar") {
				return JSON.parse(self.placar(value));
			}
			
			if(key === "pontos") {
				return;
			}
			
			return value;
		});
	};
	
	this.partida = function(model) {
		// o processo eh o mesmo, ambos possuem 'placar'
		return self.palpite(model);
	};
	
}

/**
 * Domínio para um placar.
 */
function Placar(json, idPartida) {
	var self = this;
	
	self.idPartida = idPartida;
	self.id = "";

	if (json) {
		self.id = json.id;
		
		//eh observable ?
		if(typeof json.golsMandante == "function") {
			self.golsMandante = json.golsMandante;
			self.golsVisitante = json.golsVisitante;
		} else {
			self.golsMandante = ko.observable(json.golsMandante);
			self.golsVisitante = ko.observable(json.golsVisitante);
		}
	} else {
		self.golsMandante = ko.observable("");
		self.golsVisitante = ko.observable("");
	}
	
	self.zerarPlacar = function() {
		self.golsMandante("");
		self.golsVisitante("");
	};	
	
	self.formatar = function() {
		return self.golsMandante() + " x " + self.golsVisitante();
	}
	
	self.isValido = function() {
		if(typeof self.golsMandante == "function") {
			if (typeof self.golsVisitante() !== "undefined" && typeof self.golsMandante() !== "undefined") {
				return (self.golsVisitante() !== "" && self.golsMandante() !== "");
			} else {
				return false;
			}
		} else {
			return (self.golsVisitante !== "" && self.golsMandante !== "");
		}
	};
}

/**
 * Domínio para Time
 */
function Time(json) {
	var self = this;

	self.nome = json.nome;
	self.alias = json.alias;
	self.brasao = json.brasaoFigura;

	self.brasaoFigura = ko.computed(function() {
		return URLS.images.brasao + "/" + self.brasao;
	});
}

function Palpite(json) {
	var self = this;
	
	self.id = (json) ? json.id : "";
	self.idPartida = (json) ? json.idPartida : "";
	self.placar = (json) ? (new Placar(json.placar, json.idPartida)) : (new Placar());
	self.pontos = (json) ? json.pontos : 0;
}

/**
 * Domínio de partida.
 */
function Partida(json) {
	var self = this;
	
	// ref. http://momentjs.com/docs/#/displaying/
	self.dataHoraPartida = ko.observable(moment(json.dataHoraPartida).format("DD/MM/YYYY HH:mm"));
	self.local = json.local;
	self.mandante = new Time(json.mandante);
	self.visitante = new Time(json.visitante);
	self.computadaCampeonato = json.computadaCampeonato;
	self.status = json.status;
	self.id = json.id;
	
	self.editing = ko.observable(false);
	
	self.edit = function() {
		self.editing(true);
	}

	if (typeof json.placar !== "undefined" && json.placar !== null) {
		self.placar = new Placar(json.placar, json.id);
	} else {
		self.placar = new Placar("", json.id);
	}

	self.hasPlacar = this.placar.isValido();
	self.hasPlacarProcessado = ko.pureComputed(function() { return this.hasPlacar && this.computadaCampeonato }, this);
}

function PartidaPalpite(json) {
	Partida.call(this, json); // subclass
	
	this.palpite = (json.palpite !== null) ? (new Palpite(json.palpite)) : (new Palpite());
}

// ref:
// http://stackoverflow.com/questions/8460493/subclassing-a-class-with-required-parameters-in-javascript
PartidaPalpite.prototype = Object.create(Partida.prototype);
PartidaPalpite.prototype.constructor = PartidaPalpite;