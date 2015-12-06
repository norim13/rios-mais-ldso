package ldso.rios.Form.IRR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

import ldso.rios.Autenticacao.Login;
import ldso.rios.DataBases.DB_functions;
import ldso.rios.DataBases.User;
import ldso.rios.MainActivities.Form_IRR_mainActivity;
import ldso.rios.MainActivities.GuardaRios;
import ldso.rios.R;

/*
View para mostrar um form irr já preenchido
 */
public class ViewFormIRR extends AppCompatActivity {

    LinearLayout linearLayout;
    Form_IRR form;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_form_irr);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        linearLayout = (LinearLayout) this.findViewById(R.id.linerLayout);

        linearLayout.setFocusable(false);
        linearLayout.setClickable(false);



        this.form = new Form_IRR();
        this.form.generate();
        try {
            this.id = ((int) this.getIntent().getSerializableExtra("id")) + "";
        }
        catch (Exception e){

        }

        if(getIntent().getSerializableExtra("form_irr")!= null) {
            Log.e("form", "entrou");
            HashMap<Integer,Object> respostas=(HashMap<Integer, Object>) getIntent().getSerializableExtra("form_irr");
            HashMap<Integer,String> outros= (HashMap<Integer, String>) respostas.get(-3);
            this.form.setRespostas(respostas,outros);
            this.form.other_response=outros;

        }


        for(int i=0;i<=32;i++)
        {
            try {
                this.form.getPerguntas().get(i).generateView(linearLayout, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.form.getPerguntas().get(i).setAnswer();
        }





    }



    /*
    se clicar no botão de edit, inicia um FormIRRSwipe para editar o formulário
     */
    public void edit_form(View view){
        Log.e("vai","vai editar");
        Intent i;
        i = new Intent(this, FormIRRSwipe.class);
        i.putExtra("form_irr", form.getRespostas());
        i.putExtra("form_irr_other",form.other_response);
        i.putExtra("id", id);
        startActivity(i);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(this.getIntent().getSerializableExtra("saved")!=null)
            getMenuInflater().inflate(R.menu.menu_form_upload, menu);
        else
            getMenuInflater().inflate(R.menu.menu_form_irrview, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.navigate_guardarios)
            startActivity(new Intent(this, GuardaRios.class));
        if (id == R.id.navigate_account)
            startActivity(new Intent(this, Login.class));
        if (id == R.id.navigate_upload)
        {

            //Form_IRR.loadFromIRR(this.getApplicationContext());
            this.form.fillAnswers();
            Form_IRR submeter= new Form_IRR();
            submeter.generate();
            submeter.setRespostas(this.form.respostas,this.form.other_response);
            submeter.fillAnswers();
            Form_IRR.uploadFormIRR(this.getApplicationContext(),submeter);
            this.finish();


            //Log.e("teste","tamanho do array"+Form_IRR.all_from_irrs.size());
        }
        if (id == R.id.navigate_remove){
            Log.e("delete","entrou");
            User u = User.getInstance();
            DB_functions.deleteForm(this,this.getIntent().getSerializableExtra("id").toString(),u.getEmail(),u.getAuthentication_token() );

        }

        return super.onOptionsItemSelected(item);
    }

    public void apagaou() {
        Toast toast = Toast.makeText(ViewFormIRR.this, "Formulário apagado", Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(getApplicationContext(), Form_IRR_mainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
