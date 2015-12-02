# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/
`
$(document).ready(function(){

		var mapCanvas = document.getElementById('view-route-points-map');
		var mapOptions = {
		    center: new google.maps.LatLng(41.179379, -8.606543),
		    zoom: 15,
		    mapTypeId: google.maps.MapTypeId.ROADMAP
		};
		var map = new google.maps.Map(mapCanvas, mapOptions);

});

`