package ldso.rios;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.User;
import ldso.rios.MainActivities.GuardaRios;
import ldso.rios.MainActivities.Profile;

public class Mapa_rios extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    TextView tvLatlong;
    Marker current_loc;
    Marker select_loc;
    Integer type;


    private boolean isPotentialLongPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(this.getIntent().getSerializableExtra("type")!=null)
            type= (Integer) this.getIntent().getSerializableExtra("type");
        setContentView(R.layout.activity_mapa_rios);
        mLastLocation = null;
        mGoogleApiClient = null;
        select_loc = null;
        current_loc = null;

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
        if(current_loc!=null)
            current_loc.remove();

        LatLng current_location = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        current_loc = mMap.addMarker(new MarkerOptions().position(current_location).title("Localização Actual"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current_location, 12.0f));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                if (select_loc != null) select_loc.remove();
                Log.d("arg0", arg0.latitude + "-" + arg0.longitude);
                LatLng current_location = new LatLng(arg0.latitude, arg0.longitude);
                select_loc = mMap.addMarker(new MarkerOptions().position(current_location).title("Localização escolhida"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(current_location));
            }
        });
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
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                Toast.makeText(Mapa_rios.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
                //searchNearestPlace(voice2text);
            }
            else{
                //This is what you need:
                locationManager.requestLocationUpdates(bestProvider, 1000, 0, (LocationListener) this);
            }
        }
        else
        {
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





    public void goto_next(View view)  {
        //startActivity(new Intent(this, TesteChart.class));
        Intent returnIntent = new Intent();
        if (current_loc!=null) {
            returnIntent.putExtra("latlan_current",current_loc.getPosition().latitude+";"+current_loc.getPosition().longitude);
            /*
            if (DB_functions.haveNetworkConnection(this.getApplicationContext()))
                returnIntent.putExtra("latlan_current", this.getLocationName(current_loc.getPosition()));
            else
                returnIntent.putExtra("latlan_current", current_loc.getPosition().latitude + ";" + current_loc.getPosition().longitude);
                */
        }

        else
            returnIntent.putExtra("latlan_current","0");

        if (select_loc!=null)
        {
            returnIntent.putExtra("latlan_picked",select_loc.getPosition().latitude+";"+current_loc.getPosition().longitude);
            /*
            if (DB_functions.haveNetworkConnection(this.getApplicationContext()))
                returnIntent.putExtra("latlan_picked",this.getLocationName(select_loc.getPosition()));
            else
                returnIntent.putExtra("latlan_picked",select_loc.getPosition().latitude+";"+current_loc.getPosition().longitude);
                */
        }
        else
            returnIntent.putExtra("latlan_picked","0");


        setResult(Activity.RESULT_OK,returnIntent);
        finish();

        if(type==null)
            return;

      //  i.putExtra("latlan_current",current_loc.getPosition().latitude+";"+current_loc.getPosition().longitude);
       // i.putExtra("latlan_picked",select_loc.getPosition().latitude+";"+current_loc.getPosition().longitude);
       // startActivity(new Intent(this, FormIRRSwipe.class));

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
        if(id==R.id.navigate_guardarios)
            startActivity(new Intent(this,GuardaRios.class));
        if(id==R.id.navigate_account){
            if(User.getInstance().getAuthentication_token().contentEquals(""))
                startActivity(new Intent(this, Login.class));
            else {
                startActivity(new Intent(this, Profile.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }





    public String getLocationName(LatLng locations){
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(new Criteria(), true);

        List<String> providerList = locationManager.getAllProviders();
        if(null!=locations && null!=providerList && providerList.size()>0){
            double longitude = locations.longitude;
            double latitude = locations.latitude;
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            try {
                List<Address> listAddresses = geocoder.getFromLocation(latitude, longitude, 1);
                if(null!=listAddresses&&listAddresses.size()>0){
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
