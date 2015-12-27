package ldso.rios;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.DB_functions;
import ldso.rios.MainActivities.GuardaRios;
import ldso.rios.MainActivities.PointRota;
import ldso.rios.MainActivities.Rota;

public class Mapa_Rotas extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    TextView tvLatlong;
    Integer id;
    Rota rota;
    Boolean connected=false;
    Boolean onMapready=false;
    String imagens;

    ArrayList<Marker> pontos;

    Marker markerClicked=null;

    private boolean isPotentialLongPress;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (this.getIntent().getSerializableExtra("id") != null)
            id = (Integer) this.getIntent().getSerializableExtra("id");
        setContentView(R.layout.activity_mapa__rotas);
        mLastLocation = null;
        mGoogleApiClient = null;
        pontos= new ArrayList<Marker>();
        markerClicked=null;

        Log.e("Vai entrar na DB", "DB");

        try {
            if (DB_functions.haveNetworkConnection(getApplicationContext()))
            DB_functions.getRotasList(this, this.id);
            else {
                Toast toast = Toast.makeText(Mapa_Rotas.this, "Sem ligação à Internet", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("erro", "erroDB1");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("erro", "erroDB2");
        }


        if (!isLocationServiceEnabled()) {
            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            this.startActivity(myIntent);
        } else {
            buildGoogleApiClient();

            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            } else
                Toast.makeText(this, "Not connected...", Toast.LENGTH_SHORT).show();


            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    public void route(final String resposta) throws JSONException {
        new Thread()
        {
            public void run()
            {
                Mapa_Rotas.this.runOnUiThread(new Runnable()
                {
                    public void run() {
                        try {
                            JSONObject resposta_json = new JSONObject(resposta);

                            JSONObject rota_json = (JSONObject) resposta_json.get("route");
                            JSONArray arrayImagens=(JSONArray) resposta_json.get("images");
                            imagens=arrayImagens.toString();

                            rota = new Rota(rota_json.getInt("id"),
                                    rota_json.getString("nome"),
                                    rota_json.getString("descricao"),
                                    rota_json.getString("zona"),
                                    rota_json.getString("created_at"),
                                    rota_json.getString("updated_at"),
                                    rota_json.getBoolean("publicada"));

                            Log.e("acabou", "acabou");

                            JSONArray rotas_array = (JSONArray) resposta_json.get("points");

                            for (int i = 0; i < rotas_array.length(); i++) {
                                final JSONObject point_json = rotas_array.getJSONObject(i);
                                rota.addPontos(point_json.getInt("id"),
                                        point_json.getString("nome"),
                                        point_json.getString("descricao"),
                                        Float.parseFloat(point_json.get("lat").toString()),
                                        Float.parseFloat(point_json.get("lon").toString()),
                                        point_json.getInt("ordem"),
                                        point_json.getInt("route_id"));
                            }

                            Log.e("tamanho",rota.getPontos().size()+"");


                            if(connected && onMapready)
                            {
                                // Instantiates a new Polyline object and adds points to define a rectangle
                                PolylineOptions rectOptions = new PolylineOptions();
                                rectOptions.color(Color.GREEN);

// Get back the mutable Polyline

                                for (int i =0;i<rota.getPontos().size();i++)
                                {
                                    Log.e("teste","lat:"+rota.getPontos().get(i).getLat());
                                    LatLng current_location = new LatLng(rota.getPontos().get(i).getLat(), rota.getPontos().get(i).getLon());
                                    //LatLng current_location = new LatLng(0,0);
                                    Marker marker = mMap.addMarker(new MarkerOptions().position(current_location).title(rota.getPontos().get(i).getNome()).snippet("Clique novamente para ver"));
                                    pontos.add(marker);
                                    // pontos.add( mMap.addMarker(new MarkerOptions().position(current_location).title(this.rota.getPontos().get(i).getNome())));
                                    rectOptions.add(current_location);

                                    if(i==0)
                                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current_location, 14.0f));

                                }

                                Polyline polyline = mMap.addPolyline(rectOptions);
                                polyline.setColor(Color.BLUE);
                            }



                        } catch (Exception e){

                        }



                    }
                });
            }
        }.start();


    }


    //maps

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        onMapready=true;



        // Add a marker in Sydney and move the camera

        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
    }

    public boolean isLocationServiceEnabled() {
        LocationManager locationManager = null;
        boolean gps_enabled = false, network_enabled = false;

        if (locationManager == null)
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            //do nothing...
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            //do nothing...
        }

        return gps_enabled || network_enabled;

    }

    public void onResume() {
        super.onResume();
        if (mLastLocation != null && isLocationServiceEnabled()) {
            buildGoogleApiClient();

            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            } else
                Toast.makeText(this, "Not connected...", Toast.LENGTH_SHORT).show();


            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }


    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation != null) {
            Log.e("location", "Latitude: " + String.valueOf(mLastLocation.getLatitude()) + "Longitude: " +
                    String.valueOf(mLastLocation.getLongitude()));
        }



/*
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
            }
        });
        */

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                PointRota p;

                int i;
                for (i =0;i<pontos.size();i++)
                {
                    if (pontos.get(i).equals(marker))
                        break;
                }

                Intent intent= new Intent(Mapa_Rotas.this, RotaPoint_show.class);
                intent.putExtra("nome_rota",rota.getNome());
                intent.putExtra("rota_id",rota.getId());
                intent.putExtra("nome_ponto",rota.getPontos().get(i).getNome());
                intent.putExtra("descricao_ponto",rota.getPontos().get(i).getDescricao());
                intent.putExtra("ordem_ponto",rota.getPontos().get(i).getOrdem());
                intent.putExtra("lat",rota.getPontos().get(i).getLat());
                intent.putExtra("lon",rota.getPontos().get(i).getLon());

                intent.putExtra("imagens",imagens);


                startActivity(intent);




            }
        });



        connected=true;
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection suspended...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "Failed to connect...", Toast.LENGTH_SHORT).show();

    }


    public void current(View view) {

        if (!isLocationServiceEnabled()) {
            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            this.startActivity(myIntent);
        } else {
            buildGoogleApiClient();

            if (mGoogleApiClient != null) {
                mGoogleApiClient.connect();
            } else
                Toast.makeText(this, "Not connected...", Toast.LENGTH_SHORT).show();


            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }


    }


    @TargetApi(Build.VERSION_CODES.M)
    protected void getLocation() {
        LocationManager locationManager = null;
        Criteria criteria = null;
        String bestProvider = null;
        if (isLocationServiceEnabled()) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            //You can still do this if you like, you might get lucky:
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.

                return;
            }
            Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                Log.e("TAG", "GPS is on");
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Toast.makeText(Mapa_Rotas.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
                //searchNearestPlace(voice2text);
            } else {
                //This is what you need:
                locationManager.requestLocationUpdates(bestProvider, 1000, 0, (LocationListener) this);
            }
        } else {
            //prompt user to enable location....
            //.................
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    public void goto_next(View view) {


    }


    //menu action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigate_guardarios)
            startActivity(new Intent(this, GuardaRios.class));
        if (id == R.id.navigate_account)
            startActivity(new Intent(this, Login.class));
        return super.onOptionsItemSelected(item);
    }


    public String getLocationName(LatLng locations) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);

        List<String> providerList = locationManager.getAllProviders();
        if (null != locations && null != providerList && providerList.size() > 0) {
            double longitude = locations.longitude;
            double latitude = locations.latitude;
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (null != listAddresses && listAddresses.size() > 0) {
                    String _Location = listAddresses.get(0).getAddressLine(0);
                    return _Location;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "";
            }

        }
        return "";
    }



}
