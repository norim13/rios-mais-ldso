package engenheiro.rios;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import engenheiro.rios.DataBases.DB_functions;
import engenheiro.rios.DataBases.User;
import engenheiro.rios.IRR.IRR_question;
public class Homepage extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private Bundle savedInstanceState;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    TextView tvLatlong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        tvLatlong = (TextView) findViewById(R.id.gps);

        buildGoogleApiClient();

        if(mGoogleApiClient!= null){
            mGoogleApiClient.connect();
        }
        else
            Toast.makeText(this, "Not connected...", Toast.LENGTH_SHORT).show();


    }

    User current_user;
    public static final String PREFS_NAME = "UserInfo";
    String token;

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        Toast.makeText(this, "Failed to connect...", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnected(Bundle arg0)  {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.homepage_title);
        setSupportActionBar(toolbar);

        current_user = new User();
        tvLatlong = (TextView) this.findViewById(R.id.gps);

        // Restore preferences
        token = "";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token = settings.getString("token", "-1");
        Log.e("token", ": " + token);
        try {
            DB_functions.saveForm();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation != null) {
            tvLatlong.setText("Latitude: "+ String.valueOf(mLastLocation.getLatitude())+"Longitude: "+
                    String.valueOf(mLastLocation.getLongitude()));
        }




    }

    @Override
    public void onConnectionSuspended(int arg0) {
        Toast.makeText(this, "Connection suspended...", Toast.LENGTH_SHORT).show();

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }

    public void mapas_init(View view){
        startActivity(new Intent(this, Mapa_rios.class));
    }

    public void fomr_irr(View view) throws IOException, JSONException {
        HashMap<Integer,Object> answers2=new HashMap<Integer,Object>();
        Intent i=new Intent(this, IRR_question.class);
        i.putExtra("main_title","Hidrogeomorfologia");
        i.putExtra("sub_title", "Tipo de Vale");
        ArrayList<ArrayList<Object>> al= new ArrayList<>();
        i.putExtra("answers",al);
        i.putExtra("answers2",answers2);
        i.putExtra("type",0);
        i.putExtra("required", true);
        String[] options={"1","2","3","4","5","6","7"};
        i.putExtra("options",options);
        i.putExtra("question_num", 1);
        startActivity(i);
        //startActivity(new Intent(this, IRR_question.class));
        //DB_functions.saveForm();
    }



    public void about(View view)  {
        startActivity(new Intent(this, Information.class));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {
            return true;
        }
*/


        if(id==R.id.navigate_guardarios){
            startActivity(new Intent(this,GuardaRios.class));
        }

        if(id==R.id.navigate_account){
            startActivity(new Intent(this,Login.class));

        }


        return super.onOptionsItemSelected(item);
    }

}
