package engenheiro.rios;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import engenheiro.rios.DataBases.DB_functions;
import engenheiro.rios.DataBases.User;

public class Register extends AppCompatActivity {


    protected EditText mUsername;
    protected EditText mEmail;
    protected EditText mPassword;
    protected EditText mPasswordConfirm;
    protected Button mRegisterButton;
    protected User user;
    private static final int REQUEST_READ_CONTACTS = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Registo");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //init
        mUsername=(EditText)findViewById(R.id.UserNameRegisterEditText);
        mEmail=(EditText)findViewById(R.id.EmailRegisterEditText);
        mPassword=(EditText)findViewById(R.id.PasswordRegisterEditText);
        mPasswordConfirm=(EditText)findViewById(R.id.PasswordConfirmRegisterEditText);
        mRegisterButton=(Button)findViewById(R.id.RegisterButton);
        user=new User();
        user.setId(-1);
        user.setName("teste");
        user.setEmail("teste@email.com");
        user.setPassword("teste");



    }

    public void register(View view){
        //toast
        //Toast.makeText(RegisterActivity.this,"Registando",Toast.LENGTH_LONG).show();

        //get form
        String username=mUsername.getText().toString().trim();
        String password=mPassword.getText().toString().trim();
        String password_confirm=mPasswordConfirm.getText().toString().trim();
        String email=mEmail.getText().toString().trim();
        boolean erro=false;
        if(username.length()==0){
            mUsername.setError("Preencha o campo");
            erro=true;
        }
        if( ! android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ){
            mEmail.setError("Endereço de email incorreto");
            erro=true;
        }
        if(password.length()<8){
            mPassword.setError("Tem de ter mais de 8 caracteres");
            erro=true;
        }
        if(!password_confirm.equals(password )){
            mPassword.setError("Password nao cooresponde");
            erro=true;
        }
        if (erro)return;

        Log.v("teste","registo"+password_confirm+"!"+password);

/*
        user.setName(username);
        user.setEmail(password);
        user.setPassword(email);
        user.save();

        user.getList();
        */

        try {
            DB_functions.saveUser(username,email,password,password_confirm);
        } catch (IOException e) {
            Toast.makeText(Register.this, "Erro ao registar utilizador", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (JSONException e) {
            Toast.makeText(Register.this, "Erro ao registar utilizador", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        Intent intent = new Intent(getApplicationContext(), Homepage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        //Toast.makeText(RegisterActivity.this,username, Toast.LENGTH_LONG).show();

        // Toast.makeText(RegisterActivity.this, ""+user.getList(this).size(),Toast.LENGTH_LONG).show();



        //set user

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
