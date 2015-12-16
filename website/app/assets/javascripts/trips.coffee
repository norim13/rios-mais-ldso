# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/

`
currentMarker = null;
addedMarkers = new Array();
map = null;
id_trip = null;

marker_line_array = new Array();
connection_line = null;

$(document).on('click', '#add-point-btn', submitPonto);

$(document).ready(function() {
   if ($('.edit_trip').length)
        id_trip = $('.edit_trip').attr('data-trip-id');

    var mapCanvas = document.getElementById('add-trip-points-map');
    var mapOptions = {
        center: new google.maps.LatLng(41.179379, -8.606543),
        zoom: 15,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(mapCanvas, mapOptions);
    getExistantPoints(map);
    addClickListenerToMap();

		$("#add-gps-point-trip").on('click', addGPSMarker);
    $("#add-gps-point-trip").removeAttr("disabled");


});

function addClickListenerToMap(){
    mapClickListenerHandler =
			google.maps.event.addListener(map, 'click', function(event) {
	        placeMarker(event.latLng, map);
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
		if(currentMarker != null) {
        if (!removeCurrentMarker(map, false))
          return;
    }

		//console.log(location);
    var marker = new google.maps.Marker({
        position: location,
        map: map
    });

    currentMarker = marker;
    google.maps.event.addListener(marker, 'click', function(event) {
        removeCurrentMarker(map, false);
    });
    map.panTo(location);


    /*definitiveMarkers.push(marker);
    markerCount++;*/
    $("#add-gps-point-trip").removeAttr("disabled");

    addMarkerForm(marker);

    $('html, body').animate({
        scrollTop: $("#form-current-marker").offset().top
    }, 1000);
}



function addMarkerForm(marker){
    $("#point-lat").val(marker.getPosition().lat());
    $("#point-lon").val(marker.getPosition().lng());
    $("#add-point-btn").removeAttr("disabled");
    $(".empty-this").each(function(){$(this).removeAttr("disabled");});
}

function removeCurrentMarker(map, force) {
    var x = true;
    if (!force)
        x = window.confirm("Tem a certeza que quer remover o ponto actual e as suas informações?")
    if (x){
        currentMarker.setMap(null);
        currentMarker = null;
        $(".empty-this").each(function(){ $(this).val(""); $(this).attr("disabled", "disabled"); });
        $("#point-lat").val("Nenhum ponto selecionado...");
        $("#point-lon").val("Nenhum ponto selecionado...");
        $("#add-point-btn").attr("disabled", "disabled");
		    return true;
    }
		return false;
}


function submitPonto(){
		//var nome = $("#point-nome").val();
    var trip_point = {};
    trip_point.lat = $("#point-lat").val();
    trip_point.lon = $("#point-lon").val();
    trip_point.descricao = $("#point-text").val();
    trip_point.trip_id = id_trip;
    var obj = {};
    obj.trip_point = trip_point;

    var images = new Array();

    if($('input[name="images[]"]').val() != "") {
        var imgs = $('input[name="images[]"]')[0].files[0];
        var img = {};
        img.image = imgs;
        images.push(img);
    }

    $.ajax({
        type: 'POST',
        url: '/trip_points',
        data: obj,
        success: function(data){
            for (var i = 0; i < images.length; i++) {
                uploadImages(images[i].image,data.trip_point_id);
            }

            placeDefinitiveMarker(currentMarker, map);
            removeCurrentMarker(map, true);

            $('input[name="images[]"]').val("");

            $('html, body').animate({
                scrollTop: $("#add-trip-points-map").offset().top
            }, 1000);
        },
        error: function(err){
            window.alert("Ocorreu um erro ao submeter a informação do ponto...");
        }
    });

}

function uploadImages(img,rp_id) {
    var formData = new FormData();
    formData.append('trip_image[image]',img);
    formData.append('trip_image[trip_point_id]',rp_id);
    $.ajax({
        url: '/trip_point_image',
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        type: 'POST',
        success: function (data) {
            //console.log("Imagem inserida");
        }
    });
}

function placeDefinitiveMarker(marker, map){
    addedMarkers.push(currentMarker);
    marker_line_array.push(marker.position);

    var marker = new google.maps.Marker({
        position: marker.getPosition(),
        map: map
    });
    marker.setIcon('http://maps.google.com/mapfiles/ms/icons/green-dot.png')

    if (connection_line != null)
        connection_line.setMap(null);
        connection_line = new google.maps.Polyline(
        {
            path: marker_line_array,
            strokeColor: "#2196F3",
            strokeOpacity: 1.0,
            strokeWeight: 2,
            map: map
        });

}

function getExistantPoints(map){
    $("#points-info .point-info").each(function(){
        var lat = $(this).attr('data-point-lat');
        var lon = $(this).attr('data-point-lon');

        var marker = new google.maps.Marker({
            position: new google.maps.LatLng(lat, lon)
        });
        placeDefinitiveMarker(marker, map);
    });
}
`