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
import android.widget.ArrayAdapter;
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

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.permissoes_array, android.R.layout.simple_spinner_item);
        String permS = (String) adapter2.getItem(u.getPermissoes()-1);
        perm.setText("Permissões: " + permS);

        email = (TextView) findViewById(R.id.emailTextProfile);
        email.setText(u.getEmail());

        distrito = (TextView) findViewById(R.id.distritoTextProfile);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.distritos_array, android.R.layout.simple_spinner_item);
        distrito.setText(adapter.getItem(Integer.parseInt(u.getDistrito()) - 1));

        concelho = (TextView) findViewById(R.id.concelhoTextProfile);
        concelho.setText(getConcelhoByDistritoAndConcelhoId(Integer.parseInt(u.getDistrito()), Integer.parseInt(u.getConcelho())));

        telef = (TextView) findViewById(R.id.telefTextProfile);
        telef.setText(u.getTelef());

        formacao = (TextView) findViewById(R.id.formacaoTextProfile);
        formacao.setText(u.getFormacao()? "Sim": "Não");

        profissao = (TextView) findViewById(R.id.profissaoTextProfile);
        profissao.setText(u.getProfissao());

        habilitacoes = (TextView) findViewById(R.id.habilitacoesTextProfile);
        habilitacoes.setText(u.getHabilitacoes());
    }

    public String getConcelhoByDistritoAndConcelhoId(int distritoId, int concelhoId){
        ArrayAdapter<CharSequence> unit_adapter = null;
        switch (distritoId-1) {
            case 0:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.AveiroConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 1:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.BejaConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 2:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.BragaConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 3:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.BragancaConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 4:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.CasteloBrancoConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 5:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.CoimbraConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 6:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.EvoraConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 7:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.FaroConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 8:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.GuardaConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 9:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.LeiriaConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 10:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.LisboaConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 11:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.PortalegreConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 12:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.PortoConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 13:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.SantaremConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 14:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.SetubalConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 15:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.VianaDoCasteloConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 16:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.VilaRealConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 17:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.ViseuConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 18:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.IlhaDaMadeiraConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 19:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.IlhaDePortoSantoConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 20:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.IlhaDeSantaMariaConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 21:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.IlhaDeSaoMiguelConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 22:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.IlhaTerceiraConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 23:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.IlhaDaGraciosaConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 24:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.IlhaDeSaoJorgeConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 25:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.IlhaDoPicoConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 26:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.IlhaDoFaialConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 27:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.IlhaDasFloresConcelhos, android.R.layout.simple_spinner_item);
                break;
            case 28:
                unit_adapter = ArrayAdapter.createFromResource(
                        this, R.array.IlhaDoCorvoConcelhos, android.R.layout.simple_spinner_item);
                break;
            default:
                break;
        }
        int[] primeiroConcelhoId = {1,20,34,48,60,71,88,102,118,132,148,164,179,197,218,231,
                241,255,279,289,290,291,296,298,299,301,304,305,307};
        int index = concelhoId - primeiroConcelhoId[distritoId-1];
        String concelhoValue = (String) unit_adapter.getItem(index);
        return concelhoValue;
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
