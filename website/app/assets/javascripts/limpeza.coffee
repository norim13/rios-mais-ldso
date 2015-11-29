`
    $(document).ready(function () {

        $("input").change(function () {
            var opcao = $(this).attr("data-resposta").split('-')[1];
            getRespostas(opcao);
        });
    });

    function getRespostas(perguntaId) {
        var select = $('#solucoes');

        $.ajax ({
            url: '/respostas?id=' + perguntaId,
            type: 'GET',
            dataType: 'json',
            error: function (jqXHR, textStatus, errorThrown) {
                //$('body').append("AJAX Error:" + textStatus);
                select.html('Error');
            },
            success: function (data, textStatus, jqXHR) {
                if($("#" + data.categoria_id + "-solucao").length){
                    $("#" + data.categoria_id + "-solucao").html('<h4>'+data.categoria + ' - ' +  data.opcao + '</h4>' + 'Proposta de solução: ' + data.resposta);
                } else {
                    select.append('<div class="thumbnail" id="' + data.categoria_id + '-solucao"><h4>' + data.categoria + ' - ' +  data.opcao + '</h4>' + 'Proposta de solução: ' + data.resposta + '</div>');
                }
            }
        });

    }


`