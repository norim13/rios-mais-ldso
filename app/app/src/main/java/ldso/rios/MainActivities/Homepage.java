package ldso.rios.MainActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.User;
import ldso.rios.Form.Sos_rios;
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
        toolbar.setTitle("Início");
        setSupportActionBar(toolbar);

        setUser();
    }

    /**
     * Faz set do user. Se tiver guardado, faz set dessas informacoes, se nao poe default
     */
    private void setUser() {
        User u = User.getInstance();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        token = settings.getString("token","-1");

        u.setId(Integer.parseInt(settings.getString("id","-1")));
        u.setAuthentication_token(settings.getString("token",""));
        u.setName(settings.getString("name",""));
        u.setEmail(settings.getString("email",""));

        u.setTelef(settings.getString("telef",""));
        u.setProfissao(settings.getString("profissao",""));
        u.setHabilitacoes(settings.getString("habilitacoes",""));
        u.setFormacao(Boolean.parseBoolean(settings.getString("formacao","false")));
        u.setDistrito(settings.getString("distrito","1"));
        u.setConcelho(settings.getString("concelho","1"));

        u.setPermissoes(Integer.parseInt(settings.getString("permissoes","0")));
    }

    /**
     * Abre a activity do guardarios
     * @param view
     */
    public void rotasRios(View view){
        startActivity(new Intent(this, RotasRios_list.class));
    }

    /**
     * Abre a activity do SOSRios
     * @param view
     */
    public void sosRios(View view){
        startActivity(new Intent(this, Sos_rios.class));
    }

    /**
     * Abre a lista de FormsIRR com os guardados e os uploaded
     * @param view
     * @throws IOException
     * @throws JSONException
     */
    public void form_irr(View view) throws IOException, JSONException {
        if(User.getInstance().getPermissoes() > 1)
            startActivity(new Intent(this, Form_IRR_mainActivity.class));
        else {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Não tem permissões suficientes", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    /**
     * Abre a activity de Limpezas
     * @param view
     */
    public void limpeza(View view){
        startActivity(new Intent(this, Limpeza.class));
    }

    /**
     * Abre a activity de informação
     * @param view
     */
    public void about(View view)  {
        startActivity(new Intent(this, Information.class));
    }

    //--TOOLBAR
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.navigate_guardarios)
            startActivity(new Intent(this,GuardaRios.class));

        if(id==R.id.navigate_account) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            token = settings.getString("token","-1");
            String email = settings.getString("email","-1");

            if(token.equals("-1"))
                startActivity(new Intent(this, Login.class));
            else {
                User u = User.getInstance();
                u.setEmail(email);
                u.setAuthentication_token(token);
                startActivity(new Intent(this, Profile.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);
        return true;
    }
    //--TOOLBAR
}
