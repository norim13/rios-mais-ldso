# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/

`
markers = new Array();
map = null;
id_trip = null;
currentLocationMarker = null;

marker_line_array = new Array();
connection_line = null;

$(document).on('click', '#current-location-btn-trip', goToCurrentLocation);

$(document).ready(function() {
   /*if ($('.edit_trip').length)
        id_trip = $('.edit_trip').attr('data-trip-id');*/

    var mapCanvas = document.getElementById('view-trip-points-map');
    var mapOptions = {
        center: new google.maps.LatLng(41.179379, -8.606543),
        zoom: 15,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    map = new google.maps.Map(mapCanvas, mapOptions);
    getExistantPoints(map);
    $('#modal-trip-point-info').modal({show: false});
});

function goToCurrentLocation(){
    navigator.geolocation.getCurrentPosition(function(position) {
        var temp_loc = {};
        temp_loc.lat = position.coords.latitude;
        temp_loc.lng = position.coords.longitude;

        var marker = new google.maps.Marker({
            position: new google.maps.LatLng(temp_loc.lat, temp_loc.lng),
            map: map
        });
        if(currentLocationMarker != null)
            currentLocationMarker.setMap(null);
        currentLocationMarker = marker;
        map.panTo(temp_loc);
    });
}

function placeDefinitiveMarker(marker, map){
    marker_line_array.push(marker.position);

    marker.setIcon('http://maps.google.com/mapfiles/ms/icons/green-dot.png')

}

function drawLine(){
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
        var id = $(this).attr('data-point-id');

        var marker = new google.maps.Marker({
            position: new google.maps.LatLng(lat, lon),
            map: map,
            id: id
        });
        google.maps.event.addListener(marker, 'click', function(event) {
            showMarkerInfo(marker);
        });
        markers.push(marker);
        placeDefinitiveMarker(marker, map);
    });

    if (markers.length > 0){
        map.panTo(markers[0].position);
        drawLine();
    }

}

function showMarkerInfo(marker){
    $.ajax({
        type: 'GET',
        url: '/trip_points/'+marker.id,
        success: function(data){
            //console.log(data);
            var content =
            '<div class="container-fluid">'+
                '<div class="row">'+
                    '<div class="col-md-6 col-xs-12">'+
                       '<p>Latitude: '+ data.point.lat +'</p>'+
                    '</div>'+
                    '<div class="col-md-6 col-xs-12">'+
                       '<p>Longitude: '+ data.point.lon +'</p>'+
                    '</div>'+
                    '<div class="col-xs-12">'+
                       '<p>Descrição: '+ data.point.descricao +'</p>'+
                   '</div>'+
                    '<div class="col-xs-12">'+
                       '<p>Imagens:</p>'+
                   '</div>'+
               '</div>'+
            '</div>';
            $('.modal-body').html(content);
            $('#modal-trip-point-info').modal('show');
        },
        error: function(err){
            console.log("Error getting info...");
            $('.modal-body').html("Ocorreu um erro...");
            $('#modal-trip-point-info').modal('show');
        }
    });
}

`