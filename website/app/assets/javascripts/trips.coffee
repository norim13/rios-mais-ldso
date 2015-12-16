# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/

`
currentMarker = null;
definitiveMarkers = new Array();
markerCount = 1;
map = null;

$(document).ready(function() {
    var mapCanvas = document.getElementById('add-trip-points-map');
    var mapOptions = {
        center: new google.maps.LatLng(41.179379, -8.606543),
        zoom: 15,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(mapCanvas, mapOptions);

    addClickListenerToMap();

		$("#add-gps-point-trip").on('click', addGPSMarker);
    $("#add-gps-point-trip").removeAttr("disabled");
});

function addClickListenerToMap(){
    mapClickListenerHandler =
			google.maps.event.addListener(map, 'click', function(event) {
	        placeMarker(event.latLng, map, true);
	    });
}

function removeClickListenerToMap(){
    google.maps.event.removeListener(mapClickListenerHandler);
}

function addGPSMarker(){
    $("#add-gps-point-trip").attr("disabled", "disabled");
    navigator.geolocation.getCurrentPosition(function(position) {
        var temp_loc = {};
        temp_loc.lat = position.coords.latitude;
        temp_loc.lng = position.coords.longitude;
        placeMarker(temp_loc, map);
    });
}

function placeMarker(location, map) {


		//console.log(location);
    var marker = new google.maps.Marker({
        position: location,
        map: map
    });
    marker.setIcon('http://maps.google.com/mapfiles/ms/icons/green-dot.png')

    google.maps.event.addListener(marker, 'click', function(event) {
        removeMarker(map, marker);
    });
    map.panTo(location);

		currentMarker = marker;
    /*definitiveMarkers.push(marker);
    markerCount++;*/
    $("#add-gps-point-trip").removeAttr("disabled");

    addMarkerForm(marker);

    $('html, body').animate({
        scrollTop: $("#form-current-marker").offset().top
    }, 1000);
}

function removeCurrentMarker(){
		if(currentMarker != null) {
        removeMarker(map);
				console.log("gg");
    }
}

function addMarkerForm(marker){
    $("#left-panel-forms").append(
        '<div id="form-current-marker" class="thumbnail">'+ //id = marker-form-{latitude}-{longitude}
	        '<div class="row">'+
			        '<div class="col-md-4">'+
			            'Nome:<input id="input-nome" type="text-field" name="nome" class="form-control" value=""/>'+
			        '</div>'+
			        '<div class="col-md-4">'+
			            'Latitude:<input type="text-field" name="lat" class="form-control" readonly="true" value="'+marker.getPosition().lat()+'"/>'+
			        '</div>'+
			        '<div class="col-md-4">'+
			            'Longitude:<input type="text-field" name="lon" class="form-control" readonly="true" value="'+marker.getPosition().lng()+'"/>'+
			        '</div>'+
			        '<div class="col-md-12">'+
			            '<textarea type="text-field" name="descricao" class="form-control"  rows="4" placeholder="Insira uma descrição"/>'+
			        '</div>'+
	        '</div>'+
        '</div>'
    );
}

function removeMarker(map) {
    var x=window.confirm("Tem a certeza que quer remover o ponto?")
    if (x){
        currentMarker.setMap(null);
        currentMarker = null;
        $("#form-current-marker").remove();
    }
}

`