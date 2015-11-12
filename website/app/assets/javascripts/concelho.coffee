# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/

`
$( document ).ready(function() {
    $("#distrito_distrito_id").on('change',function() {
        getConcelhos($("#distrito_distrito_id").val());
    });
});

function getConcelhos(distritoId) {

    $.ajax ({
        url: '/concelhos?id=' + distritoId,
        type: 'GET',
        dataType: 'json',
        error: function (jqXHR, textStatus, errorThrown) {
            $('body').append("AJAX Error:" + textStatus);
        },
        success: function (data, textStatus, jqXHR) {
            var select = $('#concelho_concelho_id');
            select.html('');

            $.each(data,function(key, value)
            {
                select.append('<option value=' + key + '>' + value.nome + '</option>');
            });

           // $('body').append("Successful AJAX call:" + data);
        }
    });

} `