# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/

$ ->
	$('#myModal').modal({show: false});
	$(".form-disabled input").prop("disabled", true);
	$(".form-disabled select").prop("disabled", true);

	$('#form_irr_larguraDaSuperficieDaAgua').change(calculateSeccao);
	$('#form_irr_profundidadeMedia').change(calculateSeccao);

	$('#form_irr_velocidadeMedia').change(calculateCauldal);
	$('#form_irr_seccao').change(calculateCauldal);


`function calculateSeccao() {
	if(!isNaN($('#form_irr_larguraDaSuperficieDaAgua').val()) &&
		!isNaN($('#form_irr_profundidadeMedia').val())) {
		var newValue = $('#form_irr_profundidadeMedia').val() * $('#form_irr_larguraDaSuperficieDaAgua').val();
		$('#form_irr_seccao').val(newValue.toFixed(2));
	}
	calculateCauldal();
}

function calculateCauldal() {
	if(!isNaN($('#form_irr_velocidadeMedia').val()) &&
		!isNaN($('#form_irr_seccao').val())) {
		var newValue = $('#form_irr_velocidadeMedia').val() * $('#form_irr_seccao').val();
		$('#form_irr_caudal').val(newValue.toFixed(2));
	}
}`