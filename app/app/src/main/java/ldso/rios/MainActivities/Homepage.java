package ldso.rios.MainActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONException;

import java.io.IOException;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.User;
import ldso.rios.Form.Sos_rios;
import ldso.rios.Mapa_rios;
import ldso.rios.R;

public class Homepage extends AppCompatActivity{

    private Bundle savedInstanceState;
    public static final String PREFS_NAME = "UserInfo";
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Inicio");
        setSupportActionBar(toolbar);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token=settings.getString("token","-1");
        Log.e("token", ": " + token);


    }

    User current_user;


    public void mapas_init(View view){
        startActivity(new Intent(this, Mapa_rios.class));
        //startActivity(new Intent(this, GuardaRios_form.class));

    }

    public void sosRios(View view){
        startActivity(new Intent(this, Sos_rios.class));

    }

    public void form_irr(View view) throws IOException, JSONException {
        startActivity(new Intent(this, Form_IRR_mainActivity.class));
    }

    public void limpeza(View view){
        startActivity(new Intent(this, Limpeza.class));
    }

    public void about(View view)  {
        //startActivity(new Intent(this, TesteChart.class));
        startActivity(new Intent(this, Information.class));
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }



}
