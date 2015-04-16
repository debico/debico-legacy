/*!
 * Widget para a busca de apostadores cadastrados por nome. <p/> Quando o usuário entrar com parte
 * do nome, uma lista é apresentada com os apostadores cadastrados no banco de dados.
 * O usuário então pode escolher o time desejado.
 * <p />
 * Após a escolha do time, o ID do objeto é carregado em um campo HIDDEN.
 * <p/>
 * Dependências:
 * 
 * 1) Variável Global URLS definida no contexto.
 * 2) Biblioteca autocomplete do jQuery UI.
 * 
 * @since 2.0.0
 */
$.widget("debico.buscaapostador", $.ui.autocomplete, {
	options : {
		hiddenElem : null,
		searchElem : null,
		btnAction : null,
		minLength : 3,
		// ref.: http://api.jqueryui.com/autocomplete/#option-source
		source : function(request, response) {
			var url = URLS.widgets.apostadores + "?nome=" + request.term;
			$.getJSON(url, request, function(data) {
				response(data);
			});
		},
	},

	_create : function() {
		var self = this;

		this.options.searchElem = this.options.searchElem || this.element;

		// ref.: http://jqueryui.com/autocomplete/#custom-data
		this.element.on('buscaapostadorselect', function(event, ui) {
			self.options.searchElem.val(ui.item.nome);
			self.options.hiddenElem.val(ui.item.id);

			if (self.options.btnAction) {
				console.log("Antes " + self.options.btnAction.attr('disabled'));
				
				self.options.btnAction.removeAttr('disabled');
				
				console.log("Depois " + self.options.btnAction.attr('disabled'));
			}

			return false;
		});

		this.element.on('buscaapostadorchange', function(event, ui) {
			if (ui.item == null) {
				self.options.hiddenElem.val(0);
				if (self.options.btnAction) {
					self.options.btnAction.attr('disabled', true);
				}
			}
		});

		this._super();
	},

	_renderItem : function(ul, item) {
		ul.addClass("f-dropdown");
		ul.attr("data-dropdown-content");

		return $("<li>").append(
				"<span style='padding:0.25em;'>" + item.nome + "</span>")
				.appendTo(ul);
	},
});
