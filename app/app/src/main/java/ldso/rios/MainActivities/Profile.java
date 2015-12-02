package ldso.rios.MainActivities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import ldso.rios.DataBases.DB_functions;
import ldso.rios.DataBases.User;
import ldso.rios.R;

public class Profile extends AppCompatActivity {

    TextView name;
    TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        putOldDataOnContent();

        DB_functions.getUserData(this, User.getInstance().getEmail(),User.getInstance().getAuthentication_token());
    }

    private void putOldDataOnContent() {
        User u = User.getInstance();

        name = (TextView) findViewById(R.id.nameTextProfile);
        name.setText(u.getName());

        email = (TextView) findViewById(R.id.emailTextProfile);
        email.setText(u.getEmail());
    }

    public void afterGetUserData(){
        new Thread()
        {
            public void run()
            {
                Profile.this.runOnUiThread(new Runnable() {
                    public void run() {
                        User u = User.getInstance();

                        name = (TextView) findViewById(R.id.nameTextProfile);
                        name.setText(u.getName());

                        email = (TextView) findViewById(R.id.emailTextProfile);
                        email.setText(u.getEmail());

                        Log.e("profile", "apos mudar tudo");
                    }
                });
            }
        }.start();
    }
}
