package ldso.rios.Autenticacao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ldso.rios.DataBases.DB_functions;
import ldso.rios.DataBases.User;
import ldso.rios.MainActivities.Homepage;
import ldso.rios.R;

import static ldso.rios.MainActivities.Homepage.PREFS_NAME;

public class Register extends AppCompatActivity {

    protected EditText mUsername;
    protected EditText mEmail;
    protected EditText mPassword;
    protected EditText mPasswordConfirm;

    protected EditText mTelefone;
    protected EditText mProfissao;
    protected EditText mHabilitacoes;
    protected Switch mFormacao;
    protected Spinner distritoSpinner;
    protected Spinner concelhoSpinner;

    protected Button mRegisterButton;

    protected int distritoId;
    protected int concelhoId;
    protected int[] primeiroConcelhoId = {1,20,34,48,60,71,88,102,118,132,148,164,179,197,218,231,
            241,255,279,289,290,291,296,298,299,301,304,305,307};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Registo");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //init
        mUsername=(EditText)findViewById(R.id.UserNameRegisterEditText);
        mEmail=(EditText)findViewById(R.id.EmailRegisterEditText);
        mPassword=(EditText)findViewById(R.id.PasswordRegisterEditText);
        mPasswordConfirm=(EditText)findViewById(R.id.PasswordConfirmRegisterEditText);

        distritoSpinner = (Spinner)findViewById(R.id.DistritoSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.distritos_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        distritoSpinner.setAdapter(adapter);

        final Context cont = this;

        concelhoSpinner = (Spinner)findViewById(R.id.ConcelhoSpinner);
        distritoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos, pos2;
                pos = distritoSpinner.getSelectedItemPosition();
                int iden = parent.getId();
                if (iden == R.id.DistritoSpinner) {
                    ArrayAdapter<CharSequence> unit_adapter = null;
                    pos2 = 0;
                    distritoId = pos + 1;
                    switch (pos) {
                        case 0:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.AveiroConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 1:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.BejaConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 2:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.BragaConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 3:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.BragancaConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 4:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.CasteloBrancoConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 5:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.CoimbraConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 6:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.EvoraConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 7:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.FaroConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 8:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.GuardaConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 9:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.LeiriaConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 10:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.LisboaConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 11:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.PortalegreConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 12:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.PortoConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 13:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.SantaremConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 14:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.SetubalConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 15:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.VianaDoCasteloConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 16:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.VilaRealConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 17:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.ViseuConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 18:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.IlhaDaMadeiraConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 19:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.IlhaDePortoSantoConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 20:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.IlhaDeSantaMariaConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 21:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.IlhaDeSaoMiguelConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 22:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.IlhaTerceiraConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 23:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.IlhaDaGraciosaConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 24:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.IlhaDeSaoJorgeConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 25:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.IlhaDoPicoConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 26:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.IlhaDoFaialConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 27:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.IlhaDasFloresConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        case 28:
                            unit_adapter = ArrayAdapter.createFromResource(
                                    cont, R.array.IlhaDoCorvoConcelhos, android.R.layout.simple_spinner_item);
                            break;
                        default:
                            break;
                    }

                    unit_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    concelhoSpinner.setAdapter(unit_adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });


        concelhoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos;
                pos = concelhoSpinner.getSelectedItemPosition();
                concelhoId = primeiroConcelhoId[distritoId-1]+pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });

        mTelefone=(EditText)findViewById(R.id.TelefoneRegisterEditText);
        mHabilitacoes=(EditText)findViewById(R.id.HabilitacoesRegisterEditText);
        mProfissao=(EditText)findViewById(R.id.ProfissaoRegisterEditText);
        mFormacao=(Switch) findViewById(R.id.FormacaoRegisterSwitch);

        mRegisterButton=(Button)findViewById(R.id.RegisterButton);
    }

    public void register(View view){
        //get form
        String username=mUsername.getText().toString().trim();
        String password=mPassword.getText().toString().trim();
        String password_confirm=mPasswordConfirm.getText().toString().trim();
        String email=mEmail.getText().toString().trim();

        String telef=mTelefone.getText().toString().trim();
        String profissao=mProfissao.getText().toString().trim();
        String habilitacoes=mHabilitacoes.getText().toString().trim();
        Boolean formacao=mFormacao.isChecked();

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
            mPassword.setError("Password nao corresponde");
            erro=true;
        }
        if(telef.length()!=9){
            mTelefone.setError("Tem de ter 9 caracteres");
            erro=true;
        }
        if (erro) return;
        try {
            if (DB_functions.haveNetworkConnection(getApplicationContext())) {
                DB_functions.saveUser(username, email, password, password_confirm, telef, profissao, habilitacoes, formacao, distritoId, concelhoId, this);
                Intent intent = new Intent(getApplicationContext(), Homepage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            else
            {
                Toast toast = Toast.makeText(Register.this, "Sem ligação à Internet", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (IOException e) {
            Toast.makeText(Register.this, "Erro ao registar utilizador", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (JSONException e) {
            Toast.makeText(Register.this, "Erro ao registar utilizador", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }


    public void register_response(final Boolean error, final String error_txt, final Register reg, final JSONObject obj) {

        new Thread()
        {
            public void run()
            {
                Register.this.runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        Toast toast;
                        Context context = getApplicationContext();
                        if (error){
                            toast = Toast.makeText(context, error_txt, Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else {
                            toast = Toast.makeText(context, "Registo bem sucedido ", Toast.LENGTH_LONG);
                            toast.show();

                            try {
                                login_response_after_register(error,error_txt,obj.get("authentication_token").toString(),obj.get("nome").toString(),obj.get("email").toString(),obj.get("permissoes").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }.start();
    }

    public void login_response_after_register(final Boolean error, final String error_txt, final String authentication, final String nome, final String email, final String permissoes) {

        new Thread()
        {
            public void run()
            {
                Register.this.runOnUiThread(new Runnable()
                {
                    public void run()
                    {
                        Toast toast;
                        Context context = getApplicationContext();
                        if (error){
                            toast = Toast.makeText(context, error_txt, Toast.LENGTH_LONG);
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
                            editor.putString("permissoes",permissoes);

                            // Commit the edits!
                            editor.commit();
                            finish();

                            User.getInstance().setAuthentication_token(authentication);
                            User.getInstance().setName(nome);
                            User.getInstance().setEmail(email);
                        }
                    }
                });
            }
        }.start();
    }
}
