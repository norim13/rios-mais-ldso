
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/
`
    //var base_url = 'http://***REMOVED***:10500/geoserver/rios';
    var base_url = '***REMOVED***/geoserver/rios';
$(document).ready(function(){
    $('#searchform').on('submit', function(e){
        e.preventDefault();

        var propriedade_a_procurar = 'designacao';
        //var campos = {"1" : "designacao", "2" : "codrios", "3" : "bacia"};
        var campos = {"1" : "RIVER_NAME", "2" : "EU_CD", "3" : "TYPE_NAME"};
        console.log("option: "+$("#campo-pesquisa option:selected").val());
        propriedade_a_procurar = campos[$("#campo-pesquisa option:selected").val()];


        var filter = new OpenLayers.Filter.Comparison({
            type: OpenLayers.Filter.Comparison.LIKE,
            matchCase:false,
            property: propriedade_a_procurar,
            value: "*" + $("#pesquisa-rios-input").val() + "*"
        });

        var  wfsProtocol = new OpenLayers.Protocol.WFS.v1_1_0({
            url: base_url+'/wms?SERVICE=WFS',
            geometryName: "SHAPE",
            featurePrefix: 'rios',
            //featureType: 'AtAgua_Agsup_rios_AAmb_SNIRH_PC',
            featureType: 'BaciasMA',
            srsName: 'EPSG:900913' // eg "EPSG:900913"
        });

        wfsProtocol.read ({
            filter:filter,
            callback: processTheQuery
            //scope: strategy
        });

        function processTheQuery(request) {
            // the first bit sets the value of sExt to be the a boundingbox on the features returned.
            // Depending on application logic, this could happen in other places in the code.
            //console.log('abcd');
            $("#resultados-pesquisa-rios").find("tr:gt(0)").remove();
            if(request.features.length == 0){
                $("#resultados-pesquisa-rios")
                    .append("<tr><td></td><td><i>Sem resultados...</i></td><td></td></tr>");
            }
            else
                for(var i in request.features){
                    //console.log(request.features[i].data);
                    var info_rio = request.features[i].data;
                    var row = $('<tr></tr>');

                    //mapas velhos
                    /*$(row).append('<td>'+decode_utf8(info_rio.codrios)+'</td>');
                    //var a = '<a href="/rio/'+info_rio.codrios+'">'+info_rio.designacao+'</a>';
                    $(row).append('<td>'+decode_utf8(info_rio.designacao)+'</td>');
                    $(row).append('<td>'+decode_utf8(info_rio.tipo)+'</td>');
                    $(row).append('<td>'+decode_utf8(info_rio.bacia)+'</td>');
                    $(row).append('<td><a href="/rio/'+info_rio.codrios+'">'+
                        '<button class="btn btn-primary">Ver Rio</button>'+'</a></td>');*/

                    //mapas novos - bacias
                    $(row).append('<td>'+info_rio.EU_CD+'</td>');
                    //var a = '<a href="/rio/'+info_rio.codrios+'">'+info_rio.designacao+'</a>';
                    $(row).append('<td>'+info_rio.RIVER_NAME+'</td>');
                    $(row).append('<td>'+info_rio.TYPE_NAME+'</td>');
                    $(row).append('<td><a href="/rio/'+info_rio.EU_CD+'">'+
                        '<button class="btn btn-primary">Ver Rio</button>'+'</a></td>');

                    var a = $('<a href="/rio/'+info_rio.codrios+'>Ver</a>');
                    /*$(row).click(function() {
                        window.document.location = '/rio/'+info_rio.codrios;
                    }).hover( function() {
                        $(this).toggleClass('hover');
                    });*/
                    $("#resultados-pesquisa-rios").append(row);
                }

            /*if (request.data && request.data.bbox) {
             var b = request.data.bbox;
             sExt = new OpenLayers.Bounds(b[0],b[1],b[2],b[3]);
             }else {
             var fts = request.features;
             if (fts.length>0) {
             sExt = fts[0].geometry.getBounds().clone();
             for(var i=1;i<fts.length;i++) {
             sExt.extend(fts[i].geometry.getBounds());
             }
             }
             };
             selectedLayer.destroyFeatures(); // get rid of values from previous
             call
             // any other process of the returned features goes here eg display
             attributes in a popup
             selectedLayer.addFeatures(request.features);
             map.zoomToExtent(sExt);*/
        }



    })

});



`