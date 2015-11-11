package engenheiro.rios;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONException;

import java.io.IOException;

import engenheiro.rios.DataBases.DB_functions;
import engenheiro.rios.DataBases.User;
import engenheiro.rios.IRR.IRR_1_1;

public class Homepage extends AppCompatActivity {

    User current_user;
    public static final String PREFS_NAME = "UserInfo";
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.homepage_title);
        setSupportActionBar(toolbar);

        current_user = new User();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Restore preferences
        token="";
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token=settings.getString("token","-1");
        Log.e("token", ": " + token);
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
        startActivity(new Intent(this, IRR_1_1.class));
        DB_functions.saveForm();
    }



    public void about(View view)  {
        startActivity(new Intent(this, About.class));
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
            startActivity(new Intent(this,Login_2.class));

        }


        return super.onOptionsItemSelected(item);
    }
}
