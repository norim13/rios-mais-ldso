# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/
`
markers = new Array();
markerCount = 1;
endpoint_for_connection = new Array();
connection_line = null;

$(document).ready(function(){

		var mapCanvas = document.getElementById('view-route-points-map');
		var mapOptions = {
		    center: new google.maps.LatLng(41.179379, -8.606543),
		    zoom: 15,
		    mapTypeId: google.maps.MapTypeId.ROADMAP
		};
		var map = new google.maps.Map(mapCanvas, mapOptions);

		//get points to be added to the map
		var points = getPointsOrderedInfo();

		//set map center to the first point
    var lat = parseFloat(points[0].lat);
    var lon = parseFloat(points[0].lon);
    map.setCenter({
        lat : lat,
        lng : lon
    });

		//add markers to map
		for(var i in points){
        var temp_loc = {};
        temp_loc.lat = parseFloat(points[i].lat);
        temp_loc.lng = parseFloat(points[i].lon);
				//console.log(temp_loc);
				placeMarkerInfo(temp_loc, map);
		}

    // draw a line connecting the points
    if (connection_line == null)
    {
        connection_line = new google.maps.Polyline(
            {   path: endpoint_for_connection,
                strokeColor: "#2196F3",
                strokeOpacity: 1.0,
                strokeWeight: 2,
                map: map
            });
    }
    else
        connection_line.setPath(endpoint_for_connection);
});


function placeMarkerInfo(location, map) {
    //console.log(location);
    var marker = new google.maps.Marker({
        position: location,
        map: map
    });

    google.maps.event.addListener(marker, 'click', function(event) {
        var offset = $(window).height()/3.0;
		    var latT = String(location.lat.toFixed(14)).replace(/\./g,'_');
        var lonT = String(location.lng.toFixed(14)).replace(/\./g,'_');
        $('#marker-info-'+latT+'-'+lonT).css('background-color', 'rgba(33,150,243,0.65)');
		    $('html, body').animate({
            scrollTop: $('#marker-info-'+latT+'-'+lonT).offset().top - offset
        }, 300, function(){
            $('#marker-info-'+latT+'-'+lonT).animate({ "background-color": "white" }, 300, "linear");
        });

    });



    markers.push(marker);
    endpoint_for_connection.push(marker.position);
    //console.log("new size: "+markers.length);

    markerCount++;
}

function getPointsOrderedInfo(){
    var points = [];
    var count = 1;
    $("#points-info > div").each(function(){
        var point = {};

        var nome = $("#"+this.id+' .info-nome').val();
        var lat = parseFloat($("#"+this.id+' .info-lat').html());
        var lon = parseFloat($("#"+this.id+' .info-lon').html());

        point.nome = nome;
        point.lat = lat;
        point.lon = lon;
        point.ordem = count++;
        points.push(point);
    });

    //console.log(points);
    return points;
}

`