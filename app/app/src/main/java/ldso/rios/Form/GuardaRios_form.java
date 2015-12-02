package ldso.rios.Form;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.DB_functions;
import ldso.rios.DataBases.User;
import ldso.rios.MainActivities.GuardaRios;
import ldso.rios.MapFfrag;
import ldso.rios.R;

public class GuardaRios_form extends AppCompatActivity {

    LinearLayout linearLayout;
    FrameLayout frameLayout;
    protected ArrayList<RadioButton> question1;
    protected ArrayList<RadioButton> question2;
    protected ArrayList<RadioButton> question3;
    protected ArrayList<RadioButton> question4;
    protected ArrayList<CheckBox> question5;
    protected EditText question6;
    protected ProgressBar progressbar;


    private MapFfrag fragment = null;


    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    TextView tvLatlong;
    Marker current_loc;
    Marker select_loc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guarda_rios_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Avistamento");
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        linearLayout = (LinearLayout) this.findViewById(R.id.irr_linear);
        frameLayout = (FrameLayout) this.findViewById(R.id.frameLayout);
        progressbar = (ProgressBar) this.findViewById(R.id.progressBar);

        LayoutInflater l = getLayoutInflater();
        View v = l.inflate(R.layout.fragment_map_ffrag, null);

        frameLayout.addView(v);

        GoogleMap googleMap;
        googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        if (googleMap == null)
            Log.e("teste", "é null");

       // LatLng current_location = this.getLocation();
       // current_loc = googleMap.addMarker(new MarkerOptions().position(current_location).title("Localização Actual"));

        /*
        MapFfrag map=new MapFfrag();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MapFfrag hello = new MapFfrag();
        fragmentTransaction.add(frameLayout.getId(), hello, "HELLO");
        fragmentTransaction.commit();

*/

        Resources r = getResources();
        float px_float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, r.getDisplayMetrics());
        int px = (int) px_float;
        px_float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, r.getDisplayMetrics());
        int px2 = (int) px_float;


        LinearLayout.LayoutParams radioParams;
        radioParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        radioParams.setMargins(0, px2, 0, px);

        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(radioParams);
        TextView tv1 = new TextView(this);
        tv1.setText("Local onde foi observado?");

        tv1.setLayoutParams(radioParams);
        ll.addView(tv1);
        linearLayout.addView(ll);
        String[] options = {"Árvores/ramo", "Pedra/rocha", "Ninho"};
        question1 = Form_functions.createRadioButtons(options, linearLayout, this);

        TextView tv2 = new TextView(this);
        tv2.setText("Estava a voar?");
        tv2.setLayoutParams(radioParams);
        linearLayout.addView(tv2);
        String[] options2 = {"Não estava a voar", "Pairar", "Voo picado para mergulhar", "Mergulhar", "De passagem"};
        question2 = Form_functions.createRadioButtons(options2, linearLayout, this);

        TextView tv3 = new TextView(this);
        tv3.setText("Estava a cantar?");
        tv3.setLayoutParams(radioParams);
        linearLayout.addView(tv3);
        String[] options3 = {"Não estava a cantar", "Presença", "Alerta e marcação de teritório"};
        question3 = Form_functions.createRadioButtons(options3, linearLayout, this);

        TextView tv4 = new TextView(this);
        tv4.setText("Estava a alimentar-se?");
        tv4.setLayoutParams(radioParams);
        linearLayout.addView(tv4);
        String[] options4 = {"Não estava a alimentar-se", "Peixe", "Libelinha", "Pequenos insectos"};
        question4 = Form_functions.createRadioButtons(options4, linearLayout, this);

        TextView tv5 = new TextView(this);
        tv5.setText("Comportamentos:");
        linearLayout.addView(tv5);
        tv5.setLayoutParams(radioParams);

        String[] options5 = {"Estava parado?",
                "Estava a beber?",
                "Estava a caçar?",
                "Estava a cuidar das crias?"};
        question5 = Form_functions.createCheckboxes(options5, linearLayout, this);

        question6 = new EditText(this);
        question6.setHint("Outro comportamento?");
        linearLayout.addView(question6);


        //saveGuardaRios();


    }


    public void saveGuardaRios(View view) {
        progressbar.setVisibility(View.VISIBLE);
        String q1 = Form_functions.getRadioButtonOption_string(question1);
        String q2 = Form_functions.getRadioButtonOption_string(question2);
        String q3 = Form_functions.getRadioButtonOption_string(question3);
        String q4 = Form_functions.getRadioButtonOption_string(question4);
        ArrayList<Integer> q5 = Form_functions.getCheckboxes(question5);
        String q6 = String.valueOf(question6.getText());

        DB_functions.saveGuardaRios(this, User.getToken(this), q1, q2, q3, q4, q5, q6);
    }

    public void saveGuardaRiosDB() {
        new Thread() {
            public void run() {
                GuardaRios_form.this.runOnUiThread(new Runnable() {
                    public void run() {
                        progressbar.setVisibility(View.INVISIBLE);
                        Toast toast = Toast.makeText(GuardaRios_form.this, "Avistamento submetido", Toast.LENGTH_LONG);
                        toast.show();
                        GuardaRios_form.this.finish();

                    }
                });
            }
        }.start();
    }


    @TargetApi(Build.VERSION_CODES.M)
    public LatLng getLocation() {
        // Get the location manager
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            Log.e("teste","vai sair do if");
            return null;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);
        Double lat,lon;
        try {
            lat = location.getLatitude ();
            lon = location.getLongitude ();
            return new LatLng(lat, lon);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            Log.e("teste","é nulo");
            return null;
        }
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
        if(id==R.id.navigate_account)
            startActivity(new Intent(this,Login.class));
        return super.onOptionsItemSelected(item);
    }

}
