# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/
`

$(document).ready(function(){
	console.log("ready rio");
    var filter2 = new OpenLayers.Filter.Comparison({
        type: OpenLayers.Filter.Comparison.LIKE,
        matchCase:false,
        property: "codrios",
        value: "*" + $("#cod-rio").html() + "*"
    });

    var  wfsProtocol2 = new OpenLayers.Protocol.WFS.v1_1_0({
        url: 'http://***REMOVED***:10500/geoserver/rios/wms?SERVICE=WFS',
        geometryName: "SHAPE",
        featurePrefix: 'rios',
        featureType: 'AtAgua_Agsup_rios_AAmb_SNIRH_PC',
        srsName: 'EPSG:900913' // eg "EPSG:900913"
    });

    wfsProtocol2.read ({
        filter:filter2,
        callback: fillInfoRio
        //scope: strategy
    });

    function fillInfoRio(request) {
        // the first bit sets the value of sExt to be the a boundingbox on the features returned.
        // Depending on application logic, this could happen in other places in the code.
        if(request.features.length == 0){
            $("#designacao-rio").val("Código inválido...");
        }
        else {
            console.log(request.features[0].data);
            var info_rio = request.features[0].data;
            $("#designacao-rio").html(decode_utf8(info_rio.designacao));
            $("#tipo-rio").html(decode_utf8(info_rio.tipo));
            $("#bacia-rio").html(decode_utf8(info_rio.bacia));

            $('#perfil-rio-title').html("Informações sobre "+info_rio.designacao);
        }
    }

    if ($("#chart-irr").length){
        ctx = $("#chart-irr").get(0).getContext("2d");
        // This will get the first returned node in the jQuery collection.
        var irr_hidrogeomorfologia = null;
        var irr_qualidadedaagua = null;
        var irr_alteracoesantropicas = null;
        var irr_corredorecologico = null;
        var irr_participacaopublica = null;
        var irr_organizacaoeplaneamento = null;
        var irr_total = null;

        irr_hidrogeomorfologia = parseInt($('#irr_hidrogeomorfologia').html());
        irr_qualidadedaagua = parseInt($('#irr_qualidadedaagua').html());
        irr_alteracoesantropicas = parseInt($('#irr_alteracoesantropicas').html());
        irr_corredorecologico = parseInt($('#irr_corredorecologico').html());
        irr_participacaopublica = parseInt($('#irr_participacaopublica').html());
        irr_organizacaoeplaneamento = parseInt($('#irr_organizacaoeplaneamento').html());
        irr_total = parseInt($('#irr_total').html());

        var data_array = [irr_hidrogeomorfologia,irr_qualidadedaagua,
            irr_alteracoesantropicas,irr_corredorecologico,
            irr_participacaopublica,irr_organizacaoeplaneamento,
            irr_total]

        //console.log(data_array);
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
        //console.log(data_array);

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

});


`