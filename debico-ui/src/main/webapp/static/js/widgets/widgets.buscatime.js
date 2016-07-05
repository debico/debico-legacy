/*!
 * Widget para a busca de times por nome. <p/> Quando o usuário entrar com parte
 * do nome, uma lista é apresentada com os times cadastrados no banco de dados.
 * O usuário então pode escolher o time desejado.
 * <p />
 * Após a escolha do time, o ID do objeto é carregado em um campo HIDDEN.
 * <p/>
 * Dependências:
 * 
 * 1) Variável Global URLS definida no contexto.
 * 2) Biblioteca autocomplete do jQuery UI.
 * 
 * @since 1.2.0
 */
$.widget("debico.buscatime", $.ui.autocomplete, {
	options : {
		hiddenElem : null,
		searchElem : null,
		minLength : 3,
		// ref.: http://api.jqueryui.com/autocomplete/#option-source
		source : function(request, response) {
			var url = URLS.widgets.buscatime + "?nome=" + request.term;
			$.getJSON(url, request, function(data) {
				response(data);
			});
		},
	},
	
	_create : function() {
		var self = this;
		
		this.options.searchElem = this.options.searchElem || this.element;
		
		// ref.: http://jqueryui.com/autocomplete/#custom-data
		this.element.on('buscatimeselect', function(event, ui) {
			self.options.searchElem.val(ui.item.nome);
			self.options.hiddenElem.val(ui.item.id);
			
			return false;
		});
		
		this.element.on('buscatimechange', function(event, ui) {
			if(ui.item == null) {
				self.options.hiddenElem.val(0);
			}
		});
		
		this._super();
	},
	
	_renderItem : function(ul, item) {
		// ul.addClass("f-dropdown");
		// ul.attr("data-dropdown-content");
		
		return $("<li>")
        	.append("<img style='padding:0.25em;' class='brasao' src='" + URLS.images.brasao + "/" + item.brasaoFigura +"' />")
        	.append("<span style='padding:0.25em;'>" + item.nome + "</span>")
        	.appendTo(ul);
	},
});
