package ldso.rios.MainActivities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import ldso.rios.DataBases.DB_functions;
import ldso.rios.DataBases.User;
import ldso.rios.R;

import static ldso.rios.MainActivities.Homepage.PREFS_NAME;

public class ProfileEditActivity extends AppCompatActivity {
    protected EditText name;

    protected EditText email;
    protected EditText password;
    protected EditText passwordConfirmation;
    protected EditText telef;
    protected EditText habilitacoes;
    protected EditText profissao;
    protected Switch formacao;
    private String current_password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Editar Perfil");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        updateEditProfileView();
    }

    private void updateEditProfileView() {
        User u = User.getInstance();

        name = (EditText) findViewById(R.id.nameProfileEditText);
        name.setText(u.getName());

        email = (EditText) findViewById(R.id.emailProfileEditText);
        email.setText(u.getEmail());

        password = (EditText) findViewById(R.id.passwordProfileEditText);
        passwordConfirmation = (EditText) findViewById(R.id.passwordConfirmationProfileEditText);

        telef = (EditText) findViewById(R.id.telefProfileEditText);
        telef.setText(u.getTelef());

        habilitacoes = (EditText) findViewById(R.id.habilitacoesProfileEditText);
        habilitacoes.setText(u.getHabilitacoes());

        profissao = (EditText) findViewById(R.id.profissaoProfileEditText);
        profissao.setText(u.getProfissao());

        formacao = (Switch) findViewById(R.id.formacaoProfileEditSwitch);
        formacao.setChecked(u.getFormacao());
    }

    public void editProfile(View view) {
        //get form
        String username = name.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String pass_confirm = passwordConfirmation.getText().toString().trim();
        String mEmail= email.getText().toString().trim();
        String mTelef= telef.getText().toString().trim();

        Boolean mFormacao = formacao.isChecked();

        boolean erro=false;
        if(username.length()==0){
            name.setError("Preencha o campo");
            erro=true;
        }
        if( ! android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail).matches() ){
            email.setError("Endereço de email incorreto");
            erro=true;
        }
        if(pass.length() != 0 && pass.length()<8){
            password.setError("Tem de ter mais de 8 caracteres");
            erro=true;
        }
        if(!pass_confirm.equals(pass)){
            password.setError("Password nao corresponde");
            erro=true;
        }
        if(mTelef.length()!=9){
            telef.setError("Tem de ter 9 caracteres");
            erro=true;
        }
        if (erro) return;

        showInputPasswordDialog(this);
        //DB_functions.editUser(this, User.getInstance().getEmail(),User.getInstance().getAuthentication_token());
    }

    public void showInputPasswordDialog(final ProfileEditActivity profileEditActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirme a password atual para confirmar a edição");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input.setHint("Password Atual");
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                current_password = input.getText().toString();
                DB_functions.editUser(profileEditActivity, User.getInstance().getEmail(),User.getInstance().getAuthentication_token());
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /*
     * GETTERS AND SETTERS
     */

    public EditText getName() {
        return name;
    }

    public void setName(EditText name) {
        this.name = name;
    }

    public EditText getEmail() {
        return email;
    }

    public void setEmail(EditText email) {
        this.email = email;
    }

    public EditText getPassword() {
        return password;
    }

    public void setPassword(EditText password) {
        this.password = password;
    }

    public EditText getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(EditText passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public EditText getTelef() {
        return telef;
    }

    public void setTelef(EditText telef) {
        this.telef = telef;
    }

    public EditText getHabilitacoes() {
        return habilitacoes;
    }

    public void setHabilitacoes(EditText habilitacoes) {
        this.habilitacoes = habilitacoes;
    }

    public EditText getProfissao() {
        return profissao;
    }

    public void setProfissao(EditText profissao) {
        this.profissao = profissao;
    }

    public Switch getFormacao() {
        return formacao;
    }

    public void setFormacao(Switch formacao) {
        this.formacao = formacao;
    }

    public void afterDeletingUser(final Boolean error, final String error_txt) {

        new Thread()
        {
            public void run()
            {
                ProfileEditActivity.this.runOnUiThread(new Runnable()
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
                            getApplicationContext().getSharedPreferences(PREFS_NAME, 0).edit().clear().commit();
                            User.getInstance().resetUser();
                            finish();

                            toast = Toast.makeText(context, "Apagou a sua conta", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });
            }
        }.start();
    }

    public void edit_response(final Boolean error, final String error_txt) {

        new Thread()
        {
            public void run()
            {
                ProfileEditActivity.this.runOnUiThread(new Runnable()
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
                            toast = Toast.makeText(context, "Editou com sucesso", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                });
            }
        }.start();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        this.setResult(0);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.setResult(0);
    }

    public String getCurrentPassword() {
        return current_password;
    }
}
