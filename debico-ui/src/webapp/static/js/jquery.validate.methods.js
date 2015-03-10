/**
 * Metodos de validacao customizados da biblioteca jQuery Validate
 * @see http://jqueryvalidation.org/
 * @since 1.2.0 
 */
$(document).ready(function() {
	/*
	 * Valida se o campo de senha esta de acordo com a politica.
	 */
	$.validator.addMethod("passwordRuleCheck", function(value,
			element) {
		return this.optional(element) || /(?=.{6,})(?=.*[a-zA-Z])(?=.*[0-9]).*/.test(value);
	}, "Invalid Password");
});