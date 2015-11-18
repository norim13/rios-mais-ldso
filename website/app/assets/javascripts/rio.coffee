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
            $("#designacao-rio").html(info_rio.designacao);
            $("#tipo-rio").html(info_rio.tipo);
            $("#bacia-rio").html(info_rio.bacia);
        }
    }

});


`