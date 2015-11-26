# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/

`
var ctx = null;
var irr_hidrogeomorfologia = null;
var irr_qualidadedaagua = null;
var irr_alteracoesantropicas = null;
var irr_corredorecologico = null;
var irr_participacaopublica = null;
var irr_organizacaoeplaneamento = null;
var irr_total = null;


$(document).ready(function(){
    $('#modal-photo').modal({show: false});
    $(".form-disabled input").prop("disabled", true);
    $(".form-disabled select").prop("disabled", true);
    $(".form-disabled textarea").prop("disabled", true);

    $(".question-mark").click(showImageInModal);

    $('#form_irr_larguraDaSuperficieDaAgua').change(calculateSeccao);
    $('#form_irr_profundidadeMedia').change(calculateSeccao);

    $('#form_irr_velocidadeMedia').change(calculateCauldal);
    $('#form_irr_seccao').change(calculateCauldal);

    loadIRRs();

    if ($("#chart-irr").length){
        ctx = $("#chart-irr").get(0).getContext("2d");
        // This will get the first returned node in the jQuery collection.
    }

    displayIRR();

});


function calculateSeccao() {
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
    else return 1;
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
    else return 1;
}

function calculateAlteracoesAntropicas() {
    value_irr = [];

    // alteracoes antropicas
    $('#form_irr-alteracoesantropicas input:checked').each(function() {
        if(!!$(this).attr('value_irr'))
            value_irr.push(parseInt($(this).attr('value_irr')));
    });

    $('#form_irr-alteracoesantropicas select').each(function() {
        if(!!$(this).attr('value_irr') && $(this).children("option").filter(":selected").val() != 'Nao')
          value_irr.push(parseInt($(this).attr('value_irr')));
    });

    /*if(value_irr.length > 0)
        value_irr_alteracoesantropicas = Array.max(value_irr);
    else value_irr_alteracoesantropicas = 0;*/

    if(value_irr.length > 0)
        return Array.max(value_irr);
    else return 1;
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
    else return 1;
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
    else return 1;
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
    else return 1;
}

function calculateIRR() {

    irr_hidrogeomorfologia = calculateHidrogeomorfologia();
    irr_qualidadedaagua = calculateQualidadedaagua();
    irr_alteracoesantropicas = calculateAlteracoesAntropicas();
    irr_corredorecologico = calculateCorredorEcologico();
    irr_participacaopublica = calculateParticipacaoPublica();
    irr_organizacaoeplaneamento = calculateOrganizacaoEPlaneamento();

    // irr final
    irr_total = Math.max(irr_hidrogeomorfologia,irr_qualidadedaagua,irr_alteracoesantropicas,
        irr_corredorecologico,irr_participacaopublica,irr_organizacaoeplaneamento);

    /////////////////////////////////////////
    // mostrar em divs

    displayIRR();
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
    $("#irr_hidrogeomorfologia").text(irr_hidrogeomorfologia);
    $("#irr_qualidadedaagua").text(irr_qualidadedaagua);
    $("#irr_alteracoesantropicas").text(irr_alteracoesantropicas);
    $("#irr_corredorecologico").text(irr_corredorecologico);
    $("#irr_participacaopublica").text(irr_participacaopublica);
    $("#irr_organizacaoeplaneamento").text(irr_organizacaoeplaneamento);
    $("#irr_total").text(irr_total);

    if(ctx == null)
        return;

    var data_array = [irr_hidrogeomorfologia,irr_qualidadedaagua,
        irr_alteracoesantropicas,irr_corredorecologico,
        irr_participacaopublica,irr_organizacaoeplaneamento,
        irr_total]


    for(var i in data_array){
        switch(data_array[i]){
            case 1: data_array[i] = 5; break;
            case 2: data_array[i] = 4; break;
            case 3: data_array[i] = 3; break;
            case 4: data_array[i] = 2; break;
            case 5: data_array[i] = 1; break;
            default: data_array[i] = 0 ; break;
        }
    }

    var data = {
        labels: ["Hidrogeomorfologia", "Alterações Antrópicas", "Corredor Ecológico", "Qualidade da Água", "Participação Pública", "Organização e Planeamento", "Total"],
        datasets: [
            {
                label: "My First dataset",
                fillColor: "rgba(33,150,243,0.2)",
                strokeColor: "rgba(33,150,243,1)",
                pointColor: "rgba(33,150,243,1)",
                pointStrokeColor: "#fff",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(220,220,220,1)",
                data: data_array
            }
        ]
    };

    var myLineChart = new Chart(ctx).Line(data, {
        bezierCurve: false,
        //scaleBeginAtZero : true,
        scaleOverride:true,
        scaleSteps:5,
        scaleStartValue:0,
        scaleStepWidth:1,
        scaleLabel: function (valuePayload) {
            if(Number(valuePayload.value)===0)
                return '';
            if(Number(valuePayload.value)===1)
                return '5';
            if(Number(valuePayload.value)===2)
                return '4';
            if(Number(valuePayload.value)===3)
                return '3';
            if(Number(valuePayload.value)===4)
                return '2';
            if(Number(valuePayload.value)===5)
                return '1';
        }
    });
}

function loadIRRs(){
    irr_hidrogeomorfologia = parseInt($('#form_irr_irr_hidrogeomorfologia').val());
    irr_qualidadedaagua = parseInt($('#form_irr_irr_qualidadedaagua').val());
    irr_alteracoesantropicas = parseInt($('#form_irr_irr_alteracoesantropicas').val());
    irr_corredorecologico = parseInt($('#form_irr_irr_corredorecologico').val());
    irr_participacaopublica = parseInt($('#form_irr_irr_participacaopublica').val());
    irr_organizacaoeplaneamento = parseInt($('#form_irr_irr_organizacaoeplaneamento').val());
    irr_total = parseInt($('#form_irr_irr').val());
}

`