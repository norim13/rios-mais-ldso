//myMap.js
//javascript to create web map displaying Google base map
//with custom overlays from geoserver wms and kml
 
(function () {
  window.onload = function () {
  
       var geographic = new OpenLayers.Projection("EPSG:4326");
        var mercator = new OpenLayers.Projection("EPSG:900913");

        var geographic2 = new OpenLayers.Projection("EPSG:3763");

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
                //layers: "rios:AtAgua_Agsup_rios_AAmb_SNIRH_PC",
                //layers: "rios:AtAgua_Agsup_zhidnetord5km2_PC",
                layers: "rios:netElementL",
                
                transparent: "true",
                format: "image/png"
            }
        );
        map.addLayer(lund);

        //?service=WMS&version=1.1.0&request=GetMap&layers=rios:AtAgua_Agsup_rios_AAmb_SNIRH_PC&styles=&bbox=-116041.26840000041,-294342.9310999997,162111.18240000028,275275.92229999974&width=375&height=768&srs=EPSG:3763&format=application/openlayers
       

    //Map center and zoom
        map.setCenter(lund_center, 14);

    //List of controls    
        map.addControl(new OpenLayers.Control.LayerSwitcher());
        map.addControl(new OpenLayers.Control.KeyboardDefaults());          
//        map.addControl(new new OpenLayers.Control.Navigation());

    //Coordinates of mouse position
        map.addControl(new OpenLayers.Control.MousePosition());   

        var gmap = new OpenLayers.Layer.Google("Google Streets");
        map.addLayer(gmap);


 
  };
 
})();