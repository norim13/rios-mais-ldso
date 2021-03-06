# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/
`//myMap.js
//javascript to create web map displaying Google base map
//with custom overlays from geoserver wms and kml
var myLat = null;
var myLon = null;
var geographic = new OpenLayers.Projection("EPSG:4326");
var mercator = new OpenLayers.Projection("EPSG:900913");
var geographic2 = new OpenLayers.Projection("EPSG:3763");
var base_url = 'http://***REMOVED***:10600/geoserver/rios';
//var base_url = '***REMOVED***/geoserver/rios';

(function () {
window.onload = function () {

		//Defining bounds
		var world = new OpenLayers.Bounds(-180, -89, 180, 89).transform(geographic, mercator);
		//Defining map center
		var lund_center = new OpenLayers.LonLat(-8.495595,41.111715).transform(geographic, mercator);

		var options = {
			projection: mercator,
			displayProjection: geographic2,
			units: "m",
			maxExtent: world,
			maxResolution: 156543.0399,

		};
		//Defining main variables
		var map = new OpenLayers.Map("map", options, { controls: [] });

		var osm = new OpenLayers.Layer.OSM();

		map.addLayer(osm);


		// TESTE MARKER
		/*var lonLat = new OpenLayers.LonLat(-8.495595,41.111715)
		        .transform(geographic,map.getProjectionObject());

		var markers = new OpenLayers.Layer.Markers( "Markers" );
		map.addLayer(markers);

		markers.addMarker(new OpenLayers.Marker(lonLat));*/
	    ////////////////////////////////////



		//List of layers
		var lund = new OpenLayers.Layer.WMS(
				"Rios",
			  base_url+"/wms?",
				{
					//layers: "rios:AtAgua_Agsup_rios_AAmb_SNIRH_PC",
					//layers: "rios:AtAgua_Agsup_zhidnetord5km2_PC",
					layers: "rios:netElementL",
					//layers: "rios:BaciasMA",
					transparent: "true",
					format: "image/png"
				}
		);
		map.addLayer(lund);

    var lund2 = new OpenLayers.Layer.WMS(
        "Bacias",
        base_url+"/wms?",
        {
            //layers: "rios:AtAgua_Agsup_rios_AAmb_SNIRH_PC",
            //layers: "rios:AtAgua_Agsup_zhidnetord5km2_PC",
            layers: "rios:BaciasMA",
            //layers: "rios:BaciasMA",
            transparent: "true",
            format: "image/png"
        }
    );
    map.addLayer(lund2);

		info = new OpenLayers.Control.WMSGetFeatureInfo({
			url: base_url+'/wms?',
			title: 'Identify features by clicking',
			queryVisible: true,
			eventListeners: {
				getfeatureinfo: function(event) {
					var x = Math.round(event.xy.x);
					var y = Math.round(event.xy.y);

					var bbox = map.getExtent();
		      //OpenLayers.LonLat(-8.495595,41.111715).transform(geographic, mercator);
		      var url =  base_url+"/wms?"+
		        //"LAYERS=rios%3AAtAgua_Agsup_rios_AAmb_SNIRH_PC"+
              "LAYERS=rios%3ABaciasMA"+
		        //"&QUERY_LAYERS=rios%3AAtAgua_Agsup_rios_AAmb_SNIRH_PC"+
              "&QUERY_LAYERS=rios%3ABaciasMA"+
		        "&STYLES="+
		        "&SERVICE=WMS"+
		        "&VERSION=1.1.1"+
		        "&REQUEST=GetFeatureInfo"+
		        "&BBOX="+bbox.left+"%2C"+bbox.bottom+"%2C"+bbox.right+"%2C"+bbox.top+
		        //"&FEATURE_COUNT=10"+
		        "&HEIGHT=500"+
		        "&WIDTH=1160"+
		        //"&FORMAT=image%2Fpng"+
		        "&INFO_FORMAT=application%2Fjson"+
		        "&SRS=EPSG%3A900913"+
		        "&X="+x+
		        "&Y="+y;
						console.log(url);
		        $.ajax({
		            url: url,
		            error: function(err) {
		                console.log("error");
				            console.log(err);
		            },
		            dataType: 'json',
		            success: function(data) {
		                //console.log("success. data:");
				            console.log(data);

				            if(data.features[0] != undefined) {
                        //console.log(data);
												//MAPAS NOVOS
												var designacao = data.features[0].properties.RIVER_NAME;
                        var codigo = data.features[0].properties.EU_CD;

						            //MAPAS ANTIGOS
												/*var designacao = decode_utf8(data.features[0].properties.designacao);
                        var codigo = decode_utf8(data.features[0].properties.codrios);
                        var bacia = decode_utf8(data.features[0].properties.bacia);
                        var tipo = decode_utf8(data.features[0].properties.tipo);*/


												if(!designacao) {
                            $("#rio-nome h4").html("Nome do rio: não encontrado...");
                            $("#codigo-rio").val("");
                        }
                        else {
                            $("#rio-nome h4").html("Nome do rio: " + designacao);
                            $("#codigo-rio").val(codigo);
                            $("#nome-rio").val(designacao);
														var latLon = map.getLonLatFromPixel(event.xy);
														//console.log(latLon);
														//convert to regular latitude and longitude
														var latLon_r = new OpenLayers.LonLat(latLon.lon, latLon.lat).transform(mercator,geographic);
														//fill lat and lon hidden fields
														$("#form_irr_lat").val(latLon_r.lat);
                            $("#form_irr_lon").val(latLon_r.lon);

                            $("#report_lat").val(latLon_r.lat);
                            $("#report_lon").val(latLon_r.lon);

                            map.addPopup(new OpenLayers.Popup.FramedCloud(
                                "chicken",
                                latLon,
                                null,
                                designacao,
                                null,
                                true
                            ));

                            map.setCenter(latLon, 14);
                        }
                    }
		            },
		            type: 'GET'
		        });
				}
			}
		});
		map.addControl(info);
		info.activate();

		//Map center and zoom
		map.setCenter(lund_center, 14);
		getLocation(map);
		//List of controls
		map.addControl(new OpenLayers.Control.LayerSwitcher());
		map.addControl(new OpenLayers.Control.KeyboardDefaults());
		//map.addControl(new new OpenLayers.Control.Navigation());

		//Coordinates of mouse position
		map.addControl(new OpenLayers.Control.MousePosition());

		var gmap = new OpenLayers.Layer.Google("Google Streets");
		map.addLayer(gmap);
		};

})();

function getLocation(map) {
	if ("geolocation" in navigator) {
		navigator.geolocation.getCurrentPosition(function(position) {
			myLat = position.coords.latitude;
			myLon = position.coords.longitude;
				//console.log("lat: " + myLat + " # lon: " + myLon);
			var center = new OpenLayers.LonLat(myLon,myLat).transform(geographic, mercator);
			map.setCenter(center, 14);
		});
	} else {
		console.log("Geolocation is not supported by this browser.");
	}
}
`