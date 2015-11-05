# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/

$ ->
  $('#modal-photo').modal({show: false});
  $(".form-disabled input").prop("disabled", true);
  $(".form-disabled select").prop("disabled", true);

  $(".question-mark").click(showImageInModal);

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
}

function showImageInModal(){
    var photo_path = $(this).siblings(".photo-path").first().html();
    photo_path = '/assets/'+photo_path;

    var label_for = "form_irr_"+ $(this).attr('name');
    var nome_foto = $("label[for='"+label_for+"']").html();

    $('.modal-body').html('<img src="'+photo_path+'" class="img-responsive" alt="'+nome_foto+'">');

    $("#modal-photo-title").html(nome_foto);

    $('#modal-photo').modal('show');
}

$('.btnNext').click(function(){
    $('.nav-tabs > .active').next('li').find('a').trigger('click');
    $('html,body').animate({scrollTop: 0});
});

$('.btnPrevious').click(function(){
    $('.nav-tabs > .active').prev('li').find('a').trigger('click');
    $('html,body').animate({scrollTop: 0});
});
`