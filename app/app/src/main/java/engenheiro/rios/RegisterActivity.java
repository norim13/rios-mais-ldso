package engenheiro.rios;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import engenheiro.rios.DataBases.User;

public class RegisterActivity extends AppCompatActivity {

    protected EditText mUsername;
    protected EditText mEmail;
    protected EditText mPassword;
    protected Button mRegisterButton;
    protected User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //init
        mUsername=(EditText)findViewById(R.id.UserNameRegisterEditText);
        mEmail=(EditText)findViewById(R.id.EmailRegisterEditText);
        mPassword=(EditText)findViewById(R.id.PasswordRegisterEditText);
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
        String email=mEmail.getText().toString().trim();
        user.setName(username);
        user.setEmail(password);
        user.setPassword(email);
        user.save();

        user.getList();


        //Toast.makeText(RegisterActivity.this,username, Toast.LENGTH_LONG).show();

       // Toast.makeText(RegisterActivity.this, ""+user.getList(this).size(),Toast.LENGTH_LONG).show();



        //set user

    }



}
