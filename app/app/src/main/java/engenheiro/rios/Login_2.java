package engenheiro.rios;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import engenheiro.rios.DataBases.DB_functions;

import static engenheiro.rios.Homepage.PREFS_NAME;

public class Login_2 extends AppCompatActivity {

    protected EditText email;
    protected EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

    public void login_response(final Boolean error, final String error_txt, final String authentication, final String nome, String email) {


        new Thread()
        {
            public void run()
            {
                Login_2.this.runOnUiThread(new Runnable()
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

                            // Commit the edits!
                            editor.commit();
                        }
                    }
                });
            }
        }.start();


    }
}
