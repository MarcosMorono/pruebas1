$(document).ready(function() {	
	/**
	 * Recupera todos los campos data-check y añade el evento onkeypress
	 * para permitir la introduccion de los caracteres definidos en ese atributo
	 */
	$('[data-check]').each(function() {
		$(this).keypress(function(event){
			if(event.which > 31 && !new RegExp($(this).attr('data-check')).
					test(String.fromCharCode(event.which))){
	           event.preventDefault();
	       }
		});
	});
	
	
	$('[type=number]').each(function() {
		$(this).keypress(function(event){
	       if(event.which > 31 && isNaN(String.fromCharCode(event.which))){
	           event.preventDefault();
	       }
		});
	});
	
	if(typeof datatables_lenguaje !== 'undefined') {
		$.extend( true, $.fn.dataTable.defaults, {
		    "searching": false,
		    "language" : datatables_lenguaje
		} );
	}
});

jQuery.fn.preventDoubleSubmission = function () {
    $(this).on('submit', function (e) {
        var $form = $(this);

        if ($form.data('submitted') === true) {
            // Previously submitted - don't submit again
            e.preventDefault();
        } else {
            // Mark it so that the next submit can be ignored
            $form.data('submitted', true);
        }
    });

    // Keep chainability
    return this;
};

$(function () {
    $('form').preventDoubleSubmission();

    $('[data-form-submit-clean]').click(function (e) {
        var form = $(this).attr("data-form-submit-clean");
        $(form).closest("form").find("input[type=text], textarea").val("");
        $(this).parent().toggleClass('open');
        e.stopPropagation();
        $("input:text:visible:first").focus();
    });

    $('[data-form-submit-modal]').click(function () {
        var form = $(this).attr("data-form-submit-modal");
        $(form).submit();
        var modal = $(this).attr("data-target");
        $(modal).modal('hide');
    });

    $('[data-form-submit]').click(function () {
        var form = $(this).attr("data-form-submit");
        if ($(this).is('[data-lang-code]')) {
            var idioma = $(this).attr("data-lang-code");
            $(form).find("[name=locale]").val(idioma);
        }
        $(form).submit();
    });

    $('.table-responsive [data-toggle="tooltip"]').tooltip({
        container: 'body'
    });

    $('.btn-file [type=file]').on('change', function () {
        $(this).parent().find('span').text(this.value);
    });

});

