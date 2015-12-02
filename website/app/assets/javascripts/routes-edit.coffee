# Place all the behaviors and hooks related to the matching controller here.
# All this logic will automatically be available in application.js.
# You can use CoffeeScript in this file: http://coffeescript.org/
`
markers = new Array();
markerCount = 1;
$(document).ready(function(){

		//add points
		var mapCanvas = document.getElementById('add-route-points-map');
		var mapOptions = {
		    center: new google.maps.LatLng(41.179379, -8.606543),
		    zoom: 15,
		    mapTypeId: google.maps.MapTypeId.ROADMAP
		};
		var map = new google.maps.Map(mapCanvas, mapOptions);

    google.maps.event.addListener(map, 'click', function(event) {
        placeMarker(event.latLng, map);
    });

    $( "#route-points-sortable" ).sortable({
        placeholder: "ui-state-highlight"
    });
    $( "#route-points-sortable" ).disableSelection();

		$("#test-order-points").click(getPointsOrdered);

    $(document).on('input', 'input[name="nome"]', function(){
        var class_nomes = this.id.replace('input-nome','nome-ponto');
        var new_name = $(this).val();
        $("."+class_nomes).each(function(){
		        //console.log(this);
            $(this).html(new_name);
        });
    });

    $("#submit-rota").on('click', function(e){
        //e.preventDefault();
        console.log($('form input[name="utf8"]').val());
        var route = {};
        route.nome = $("form #nome-rota").val();
        route.descricao = $("form #descricao-rota").val();
        route.zona = $("form #zona-rota").val();
        route.publicada = $("form #publicada-rota").val();



        var obj = {};
        obj.route = route;
        obj.authenticity_token = $('form input[name="authenticity_token"]').val();
        obj.rota_points = getPointsOrdered();
        console.log(JSON.stringify(obj));

        $.ajax({
            url: '/routes',
            type: 'POST',
            dataType: 'json',
            data: obj,
            success: function(data){
                console.log(data);
                window.location.href = "/routes";
            },
            error: function(err){
                console.log(err);
            }
        });
    });
});

function placeMarker(location, map) {
    var marker = new google.maps.Marker({
        position: location,
        map: map
    });

    google.maps.event.addListener(marker, 'click', function(event) {
        removeMarker(map, marker);
    });

		markers.push(marker);
    //console.log("new size: "+markers.length);

		var latT = String(marker.getPosition().lat()).replace(/\./g,'_');
    var lonT = String(marker.getPosition().lng()).replace(/\./g,'_');
    var partialId = latT + '-' + lonT;

		var formId = addMarkerForm(marker, partialId);
    addMarkerSortable(marker, partialId);
    $('html, body').animate({
        scrollTop: $("#"+formId).offset().top
    }, 1000);
		markerCount++;
}

function removeMarker(map, marker) {

    var x=window.confirm("Tem a certeza que quer remover o ponto?")
    if (x){
        var latT = String(marker.getPosition().lat()).replace(/\./g,'_');
        var lonT = String(marker.getPosition().lng()).replace(/\./g,'_');
        var partialId = latT + '-' + lonT;

        for (i = 0; i < markers.length; i++) {
            if (markers[i] === marker){
                //console.log(markers[i].getPosition().lat());
                markers[i].setMap(null);
                markers.splice(i, 1);
                removeMarkerForm(partialId);
                removeMarkerSortable(partialId);
                break;
            }
        }
        //console.log("new size: "+markers.length);
    }
}

function addMarkerForm(marker, partialId){
		var id = 'marker-form-'+partialId;
		$("#left-panel-forms").append(
			'<div id="'+id+'" class="thumbnail">'+ //id = marker-form-{latitude}-{longitude}
				'<div class="row">'+
					//'<form class="form form-inline">'+
						'<div class="col-xs-12">'+
							'<h4><span class="nome-ponto-'+partialId+'">Ponto '+markerCount+'</span></h4>'+
						'</div>'+
						'<div class="col-md-4">'+
							'Nome:<input id="input-nome-'+partialId+'" type="text-field" name="nome" class="form-control" value="Ponto '+markerCount+'"/>'+
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
					//'</form>'+
				'</div>'+
			'</div>'
		);
		return id;
}

function addMarkerSortable(marker, partialId){
    var id = 'marker-sortable-'+partialId;
    $("#route-points-sortable").append(
      '<li id="'+id+'" class="ui-state-default"> <span class="glyphicon glyphicon-resize-vertical"></span> <span class="nome-ponto-'+partialId+'">Ponto '+markerCount+'</span></li>' //id = marker-sortable-{latitude}-{longitude}
    );
		return id;
}

function removeMarkerForm(partialId){
	$("#marker-form-"+partialId).remove();
}

function removeMarkerSortable(partialId){
    $("#marker-sortable-"+partialId).remove();
}

function getPointsOrdered(){
    var points = [];
    var count = 1;
		$("#route-points-sortable > li").each(function(){
				var point = {};
				var this_id_parts = this.id.split('-');

				//form id. this confuse code handles the fact that negative coordinates could interfeer with the separator (-) used in the id
				var form_id = 'marker-form-';
				var i = 2;
        if(this_id_parts[i] == ""){
            i++;
            form_id = form_id + '-';
        }
				form_id = form_id + this_id_parts[i++] + '-';
        if(this_id_parts[i] == ""){
            i++;
		        form_id = form_id + '-';
        }
        form_id = form_id + this_id_parts[i++];
				//console.log(form_id);
				///////////////////////////////////////////////////////////////

				var nome = $("#"+form_id+' input[name="nome"]').val();
				var descricao = $("#"+form_id+' textarea[name="descricao"]').val();
        var lat = $("#"+form_id+' input[name="lat"]').val();
        var lon = $("#"+form_id+' input[name="lon"]').val();

				point.nome = nome;
				point.descricao = descricao;
				point.lat = lat;
				point.lon = lon;
        point.ordem = count++;

        //var obj = {};
        //obj.point = point;
        points.push(point);
    });

		//console.log(points);
    return points;
}
`