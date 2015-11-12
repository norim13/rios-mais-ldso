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

//http://localhost:10500/geoserver/rios/wms?service=WMS&version=1.1.0&request=GetMap&layers=rios:AtAgua_Agsup_rios_AAmb_SNIRH_PC&styles=&bbox=-116041.26840000041,-294342.9310999997,162111.18240000028,275275.92229999974&width=375&height=768&srs=EPSG:3763&format=application/openlayers
//List of layers
var lund = new OpenLayers.Layer.WMS(
	"Rios",
	"http://***REMOVED***:10500/geoserver/rios/wms?",
	{
layers: "rios:AtAgua_Agsup_rios_AAmb_SNIRH_PC",
//layers: "rios:AtAgua_Agsup_zhidnetord5km2_PC",
//layers: "rios:netElementL",

transparent: "true",
format: "image/png"
}
);
map.addLayer(lund);

info = new OpenLayers.Control.WMSGetFeatureInfo({
	url: 'http://***REMOVED***:10500/geoserver/rios/wms?',
	title: 'Identify features by clicking',
	queryVisible: true,
	eventListeners: {
		getfeatureinfo: function(event) {
			/*map.addPopup(new OpenLayers.Popup.FramedCloud(
				"chicken",
				map.getLonLatFromPixel(event.xy),
				null,
				event.text,
				null,
				true
			));*/

			var x = Math.round(event.xy.x);
			var y = Math.round(event.xy.y);

			var bbox = map.getExtent();
      //OpenLayers.LonLat(-8.495595,41.111715).transform(geographic, mercator);
      var url =  "http://***REMOVED***:10500/geoserver/rios/wms?"+
        "LAYERS=rios%3AAtAgua_Agsup_rios_AAmb_SNIRH_PC"+
        "&QUERY_LAYERS=rios%3AAtAgua_Agsup_rios_AAmb_SNIRH_PC"+
        "&STYLES="+
        "&SERVICE=WMS"+
        "&VERSION=1.1.1"+
        "&REQUEST=GetFeatureInfo"+
        "&BBOX="+bbox.left+"%2C"+bbox.bottom+"%2C"+bbox.right+"%2C"+bbox.top+
        //"&FEATURE_COUNT=10"+
        "&HEIGHT=300"+
        "&WIDTH=1160"+
        //"&FORMAT=image%2Fpng"+
        "&INFO_FORMAT=application%2Fjson"+
        "&SRS=EPSG%3A900913"+
        "&X="+x+
        "&Y="+y;
				//console.log(url);
        $.ajax({
            url: url,
            error: function(err) {
                console.log("error");
		            console.log(err);
            },
            dataType: 'json',
            success: function(data) {
                console.log("success. data:");
		            console.log(data.features[0]);
		            var designacao_rio = data.features[0].properties.designacao;
								$(".rio-info h4").html("Nome do rio: "+designacao_rio);
            },
            type: 'GET'
        });
		}
	}
});
map.addControl(info);
info.activate();

//?service=WMS&version=1.1.0&request=GetMap&layers=rios:AtAgua_Agsup_rios_AAmb_SNIRH_PC&styles=&bbox=-116041.26840000041,-294342.9310999997,162111.18240000028,275275.92229999974&width=375&height=768&srs=EPSG:3763&format=application/openlayers


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
	/*if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(funtion (position){

        });
    }*/

	if ("geolocation" in navigator) {
	navigator.geolocation.getCurrentPosition(function(position) {
	myLat = position.coords.latitude;
		myLon = position.coords.longitude;
		var center = new OpenLayers.LonLat(myLon,myLat).transform(geographic, mercator);
		map.setCenter(center, 14);
});
} else {
console.log("Geolocation is not supported by this browser.");
}
}

function showPosition(position) {

}`