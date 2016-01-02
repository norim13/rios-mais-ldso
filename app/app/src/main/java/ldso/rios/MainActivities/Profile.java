package ldso.rios.MainActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ldso.rios.DataBases.DB_functions;
import ldso.rios.DataBases.User;
import ldso.rios.R;

import static ldso.rios.MainActivities.Homepage.PREFS_NAME;

public class Profile extends AppCompatActivity {

    TextView name;
    TextView email;
    TextView distrito;
    TextView concelho;
    TextView telef;
    TextView formacao;
    TextView profissao;
    TextView habilitacoes;

    TextView perm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Perfil");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplication(), ProfileEditActivity.class),1);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //progressBar.setIndeterminate(true);
        updateProfileView();

        if (DB_functions.haveNetworkConnection(getApplicationContext()))
        DB_functions.getUserData(this, User.getInstance().getEmail(),User.getInstance().getAuthentication_token());
        else {
            Toast toast = Toast.makeText(Profile.this, "Sem ligação à Internet", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //matches the result code passed from ChildActivity
        if(resultCode == 0)
        {
            //kill self
            Profile.this.finish();
        }
    }

    public void logout(View view) {
        this.getApplicationContext().getSharedPreferences(PREFS_NAME, 0).edit().clear().commit();
        User.getInstance().resetUser();
        this.finish();

        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, "Até mais logo!", Toast.LENGTH_LONG);
        toast.show();
    }

    private void updateProfileView() {
        User u = User.getInstance();

        name = (TextView) findViewById(R.id.nameTextProfile);
        name.setText(u.getName());

        perm = (TextView) findViewById(R.id.permissoesTextProfile);
        perm.setText("Permissões: "+ u.getPermissoes());

        email = (TextView) findViewById(R.id.emailTextProfile);
        email.setText(u.getEmail());

        distrito = (TextView) findViewById(R.id.distritoTextProfile);
        distrito.setText(u.getDistrito());

        concelho = (TextView) findViewById(R.id.concelhoTextProfile);
        concelho.setText(u.getConcelho());

        telef = (TextView) findViewById(R.id.telefTextProfile);
        telef.setText(u.getTelef());

        formacao = (TextView) findViewById(R.id.formacaoTextProfile);
        formacao.setText(u.getFormacao()? "Sim": "Não");

        profissao = (TextView) findViewById(R.id.profissaoTextProfile);
        profissao.setText(u.getProfissao());

        habilitacoes = (TextView) findViewById(R.id.habilitacoesTextProfile);
        habilitacoes.setText(u.getHabilitacoes());
    }

    public void afterGetUserData(){
        new Thread()
        {
            public void run()
            {
                Profile.this.runOnUiThread(new Runnable() {
                    public void run() {
                        User u = User.getInstance();

                        if(!u.getName().equals(name.getText()) ||
                                !u.getConcelho().equals(concelho.getText()) ||
                                !u.getDistrito().equals(distrito.getText()) ||
                                !u.getTelef().equals(telef.getText()) ||
                                !u.getHabilitacoes().equals(habilitacoes.getText()) ||
                                !u.getProfissao().equals(profissao.getText()) ||
                                !String.valueOf(u.getFormacao()).equals(formacao.getText()) ||
                                !u.getEmail().equals(email.getText())) {

                            updateProfileView();
                            updateSharedPreferencesUser();
                            Log.e("profile","dados diferentes!");
                        }
                    }
                });
            }
        }.start();
    }

    public void updateSharedPreferencesUser() {
        User u = User.getInstance();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("id",String.valueOf(u.getId()));
        editor.putString("token",u.getAuthentication_token());
        editor.putString("name",u.getName());
        editor.putString("email",u.getEmail());

        editor.putString("telef",u.getTelef());
        editor.putString("profissao",u.getProfissao());
        editor.putString("habilitacoes",u.getHabilitacoes());
        editor.putString("formacao", String.valueOf(u.getFormacao()));
        editor.putString("distrito",u.getDistrito());
        editor.putString("concelho",u.getConcelho());

        editor.putString("permissoes",u.getPermissoes().toString());

        // Commit the edits!
        editor.commit();
    }
}
