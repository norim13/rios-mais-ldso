# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/

`
    $( document ).ready(function() {
        $("#user_distrito_id").on('change',function() {
            getConcelhos($("#user_distrito_id").val());
        });
    });

    function getConcelhos(distritoId) {
        var select = $('#user_concelho_id');
        select.html('');

        $.ajax ({
            url: '/concelhos?id=' + distritoId,
            type: 'GET',
            dataType: 'json',
            error: function (jqXHR, textStatus, errorThrown) {
                //$('body').append("AJAX Error:" + textStatus);
                select.html('<option>Escolha o concelho</option>');
            },
            success: function (data, textStatus, jqXHR) {
                $.each(data,function(key, value)
                {
                    select.append('<option value=' + value.id + '>' + value.nome + '</option>');
                });
                // $('body').append("Successful AJAX call:" + data);
            }
        });

    } `