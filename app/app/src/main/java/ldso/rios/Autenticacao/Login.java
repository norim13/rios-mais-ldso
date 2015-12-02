package ldso.rios.Autenticacao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import ldso.rios.DataBases.DB_functions;
import ldso.rios.MainActivities.Homepage;
import ldso.rios.R;

import static ldso.rios.MainActivities.Homepage.PREFS_NAME;

public class Login extends AppCompatActivity {

    protected EditText email;
    protected EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(Homepage.PREFS_NAME, 0);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Autenticação");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        email=(EditText)findViewById(R.id.email_login);
        password=(EditText)findViewById(R.id.password_login);
    }

    public void login(View view) throws IOException, JSONException {
        String email_txt=email.getText().toString();
        String password_txt=password.getText().toString();
        DB_functions.login(email_txt, password_txt, this);
    }

    public void register(View view){
        startActivity(new Intent(this, Register.class));
    }

    public void login_response(final Boolean error, final String error_txt, final String authentication, final String nome, final String email) {

        new Thread()
        {
            public void run()
            {
                Login.this.runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        Toast toast;
                        Context context = getApplicationContext();
                        if (error){

                            toast = Toast.makeText(context, ""+error_txt, Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else {
                            toast = Toast.makeText(context, "Bem vindo "+nome, Toast.LENGTH_LONG);
                            toast.show();

                            // We need an Editor object to make preference changes.
                            // All objects are from android.context.Context
                            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("token",authentication);
                            editor.putString("name",nome);
                            editor.putString("email",email);

                            // Commit the edits!
                            editor.commit();
                            finish();
                        }
                    }
                });
            }
        }.start();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}