# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/

$ ->
    $('#modal-photo').modal({show: false});
    $(".form-disabled input").prop("disabled", true);
    $(".form-disabled select").prop("disabled", true);
    $(".form-disabled textarea").prop("disabled", true);

    $(".question-mark").click(showImageInModal);

    $('#form_irr_larguraDaSuperficieDaAgua').change(calculateSeccao);
    $('#form_irr_profundidadeMedia').change(calculateSeccao);

    $('#form_irr_velocidadeMedia').change(calculateCauldal);
    $('#form_irr_seccao').change(calculateCauldal); displayIRR();


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
    calculateIRR();
    $('html,body').animate({scrollTop: 0});
});

$('.btnPrevious').click(function(){
    $('.nav-tabs > .active').prev('li').find('a').trigger('click');
    calculateIRR();
    $('html,body').animate({scrollTop: 0});
});

$('.tab-pane input, .tab-pane select, .tab-pane textarea').on('invalid', function(){

    // Find the tab-pane that this element is inside, and get the id
    var $closest = $(this).closest('.tab-pane');
    var id = $closest.attr('id');

    // Find the link that corresponds to the pane and have it show
    $('.nav a[href="#' + id + '"]').tab('show');

});

$('.nav-tabs').click(function() {
    calculateIRR();
});

Array.min = function(array) {
    return Math.min.apply(Math,array);
};

Array.max = function(array) {
    return Math.max.apply(Math,array);
};

$('.form_form_irr').submit(function(){
    calculateIRR();

    $('#form_irr_irr_hidrogeomorfologia').val($("#irr_hidrogeomorfologia").text());
    $('#form_irr_irr_qualidadedaagua').val($("#irr_qualidadedaagua").text());
    $('#form_irr_irr_alteracoesantropicas').val($("#irr_alteracoesantropicas").text());
    $('#form_irr_irr_corredorecologico').val($("#irr_corredorecologico").text());
    $('#form_irr_irr_participacaopublica').val($("#irr_participacaopublica").text());
    $('#form_irr_irr_organizacaoeplaneamento').val($("#irr_organizacaoeplaneamento").text());
    $('#form_irr_irr').val($("#irr_total").text());

    if($('#form_irr_irr_hidrogeomorfologia').val() != '' &&
        $('#form_irr_irr_qualidadedaagua').val() != '' &&
        $('#form_irr_irr_alteracoesantropicas').val() != '' &&
        $('#form_irr_irr_corredorecologico').val() != '' &&
        $('#form_irr_irr_participacaopublica').val() != '' &&
        $('#form_irr_irr_organizacaoeplaneamento').val() != '' &&
        $('#form_irr_irr').val() != '')
    return true;
    else return false;
});

function calculateHidrogeomorfologia() {
    // hidrogeomorfologia
    value_irr = [];
    $('#form_irr-hidrogeomorfologia input:checked').each(function() {
        if(!!$(this).attr('value_irr'))
            value_irr.push(parseInt($(this).attr('value_irr')));
    });

    /*if(value_irr.length > 0)
        value_irr_hidrogeomorfologia = Array.max(value_irr);
    else value_irr_hidrogeomorfologia = 0;*/

    if(value_irr.length > 0)
        return Array.max(value_irr);
    else return 0;
}

function calculateQualidadedaagua() {
    value_irr = [];

    // qualidade da agua
    $('#form_irr-indiciosnaagua input:checked').each(function() {
        if(!!$(this).attr('value_irr'))
            value_irr.push(parseInt($(this).attr('value_irr')));
    });

    $('#form_irr-cordaagua input:checked').each(function() {
        if(!!$(this).attr('value_irr'))
            value_irr.push(parseInt($(this).attr('value_irr')));
    });

    $('#form_irr-odordaagua input:checked').each(function() {
        if(!!$(this).attr('value_irr'))
            value_irr.push(parseInt($(this).attr('value_irr')));
    });

    temp_macroinvertebrados = [];
    $('#form_irr-macroinvertebrados input:checked').each(function() {
        if(!!$(this).attr('value_irr'))
            temp_macroinvertebrados.push(parseInt($(this).attr('value_irr')));
    });

    if(temp_macroinvertebrados.length > 0)
        value_irr.push(Array.min(temp_macroinvertebrados));

    /*if(value_irr.length > 0)
        value_irr_qualidadedaagua = Array.max(value_irr);
    else value_irr_qualidadedaagua = 0;*/

    if(value_irr.length > 0)
        return Array.max(value_irr);
    else return 0;
}

function calculateAlteracoesAntropicas() {
    value_irr = [];

    // alteracoes antropicas
    $('#form_irr-alteracoesantropicas input:checked').each(function() {
        if(!!$(this).attr('value_irr'))
            value_irr.push(parseInt($(this).attr('value_irr')));
    });

    /*if(value_irr.length > 0)
        value_irr_alteracoesantropicas = Array.max(value_irr);
    else value_irr_alteracoesantropicas = 0;*/

    if(value_irr.length > 0)
        return Array.max(value_irr);
    else return 0;
}

function calculateCorredorEcologico() {
    value_irr = [];

    // corredor ecologico
    $('#form_irr-corredorecologico input:checked').each(function() {
        if(!!$(this).attr('value_irr'))
            value_irr.push(parseInt($(this).attr('value_irr')));
    });

    /*if(value_irr.length > 0)
        value_irr_corredorecologico = Array.max(value_irr);
    else value_irr_corredorecologico = 0;*/

    if(value_irr.length > 0)
        return Array.max(value_irr);
    else return 0;
}

function calculateParticipacaoPublica() {
    value_irr = [];

    // participacao publica
    $('#form_irr-participacaopublica input:checked').each(function() {
        if(!!$(this).attr('value_irr'))
            value_irr.push(parseInt($(this).attr('value_irr')));
    });

    /*if(value_irr.length > 0)
        value_irr_participacaopublica = Array.max(value_irr);
    else value_irr_participacaopublica = 0;*/

    if(value_irr.length > 0)
        return Array.max(value_irr);
    else return 0;
}

function calculateOrganizacaoEPlaneamento() {
    value_irr = [];

    // organizacao e planeamento
    $('#form_irr-organizacaoeplaneamento input:checked').each(function() {
        if(!!$(this).attr('value_irr'))
            value_irr.push(parseInt($(this).attr('value_irr')));
    });

    /*if(value_irr.length > 0)
        value_irr_organizacaoeplaneamento = Array.max(value_irr);
    else value_irr_organizacaoeplaneamento = 0;*/

    if(value_irr.length > 0)
        return Array.max(value_irr);
    else return 0;
}

function calculateIRR() {
    value_irr_hidrogeomorfologia = calculateHidrogeomorfologia();
    value_irr_qualidadedaagua = calculateQualidadedaagua();
    value_irr_alteracoesantropicas = calculateAlteracoesAntropicas();
    value_irr_corredorecologico = calculateCorredorEcologico();
    value_irr_participacaopublica = calculateParticipacaoPublica();
    value_irr_organizacaoeplaneamento = calculateOrganizacaoEPlaneamento();

    // irr final
    irr = Math.max(value_irr_hidrogeomorfologia,value_irr_qualidadedaagua,value_irr_alteracoesantropicas,value_irr_corredorecologico,
        value_irr_participacaopublica,value_irr_organizacaoeplaneamento);

    /////////////////////////////////////////
    // mostrar em divs

    $("#irr_hidrogeomorfologia").text(value_irr_hidrogeomorfologia);
    $("#irr_qualidadedaagua").text(value_irr_qualidadedaagua);
    $("#irr_alteracoesantropicas").text(value_irr_alteracoesantropicas);
    $("#irr_corredorecologico").text(value_irr_corredorecologico);
    $("#irr_participacaopublica").text(value_irr_participacaopublica);
    $("#irr_organizacaoeplaneamento").text(value_irr_organizacaoeplaneamento);
    $("#irr_total").text(irr);
}

function calculateIRRFromValues(vh,vq,va,vc,vp,vo){
    irr = Math.max(vh,vq,va,vc,vp,vo);

    $("#irr_hidrogeomorfologia").text(vh);
    $("#irr_qualidadedaagua").text(vq);
    $("#irr_alteracoesantropicas").text(va);
    $("#irr_corredorecologico").text(vc);
    $("#irr_participacaopublica").text(vp);
    $("#irr_organizacaoeplaneamento").text(vo);
    $("#irr_total").text(irr);
}

function displayIRR() {
    $("#irr_hidrogeomorfologia").text($('#form_irr_irr_hidrogeomorfologia').val());
    $("#irr_qualidadedaagua").text($('#form_irr_irr_qualidadedaagua').val());
    $("#irr_alteracoesantropicas").text($('#form_irr_irr_alteracoesantropicas').val());
    $("#irr_corredorecologico").text($('#form_irr_irr_corredorecologico').val());
    $("#irr_participacaopublica").text($('#form_irr_irr_participacaopublica').val());
    $("#irr_organizacaoeplaneamento").text($('#form_irr_irr_organizacaoeplaneamento').val());
    $("#irr_total").text($('#form_irr_irr').val());
}

`